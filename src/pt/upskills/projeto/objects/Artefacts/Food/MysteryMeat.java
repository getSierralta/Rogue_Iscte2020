package pt.upskills.projeto.objects.Artefacts.Food;

import pt.upskills.projeto.objects.Food;
import pt.upskills.projeto.rogue.utils.Position;


public class MysteryMeat extends Food {

    private Position position;

    public MysteryMeat(Position position) {
        super(5, 0,50);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Mystery_meat";
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
