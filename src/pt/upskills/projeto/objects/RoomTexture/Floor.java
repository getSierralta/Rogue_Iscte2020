package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Floor implements ImageTile {

    private Position position;

    public Floor(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Floor_prison";
        }else if(Map.level == 3){
            return "Floor_special_city";
        }
        return "Floor";
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
