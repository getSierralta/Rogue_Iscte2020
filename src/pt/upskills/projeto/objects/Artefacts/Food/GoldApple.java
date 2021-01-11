package pt.upskills.projeto.objects.Artefacts.Food;

import pt.upskills.projeto.objects.Food;
import pt.upskills.projeto.rogue.utils.Position;

public class GoldApple extends Food {

    private Position position;

    public GoldApple(Position position) {
        super(1000, 50,600);
        this.position = position;
    }

    @Override
    public String getName() {
        return "GoldApple";
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
