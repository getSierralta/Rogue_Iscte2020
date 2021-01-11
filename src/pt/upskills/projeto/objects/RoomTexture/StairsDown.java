package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class StairsDown implements ImageTile {

    private Position position;

    public StairsDown(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Stairs_down_prison";
        }
        return "StairsDown";
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
