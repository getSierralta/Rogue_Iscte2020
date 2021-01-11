package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Ocho implements ImageTile {

    private Position position;

    public Ocho(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "8";
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
