package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Sesentaidos implements ImageTile {

    private Position position;

    public Sesentaidos(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "62";
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
