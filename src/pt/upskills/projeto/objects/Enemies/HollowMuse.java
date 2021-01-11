package pt.upskills.projeto.objects.Enemies;

import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class HollowMuse extends Enemy {

    private Position position;

    public HollowMuse(Position position) {
        super(2,80,"horse",2,15,"level2Artefacts",100,2,100);
        this.position = position;
    }

    @Override
    public String getName() {
        return "HollowMuse";
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
