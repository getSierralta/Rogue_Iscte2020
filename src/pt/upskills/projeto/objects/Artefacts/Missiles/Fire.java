package pt.upskills.projeto.objects.Artefacts.Missiles;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.objects.MissileWeapon;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

public class Fire extends MissileWeapon {

    private Position position;


    public Fire(Position position) {
        super(100, 0, 5, 0, 6, 12, "explosion");
        this.position = position;
    }

    @Override
    public void setExplosion(String explosion) {
        this.explosion.setName(explosion);
    }
    @Override
    public String getName() {
        return "Fire";
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
