package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.game.MissileThread;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;

public abstract class Artefact implements ImageTile {
	final int price;
	final int sell;
	final int experience;

	/**
	 * //TODO store
	 * @param price of buying at the store
	 * @param sell of selling at the store
	 * @param experience the points the hero gets when collecting the item
	 */
	public Artefact(int price, int sell, int experience) {
		this.price = price;
		this.sell = sell;
		this.experience = experience;
	}

	public int getExperience() {
		return experience;
	}

	/**
	 * by default if an item doesn't override the use method them it can't be used
	 * @param index the index in which the item has this artefact saved
	 * @return if you can use the item oor not
	 */
	protected boolean use(int index){
		Map.gui.setText(new JLabel("Can't use "+getName()),false);
		return false;
	}

	/**
	 * this methods take an object from the status and put's it in the tiles in the position of the hero
	 * If you can't use an object you can throw it to where the hero is standing
	 * @param index the index in which the item has this artefact saved
	 * @param isObject if is true then is an artefact if is false is a Missile
	 */
	protected void throwObject(int index, boolean isObject){

		if (isObject){
			//Remove the object from the status
			Map.gui.removeStatusImage(Map.hero.getObject(index));
			//Set the position of the object to the position of the hero
			Map.hero.getObject(index).setPosition(new Position(Map.hero.getPosition().getX(), Map.hero.getPosition().getY()));
			//Add the object to the tiles
			Map.tiles.add(Map.hero.getObject(index));
			//Adds the object to the gui
			Map.gui.addImage(Map.hero.getObject(index));
			//Removes the object from the hero
			Map.hero.setObject(index, null);
		}else {
			Map.gui.removeStatusImage(Map.hero.getMissiles(index));
			Map.hero.getMissiles(index).setPosition(new Position(Map.hero.getPosition().getX(), Map.hero.getPosition().getY()));
			Map.tiles.add(Map.hero.getMissiles(index));
			Map.gui.addImage(Map.hero.getMissiles(index));
			//Creates a new thread
			MissileThread missileThread = new MissileThread(Map.hero.getDirection(),Map.hero.getMissiles(index));
			//Starts the thread
			missileThread.start();
			Map.gui.setText(new JLabel(getName()+" LAUNCHED . . .. . .. ."),true);
			//Set the missile to null (Avoid using it twice)
			Map.hero.setMissiles(index, null);
		}

	}

	@Override
	public String toString() {
		return getName()+","+getPosition().getX()+","+getPosition().getY()+";";
	}

	public  int getHitDamage(){
		return 0;
	}

	public  int getMinimumHit(){
		return 0;
	}
}
