package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class Skeleton extends Enemy {

    private Position position;

    public Skeleton(Position position) {
        super(1,8,"Meele",1,3,"level1artefacts",10,3,16);
        this.position = position;
    }


    @Override
    public String getName() {
        return "Skeleton";
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
