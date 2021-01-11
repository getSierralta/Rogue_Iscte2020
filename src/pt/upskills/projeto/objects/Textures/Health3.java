package pt.upskills.projeto.objects.Textures;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Health3 implements ImageTile, Health {

    private Position position;

    public Health3(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "health3";
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
