package pt.upskills.projeto.objects.Artefacts.Armor;


import pt.upskills.projeto.objects.Armor;
import pt.upskills.projeto.rogue.utils.Position;

public class Plate extends Armor {

    private Position position;

    public Plate(Position position) {
        super(1000,100,10, 17, 10,5);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Plate_armor";
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
