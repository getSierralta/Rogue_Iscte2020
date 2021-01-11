package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

public class StairsUp implements ImageTile, Obstacle {

    private Position position;

    public StairsUp(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Stairs_up_prison";
        }
        return "StairsUp";
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
