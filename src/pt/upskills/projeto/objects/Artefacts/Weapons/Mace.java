package pt.upskills.projeto.objects.Artefacts.Weapons;


import pt.upskills.projeto.objects.MeeleWeapon;
import pt.upskills.projeto.rogue.utils.Position;

public class Mace extends MeeleWeapon {

    private Position position;

    public Mace(Position position) {
        super(60,20,1, 12, 5, 10);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Mace";
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
