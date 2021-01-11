package pt.upskills.projeto.objects.Textures;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Health9 implements ImageTile, Health {

    private Position position;

    public Health9(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "health9";
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
