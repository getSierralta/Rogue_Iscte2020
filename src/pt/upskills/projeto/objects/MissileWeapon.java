package pt.upskills.projeto.objects;


import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefacts.Missiles.Bomb;
import pt.upskills.projeto.objects.Textures.Explosion;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;

public abstract class MissileWeapon extends Powers{

	private final int baseStrength;
	private final int minimumHit;
	private int hitDamage;
	private final String power;
	public Explosion explosion;
	private int time;
	private int flag = 0;

	public MissileWeapon(int price, int sell,int experience, int baseStrength, int minimumHit, int hitDamage, String power) {
		super(price, sell, experience);
		this.baseStrength = baseStrength;
		this.minimumHit = minimumHit;
		this.hitDamage = hitDamage;
		this.power = power;
		this.explosion = new Explosion(this.getPosition(), "Explosion");

	}

	public int getMinimumHit() {
		return minimumHit;
	}

	public int getHitDamage() {
		return hitDamage;
	}

	public Explosion getExplosion() {
		return explosion;
	}

	public void setExplosion(Explosion explosion){
		this.explosion = explosion;
	}

	public boolean validateImpact(Position position){

		for (ImageTile tile: Map.tiles) {
			if (tile.getPosition().getX() == position.getX() &&
					tile.getPosition().getY() == position.getY()) {
				if (tile instanceof Obstacle) {
					Map.gui.setText(new JLabel(this.getName()+" has hit "+tile.getName()),true);
					this.explosion.setPosition(position);
					Map.tiles.add(explosion);
					Map.gui.addImage(explosion);
					if (tile instanceof Enemy){
						Map.gui.setText(new JLabel(this.getName()+" has hit "+tile.getName()),true);
						if (this instanceof Bomb){
							this.activateBomb();
						}
						Map.hero.attack((Enemy) tile,false,this);
					}
					return false;
				}
			}
		}
		return true;
	}


	@Override
	protected String getPower() {
		return null;
	}
	@Override
	public long getTime() {
		return 0;
	}

	@Override
	public void changeStatus() {

	}
	@Override
	protected void setHitDamage(int i) {
		this.hitDamage = i;
	}

	@Override
	protected void setTime(int i) {
		this.time = i;
	}

	@Override
	protected boolean use(int indexArtefact) {
		if(Map.hero.getStrength() >= this.baseStrength){
			return true;
		}
		Map.gui.setText(new JLabel("Can't throw "+this.getName()+" try again when you are stronger"),false);
		return false;
	}
}