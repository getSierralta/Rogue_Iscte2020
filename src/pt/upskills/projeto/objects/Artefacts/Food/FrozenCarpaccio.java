package pt.upskills.projeto.objects.Artefacts.Food;

import pt.upskills.projeto.objects.Food;
import pt.upskills.projeto.rogue.utils.Position;

public class FrozenCarpaccio extends Food {

    private Position position;

    public FrozenCarpaccio(Position position) {
        super(5, 0,1);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Frozen_carpaccio";
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
