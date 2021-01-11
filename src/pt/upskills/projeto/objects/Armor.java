package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;

import javax.swing.*;

public abstract class Armor extends Artefact{

	private int baseStrength;
	private int hitDamage;
	private int exponent;


	public Armor(int price, int sell, int experience, int baseStrength, int hitDamage, int exponent) {
		super(price, sell, experience);
		this.baseStrength = baseStrength;
		this.hitDamage = hitDamage;
		this.exponent = exponent;
	}

	public int getBaseStrength() {
		return baseStrength;
	}

	public int getHitDamage() {
		if (Map.hero.getLevel() < 5){
			return hitDamage + Map.hero.getLevel()*exponent;
		}else {
			return hitDamage + 5*exponent;
		}
	}

	@Override
	protected boolean use(int indexArtefact) {
		if (Map.hero.getStrength() >= (this.getBaseStrength()-Map.hero.getLevel())){
			if (Map.hero.getArmor() == null || !(Map.hero.getArmor().getName().equals(this.getName()))) {
				System.out.println(this.getName() + " equipped!");
				Map.gui.removeStatusImage(this);
				Map.hero.setObject(indexArtefact, null);
				Map.hero.setArmor(this);
				Map.gui.setText(new JLabel(this.getName()+" equipped! Armor +"+getHitDamage()),true);
				return true;
			}
		}
		Map.gui.setText(new JLabel("Can't equip "+this.getName()+" try again when you are stronger"),false);
		return false;

	}

}
