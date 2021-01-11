package pt.upskills.projeto.objects.Artefacts.Keys;


import pt.upskills.projeto.objects.MotherKey;
import pt.upskills.projeto.rogue.utils.Position;

public class SkullKey extends MotherKey {

    private Position position;

    public SkullKey(Position position) {
        super(0,0,1);
        this.position = position;
    }



    @Override
    public String getName() {
        return "Skull_key";
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
