package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Once implements ImageTile {

    private Position position;

    public Once(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "11";
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
