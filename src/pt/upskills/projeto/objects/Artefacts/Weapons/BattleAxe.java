package pt.upskills.projeto.objects.Artefacts.Weapons;


import pt.upskills.projeto.objects.MeeleWeapon;
import pt.upskills.projeto.rogue.utils.Position;

public class BattleAxe extends MeeleWeapon {

    private Position position;

    public BattleAxe(Position position) {
        super(510,50,1, 16, 9, 18);
        this.position = position;
    }

    @Override
    public String getName() {
        return "BattleAxe";
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
