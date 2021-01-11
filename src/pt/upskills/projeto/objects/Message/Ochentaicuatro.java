package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Ochentaicuatro implements ImageTile {

    private Position position;

    public Ochentaicuatro(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "84";
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
