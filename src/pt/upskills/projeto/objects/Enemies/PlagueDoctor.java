package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class PlagueDoctor extends Enemy {

    private Position position;

    public PlagueDoctor(Position position) {
        super(1,25,"Meele",3,8,"level2Artefatcs",20,3,64);
        this.position = position;
    }

    @Override
    public String getName() {
        return "PlagueDoctor";
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
