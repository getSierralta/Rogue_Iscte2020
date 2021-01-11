package pt.upskills.projeto.objects.NPC;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public class AnimeGirlDancing2 implements ImageTile {

    private Position position;

    public AnimeGirlDancing2(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "dancingAnime2";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return getName()+","+getPosition().getX()+","+getPosition().getY()+";";
    }
}
