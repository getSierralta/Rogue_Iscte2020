package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefact;
import pt.upskills.projeto.objects.Artefacts.Food.*;
import pt.upskills.projeto.objects.Artefacts.Keys.GoldenKey;
import pt.upskills.projeto.objects.Artefacts.Keys.Key;
import pt.upskills.projeto.objects.Artefacts.Missiles.Bomb;
import pt.upskills.projeto.objects.Artefacts.Missiles.CurareDart;
import pt.upskills.projeto.objects.Artefacts.Missiles.Fire;
import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grass implements ImageTile, Obstacle {

    private Position position;

    public Grass(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Grass_long_prison";
        }
        return "Grass";
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
    public String toString() {
        return getName()+","+getPosition().getX()+","+getPosition().getY()+";";
    }

    public Artefact getDrop() {
        Position newPosition = new Position(this.getPosition().getX(),this.getPosition().getY());
        List<Artefact> randomDrop = new ArrayList<>();
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Key(newPosition));
        randomDrop.add(new Fire(newPosition));
        randomDrop.add(new Ration(newPosition));
        randomDrop.add(new GoodMeat(newPosition));
        randomDrop.add(new Pasty(newPosition));
        randomDrop.add(new FrozenCarpaccio(newPosition));
        randomDrop.add(new MysteryMeat(newPosition));
        randomDrop.add(new Ration(newPosition));
        randomDrop.add(new GoodMeat(newPosition));
        randomDrop.add(new Pasty(newPosition));
        randomDrop.add(new Ration(newPosition));
        randomDrop.add(new GoodMeat(newPosition));
        randomDrop.add(new Pasty(newPosition));
        randomDrop.add(new FrozenCarpaccio(newPosition));
        randomDrop.add(new Ration(newPosition));
        randomDrop.add(new GoodMeat(newPosition));
        randomDrop.add(new Pasty(newPosition));
        randomDrop.add(new FrozenCarpaccio(newPosition));
        randomDrop.add(new MysteryMeat(newPosition));
        randomDrop.add(new CurareDart(newPosition));
        randomDrop.add(new Bomb(newPosition));
        randomDrop.add(new GoldenKey(newPosition));
        //Add potions
        for (int i = 0; i < 40; i++) {
            randomDrop.add(null);
        }
        Random random = new Random();
        int r = random.nextInt((randomDrop.size()-1)+1);
        return randomDrop.get(r);
    }
    public void use() {
        Artefact drop = getDrop();
        Map.tiles.add(new GrassT(new Position(this.getPosition().getX(),this.getPosition().getY())));
        Map.gui.addImage(new GrassT(new Position(this.getPosition().getX(),this.getPosition().getY())));
        Map.tiles.remove(this);
        Map.gui.removeImage(this);
        if (drop != null){
            Map.tiles.add(drop);
            Map.gui.addImage(drop);
        }
        for (ImageTile tile: Map.tiles) {
            if (tile instanceof Enemy){
                Map.gui.removeImage(tile);
                Map.gui.addImage(tile);
            }
        }
        Map.gui.removeImage(Map.hero);
        Map.gui.addImage(Map.hero);
    }
}
