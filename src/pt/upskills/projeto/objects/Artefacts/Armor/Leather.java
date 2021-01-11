package pt.upskills.projeto.objects.Artefacts.Armor;


import pt.upskills.projeto.objects.Armor;
import pt.upskills.projeto.rogue.utils.Position;

public class Leather extends Armor {

    private Position position;

    public Leather(Position position) {
        super(60,20,4, 11, 4,2);
        this.position = position;
    }


    @Override
    public String getName() {
        return "Leather_armor";
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
