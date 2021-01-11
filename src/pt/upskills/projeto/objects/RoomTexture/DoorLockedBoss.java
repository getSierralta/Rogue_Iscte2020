package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Door;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

public class DoorLockedBoss extends Door implements ImageTile, Obstacle {

    private Position position;

    public DoorLockedBoss( Position position) {
        super("Skull_key");
        this.position = position;
    }

    @Override
    public String getName() {
        if (Map.level == 2){
            return "Door_boss_locked_prison";
        }
        return "Door_boss_locked";
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
