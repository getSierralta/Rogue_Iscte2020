package pt.upskills.projeto.objects.Artefacts.Weapons;


import pt.upskills.projeto.objects.MeeleWeapon;
import pt.upskills.projeto.rogue.utils.Position;

public class ShortSword extends MeeleWeapon {

    private Position position;

    public ShortSword(Position position) {
        super(20,25,1, 10, 3, 6);
        this.position = position;
    }

    @Override
    public String getName() {
        return "ShortSword";
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
