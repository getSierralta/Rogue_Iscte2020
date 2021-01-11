package pt.upskills.projeto.objects.Enemies;
import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class Bat extends Enemy {

    private Position position;

    public Bat(Position position) {
        super(1,12,"side-to-side",2,4,"level1artefacts",10,5,32);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Bat";
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
