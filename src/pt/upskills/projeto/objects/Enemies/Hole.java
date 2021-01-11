package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class Hole extends Enemy {

    private Position position;

    public Hole(Position position) {
        super(0,40,"hole",0,0,"level1Artefacts",20,0,32);
        this.position = position;
    }
    //Graphic made by Jõao Figueiredo
    @Override
    public String getName() {
        return "Hole";
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
