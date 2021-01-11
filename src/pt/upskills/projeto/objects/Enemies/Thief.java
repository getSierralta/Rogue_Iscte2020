package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class Thief extends Enemy {

    private Position position;

    public Thief(Position position) {
        super(1,80,"bishop",1,7,"level2Artefacts",30,2,80);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Thief";
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
