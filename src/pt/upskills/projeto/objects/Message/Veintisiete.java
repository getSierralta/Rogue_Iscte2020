package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Veintisiete implements ImageTile {

    private Position position;

    public Veintisiete(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "27";
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
