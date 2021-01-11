package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

public class Statue implements ImageTile, Obstacle {

    private Position position;

    public Statue(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Statue_special_city";
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
