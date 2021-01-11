package pt.upskills.projeto.objects.Artefacts.Weapons;

import pt.upskills.projeto.objects.MeeleWeapon;
import pt.upskills.projeto.rogue.utils.Position;

public class Sword extends MeeleWeapon {

    private Position position;

    public Sword(Position position) {
        super(280,30,1, 14, 7, 16);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Sword";
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
