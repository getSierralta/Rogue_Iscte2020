package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;

import javax.swing.*;

public abstract class Food extends Artefact{
	public Food(int price, int sell,int experience) {
		super(price, sell, experience);
	}

	@Override
	protected boolean use(int indexArtefact) {

		int[] heroLife = Map.hero.getHealth();
		for (int i = 0; i <= 3 ; i++) {
			if (heroLife[i] < Map.hero.getHealthBoxValue() ){
				Map.gui.setText(new JLabel(this.getName()+" was consumed! +"+(Map.hero.getHealthBoxValue()-heroLife[i])),true);
				heroLife[i] = Map.hero.getHealthBoxValue();
				Map.hero.setHealth(heroLife);
				Map.hero.changeImageLifeStatus();
				Map.gui.removeStatusImage(this);
				Map.hero.setObject(indexArtefact,null);
				return true;
			}
		}
		System.out.println("Life's full can´t eat "+this.getName());
		Map.gui.setText(new JLabel("Life's full can´t eat "+this.getName()),false);
		return false;
	}
	@Override
	public String toString() {
		return getName()+","+getPosition().getX()+","+getPosition().getY()+";";
	}
}
