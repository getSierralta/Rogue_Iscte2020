package pt.upskills.projeto.objects;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

public interface Powerful {
    void activatePower();
    long getTime();
    void changeStatus();
    ImageTile getExplosion();
    void setExplosion(String explosion);
    void activateBomb();
    String getName();
    void attackHero();
    Position getPosition();
    void setPosition(Position nextPosition);
    boolean validateImpact(Position nextPosition);
}
