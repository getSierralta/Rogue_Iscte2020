package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Powers;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;
import java.util.Random;

public class Trap extends Powers {

    private final Explosion explosion;
    private Position position;
    private String name;
    private int time;
    private String power;
    private int hitDamage;

    public Trap(Position position) {
        super(0,0,50);
        this.position = position;
        this.name = setName();
        explosion = new Explosion(new Position(this.position.getX(),this.position.getY()),"Explosion");
    }

    public void setPower(String power) {
        this.power = power;
    }

    private String setName() {
        Random random = new Random();
        int r = random.nextInt((7 - 1) + 1);
        switch (r) {
            case 2:
                setPower("paralyticGas");
                if (Map.level == 2) {
                    return "Trap_paralytic_gas_prison";
                }
                return "Trap_paralytic_gas_sewers";
            case 3:
                setPower("lightning");
                if (Map.level == 2) {
                    return "Lightning_prison";
                }
                return "Lightning_sewers";
            case 4:
                setPower("summoning");
                if (Map.level == 2) {
                    return "Trap_alarm_prison";
                }
                return "Trap";
            case 5:
                setPower("liquidFlame");
                if (Map.level == 2){
                    return  "Trap_fire_prison";
                }
                return  "Trap_fire_sewers";
            case 6:
                setPower("poison");
                if (Map.level == 2){
                    return  "Trap_poison_dart_prison";
                }
                return  "Trap_poison_dart_sewers";
            default:
                setPower("toxicGas");
                if (Map.level == 2){
                    return  "Trap_toxic_gas_prison";
                }
                return  "Trap_toxic_gas_sewers";
        }

    }

    @Override
    protected void setHitDamage(int i) {
        this.hitDamage = i;
    }

    @Override
    protected void setTime(int i) {
        this.time = i;
    }

    public long getTime() {
        return time;
    }

    public void changeStatus() {
        if (Map.level == 2) {
            this.name = "Trap_triggered_prison";
        }else {
            this.name = "Trap_triggered_sewers";
        }

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
        return false;
    }

    @Override
    public String toString() {
        return getName() + "," + getPosition().getX() + "," + getPosition().getY() + ";";
    }


    @Override
    public int getHitDamage() {
        return hitDamage;
    }

    @Override
    protected String getPower() {
        return power;
    }


}
