package pt.upskills.projeto.objects.Textures;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

public class Black implements ImageTile, Obstacle {

    private Position position;

    public Black(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Black";
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
}
