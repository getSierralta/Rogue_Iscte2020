package pt.upskills.projeto.objects.Artefacts.Weapons;


import pt.upskills.projeto.objects.MeeleWeapon;
import pt.upskills.projeto.rogue.utils.Position;

public class Hammer extends MeeleWeapon {

    private Position position;

    public Hammer(Position position) {
        super(1000,100,1, 18, 10, 21);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    protected boolean use(int indexArtefact) {

        return false;
    }

}
