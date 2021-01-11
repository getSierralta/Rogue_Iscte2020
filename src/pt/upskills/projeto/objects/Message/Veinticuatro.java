package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Veinticuatro implements ImageTile {

    private Position position;

    public Veinticuatro(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "24";
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
