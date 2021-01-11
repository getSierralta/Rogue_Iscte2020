package pt.upskills.projeto.objects.Artefacts;

import pt.upskills.projeto.objects.Artefact;
import pt.upskills.projeto.rogue.utils.Position;

public class Gold extends Artefact {

    private Position position;
    private int amount;

    public Gold(Position position, int amount) {
        super(0,0,1);
        this.position = position;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return "Gold";
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
