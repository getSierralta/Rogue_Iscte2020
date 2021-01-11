package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.Door;
import pt.upskills.projeto.rogue.utils.Position;

public class DoorClosed extends Door {


    private Position position;

    public DoorClosed(Position position) {
        super("null");
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Door_closed_prison";
        }
        return "DoorClosed";
    }

    @Override
    public Position getPosition() {
        return position;
    }
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

}
