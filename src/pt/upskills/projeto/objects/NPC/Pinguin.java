package pt.upskills.projeto.objects.NPC;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Pinguin implements ImageTile {

    private Position position;

    public Pinguin(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "pinguin";
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
