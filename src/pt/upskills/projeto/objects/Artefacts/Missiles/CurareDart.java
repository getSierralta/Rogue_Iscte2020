package pt.upskills.projeto.objects.Artefacts.Missiles;



import pt.upskills.projeto.objects.MissileWeapon;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

public class CurareDart extends MissileWeapon {

    private Position position;

    private Explosion explosion = new Explosion(this.getPosition(), "Explosion");

    public CurareDart(Position position) {
        super(10, 5, 5, 12, 2, 5, "paralize");
        this.position = position;
    }

    @Override
    public void setExplosion(String explosion) {
        this.explosion.setName(explosion);
    }
    @Override
    public String getName() {
        return "Curare_dart";
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
