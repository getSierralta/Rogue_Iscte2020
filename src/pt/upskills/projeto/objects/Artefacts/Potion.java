package pt.upskills.projeto.objects.Artefacts;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.game.PotionThread;
import pt.upskills.projeto.game.PowerThread;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.objects.Powerful;
import pt.upskills.projeto.objects.Powers;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.util.Random;

public class Potion extends Powers {

    private final Explosion explosion;
    private Position position;
    private final String name;
    private String power;
    private int hitDamage;
    private int time;


    public Potion(Position position) {
        super(100, 0, 12);
        this.position = position;
        this.name = setName();
        this.power = setPower();
        this.hitDamage = 0;
        this.explosion = new Explosion(this.getPosition(), "Explosion");
        this.time = 0;
    }

    @Override
    protected void setHitDamage(int i) {
        this.hitDamage = i;
    }

    @Override
    protected void setTime(int i) {
        this.time = i;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public int getHitDamage() {
        return hitDamage;
    }

    @Override
    public String getPower() {
        return power;
    }

    public String setPower() {
        Random random = new Random();
        int r = random.nextInt(11);
        switch (r) {
            case 2:
                return "liquidFlame";
            case 3:
                return "paralyticGas";
            case 4:
                return "poison";
            case 5:
                return "lightning";
            case 6:
                return "summoning";
            case 7:
                return "levitation";
            case 8:
                return "healing";
            case 9:
                return "might";
            case 10:
                return "experience";
            default:
                return "toxicGas";
        }
    }

    private String setName(){
        Random random = new Random();
        int r = random.nextInt((13-1)+1);
        switch (r){
            case 2:
                return "Amber";
            case 3:
                return "Azure";
            case 4:
                return "Bistre";
            case 5:
                return "Charcoal";
            case 6:
                return "Crimson";
            case 7:
                return "Golden";
            case 8:
                return "Indigo";
            case 9:
                return "Ivory";
            case 10:
                return "Jade";
            case 11:
                return "Magenta";
            case 12:
                return "Silver";
            default:
                return "Turquoise";
        }
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean validateImpact(Position nextPosition) {
        for (ImageTile tile: Map.tiles) {
            if (tile.getPosition().getX() == position.getX() &&
                    tile.getPosition().getY() == position.getY()) {
                if (tile instanceof Obstacle) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void changeStatus() {
        Map.tiles.remove(this);
        Map.gui.removeImage(this);
        Map.tiles.remove(this.getExplosion());
        Map.gui.removeImage(this.getExplosion());
    }

    @Override
    public ImageTile getExplosion() {
        return explosion;
    }

    @Override
    public void setExplosion(String explosion) {
        this.explosion.setName(explosion);
    }

    @Override
    protected boolean use(int index) {
        Map.gui.removeStatusImage(Map.hero.getObject(index));
        try {
            Powerful powerful = (Powerful) Map.hero.getObject(index);
            powerful.setPosition(new Position(Map.hero.getPosition().getX(), Map.hero.getPosition().getY()));
            //Creates a new thread
            PowerThread powerThread = new PowerThread(powerful);
            //Starts the thread
            powerThread.start();
        }catch (ClassCastException e){
            System.out.println("not a potion");
        }

        //Set the missile to null (Avoid using it twice)
        Map.hero.setObject(index, null);
        return true;
    }

    @Override
    protected void throwObject(int index, boolean isObject) {
        Map.gui.removeStatusImage(Map.hero.getObject(index));
        Map.hero.getObject(index).setPosition(new Position(Map.hero.getPosition().getX(), Map.hero.getPosition().getY()));
        Map.tiles.add(Map.hero.getObject(index));
        Map.gui.addImage(Map.hero.getObject(index));
        //Creates a new thread
        this.explosion.setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        try {
            PotionThread potionThread = new PotionThread((Powerful) Map.hero.getObject(index),Map.hero.getDirection());
            //Starts the thread
            potionThread.start();
        }catch (ClassCastException e){
            System.out.println("Not a potion");
        }

        Map.gui.setText(new JLabel(getName()+" LAUNCHED . . .. . .. ."),true);
        //Set the missile to null (Avoid using it twice)
        Map.hero.setObject(index, null);
    }
}
