package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class Pig extends Enemy {

    private Position position;

    public Pig(Position position) {
        super(2,15,"bishop",3,6,"level1Artefacts",50,3,48);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Pig";
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
