package pt.upskills.projeto.objects.Message;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class Diesinueve implements ImageTile {

    private Position position;

    public Diesinueve(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "19";
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
