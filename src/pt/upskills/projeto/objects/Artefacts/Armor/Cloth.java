package pt.upskills.projeto.objects.Artefacts.Armor;


import pt.upskills.projeto.objects.Armor;
import pt.upskills.projeto.rogue.utils.Position;

public class Cloth extends Armor {

    private Position position;

    public Cloth(Position position) {
        super(20,5,2, 9, 2,1);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Cloth_armor";
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
