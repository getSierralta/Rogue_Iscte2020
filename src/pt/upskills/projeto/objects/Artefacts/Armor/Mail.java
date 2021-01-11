package pt.upskills.projeto.objects.Artefacts.Armor;


import pt.upskills.projeto.objects.Armor;
import pt.upskills.projeto.rogue.utils.Position;

public class Mail extends Armor {

    private Position position;

    public Mail(Position position) {
        super(280,30,6,13,6,3);
        this.position = position;
    }

    @Override
    public String getName() {
        return "Mail_armor";
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
