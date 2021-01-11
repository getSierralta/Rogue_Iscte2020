package pt.upskills.projeto.objects.Textures;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Explosion implements ImageTile {

    private Position position;
    private String name;

    public Explosion(Position position, String explosion) {
        this.position = position;
        this.name = explosion;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return getName()+","+getPosition().getX()+","+getPosition().getX()+";";
    }


}
