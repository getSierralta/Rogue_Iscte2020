package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class BadGuy extends Enemy {

    private Position position;

    public BadGuy(Position position) {
        super(1,20,"horse",1,7,"Level2Artefacts",25,3,80);
        this.position = position;
    }

    @Override
    public String getName() {
        return "BadGuy";
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
