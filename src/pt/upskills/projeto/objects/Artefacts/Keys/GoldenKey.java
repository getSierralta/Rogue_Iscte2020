package pt.upskills.projeto.objects.Artefacts.Keys;


import pt.upskills.projeto.objects.MotherKey;
import pt.upskills.projeto.rogue.utils.Position;

public class GoldenKey extends MotherKey {

    private Position position;

    public GoldenKey(Position position) {
        super(0,0,1);
        this.position = position;
    }



    @Override
    public String getName() {
        return "Golden_key";
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
