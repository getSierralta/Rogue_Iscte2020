package pt.upskills.projeto.objects.Artefacts.Missiles;



import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.MissileWeapon;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

public class Bomb extends MissileWeapon {

    private Position position;
    private Explosion explosion = new Explosion(this.getPosition(), "Explosion");
    public Bomb(Position position) {
        super(200, 20, 5, 10, 4, 2*Map.hero.getLevel()+3*10, "explosion");
        this.position = position;
    }


    @Override
    public void setExplosion(String explosion) {
        this.explosion.setName(explosion);
    }

    @Override
    public String getName() {
        return "Bomb";
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
