package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;

import javax.swing.*;

public abstract class MeeleWeapon extends Artefact{
	private int baseStrength;
	private int minimumHit;
	private int hitDamage;



	public MeeleWeapon(int price, int sell, int experience, int baseStrength, int minimumHit, int hitDamage) {
		super(price, sell, experience);
		this.baseStrength = baseStrength;
		this.minimumHit = minimumHit;
		this.hitDamage = hitDamage;
	}

	public int getBaseStrength() {
		return baseStrength;
	}

	public int getMinimumHit() {
		return minimumHit;
	}

	public int getHitDamage() {
		return hitDamage;
	}

	@Override
	protected boolean use(int indexArtefact) {
		if (Map.hero.getStrength() >= this.getBaseStrength()){
			if (Map.hero.getWeapon() == null || !(Map.hero.getWeapon().getName().equals(this.getName()))) {
				System.out.println(this.getName() + " equipped!");
				Map.gui.removeStatusImage(this);
				Map.hero.setObject(indexArtefact, null);
				Map.hero.setWeapon(this);
				Map.gui.setText(new JLabel(this.getName()+" equipped! hit damage: +"+getHitDamage()),true);
				return true;
			}
		}
		Map.gui.setText(new JLabel("Can't equip "+this.getName()+" try again when you are stronger"),false);
		return false;
	}

}
