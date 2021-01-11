package pt.upskills.projeto.objects.RoomTexture;


import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.Door;
import pt.upskills.projeto.rogue.utils.Position;

public class DoorLocked extends Door {

    private Position position;

    public DoorLocked( Position position) {
        super("Key");
        this.position = position;
    }


    @Override
    public String getName() {
        if (Map.level == 2){
            return "Door_locked_prison";
        }
        return "Door_locked";
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
