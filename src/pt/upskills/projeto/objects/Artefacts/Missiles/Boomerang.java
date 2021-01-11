package pt.upskills.projeto.objects.Artefacts.Missiles;



import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.MissileWeapon;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

public class Boomerang extends MissileWeapon {

    private Position position;
    private Explosion explosion = new Explosion(this.getPosition(), "Explosion");
    public Boomerang(Position position) {
        super(100, 5, 16, 10, 4, (int) (2.5* Map.hero.getLevel()*1.5), "");
        this.position = position;
    }

    @Override
    public void setExplosion(String explosion) {
        this.explosion.setName(explosion);
    }


    @Override
    public String getName() {
        return "Boomerang";
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
