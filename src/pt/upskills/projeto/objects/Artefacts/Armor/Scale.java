package pt.upskills.projeto.objects.Artefacts.Armor;


import pt.upskills.projeto.objects.Armor;
import pt.upskills.projeto.rogue.utils.Position;

public class Scale extends Armor {

    private Position position;

    public Scale(Position position) {
        super(510,50,8, 8, 8,4);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Scale_armor";
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
