package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.Door;
import pt.upskills.projeto.rogue.utils.Position;

public class DoorOpen extends Door  {

    private Position position;

    public DoorOpen( Position position) {
        super("null");
        this.position = position;
        this.openDoor();
    }


    @Override
    public String getName() {
        if (Map.level == 2){
            return "Door_open_prison";
        }
        return "DoorOpen";
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
