package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefacts.*;
import pt.upskills.projeto.objects.Artefacts.Armor.Cloth;
import pt.upskills.projeto.objects.Artefacts.Armor.Leather;
import pt.upskills.projeto.objects.Artefacts.Armor.Mail;
import pt.upskills.projeto.objects.Artefacts.Food.*;
import pt.upskills.projeto.objects.Artefacts.Keys.GoldenKey;
import pt.upskills.projeto.objects.Artefacts.Keys.Key;
import pt.upskills.projeto.objects.Artefacts.Keys.SkullKey;
import pt.upskills.projeto.objects.Artefacts.Missiles.Bomb;
import pt.upskills.projeto.objects.Artefacts.Missiles.Boomerang;
import pt.upskills.projeto.objects.Artefacts.Missiles.CurareDart;
import pt.upskills.projeto.objects.Artefacts.Missiles.Fire;
import pt.upskills.projeto.objects.Artefacts.Weapons.BattleAxe;
import pt.upskills.projeto.objects.Artefacts.Weapons.Hammer;
import pt.upskills.projeto.objects.Artefacts.Weapons.Mace;
import pt.upskills.projeto.objects.Artefacts.Weapons.Sword;
import pt.upskills.projeto.objects.Enemies.Bat;
import pt.upskills.projeto.objects.Enemies.HollowMuse;
import pt.upskills.projeto.objects.Enemies.Pig;
import pt.upskills.projeto.objects.RoomTexture.Chasm;
import pt.upskills.projeto.objects.RoomTexture.StairsDown;
import pt.upskills.projeto.objects.RoomTexture.Wall;
import pt.upskills.projeto.objects.Textures.*;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy implements ImageTile, Obstacle {
	//TODO implements different types of movements
	//TODO implements resistant, immune, armor, and array of artefacts
	/**
	 * Stats
	 */
	private final int walkSpeed;
	private int health;
	private final String movement;
	private final int damageInitial;
	private final int damageFinal;
	private final String drops;
	private final int experience;
	private final int range;
	private final int gold;
	private ImageTile life;
	private final int baseHealth;
	private Vector2D lastDirection = Direction.LEFT.asVector();
	private int batCounter = 0;
	private boolean paralytic;

	public Enemy(int walkSpeed, int health, String movement, int damageInitial, int damageFinal, String drops, int experience, int range, int gold) {
		this.walkSpeed = walkSpeed;
		this.health = health;
		this.movement = movement;
		this.damageInitial = damageInitial;
		this.damageFinal = damageFinal;
		this.drops = drops;
		this.experience = experience;
		this.range = range;
		this.gold = gold;
		this.life = new Health1(new Position(0, 0));
		this.baseHealth = health;
		this.paralytic = false;
	}

	/**
	 * @return a random amount of gold
	 */
	private Gold getGold() {
		Random random = new Random();
		int r = random.nextInt((this.gold - 1) + 1) + 1;
		Map.gui.setText(new JLabel(getName() + " dropped " + r + "$"), true);
		return new Gold(new Position(this.getPosition().getX(), this.getPosition().getY()), r);
	}

	/**
	 * change the position of the enemy
	 *
	 * @param x         the x coordinate of where you want to go
	 * @param y         the y coordinate of where you want to go
	 * @param direction direction to go
	 */
	private boolean changePosition(int x, int y, Vector2D direction) {
		if (y > 0 && y < 9 && x > 0 && x < 9 && checkWhatsThere(x, y)) {
			this.setPosition(this.getPosition().plus(direction));
			changeLifeStatus();
			return true;
		}
		return false;
	}

	private boolean changePosition(int x, int y) {
		if (y > 0 && y < 9 && x > 0 && x < 9 && checkWhatsThere(x, y)) {
			this.setPosition(new Position(x, y));
			changeLifeStatus();
			return true;
		}
		return false;
	}

	/**
	 * @return if the hero is within the range of the enemy
	 */
	protected boolean checkForHero() {
		//We iterate throw the matrix
		for (int i = -range; i <= range; i++) {
			for (int j = -range; j <= range; j++) {
				//this.getPosition -j (We subtract the range out of the position of the enemy)
				//this.getPosition -i (We subtract the range out of the position of the enemy)
				//This way we are making sure we are using the enemy position as a reference
				if (Map.hero.getPosition().getX() == this.getPosition().getX() - j
						&& Map.hero.getPosition().getY() == this.getPosition().getY() - i) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * checks what tile is in that coordinate and takes an action accordingly
	 *
	 * @param x the x coordinate of where you want to go
	 * @param y the y coordinate of where you want to go
	 * @return true if its a coordinate to where you can move
	 */
	public boolean checkWhatsThere(int x, int y) {
		//We iterate to the tiles of the room we are it
		List<ImageTile> tiles = new ArrayList<>(Map.tiles);
		for (ImageTile tile : tiles) {
			//We chek if the tile is in the coordinates we wish to go to
			if (tile.getPosition().getX() == x &&
					tile.getPosition().getY() == y) {
				//We check what type of thing is at that coordinate
				if (tile instanceof Obstacle) {
					if (this instanceof Bat && tile instanceof Wall || this instanceof Bat && tile instanceof Black || this instanceof Bat && tile instanceof Chasm) {
						return true;
					} else {
						return false;
					}

				}
				//If the hero is in that position we attack it
				if (tile instanceof Hero) {
					attack();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * it moves the enemy in a random direction
	 */
	public void moveRandomly() {
		if (!paralytic) {
			//If the hero is not in the range, then we move randomly
			if (!checkForHero()) {
				switch (movement.toLowerCase()) {
					case "hole":
						spawnBats();
						break;
					case "side-to-side":
						moveSideToSide();
						break;
					case "bishop":
						moveBishop();
						break;
					case "horse":
						moveHorse();
						break;
					default:
						moveMeele();
						break;
				}
			} else {
				moveToHero();
			}
		}
	}

	private void spawnBats() {
		if (batCounter == 15) {
			Bat bat = new Bat(new Position(this.getPosition().getX(), this.getPosition().getY()));
			Map.tiles.add(bat);
			Map.gui.addImage(bat);
			Map.gui.setText(new JLabel("Bat spawn"), false);
		}
		if (batCounter > 15) {
			batCounter = 0;
		}
		batCounter++;
	}

	/**
	 * attacks our hero
	 */
	private void attack() {
		//We save the life of our hero so we can iterate trow it
		int[] heroLife = Map.hero.getHealth();
		Random random = new Random();
		//We create the i variable outside the loop so we can reference it later on
		int i;
		//Random().nextInt(int bound) generates a random integer from i (inclusive) to f (exclusive)
		//To include the last value we add 1
		int originalAttack = (random.nextInt(((this.damageFinal - this.damageInitial) + 1)) + this.damageInitial);
		int attack;
		if (Map.hero.getArmor() != null) {
			int defend = random.nextInt(Map.hero.getArmor().getHitDamage());
			if (originalAttack - defend >= 0) {
				attack = originalAttack - defend;
			} else {
				attack = 0;
			}

			Map.gui.setText(new JLabel(getName() + " attacked " + originalAttack + " but our armor adsorbed " + defend + " final attack " + attack), false);
		} else {
			attack = originalAttack;
			Map.gui.setText(new JLabel(getName() + " attacked " + attack), false);
		}

		//We look for the life of the hero that's greater than 0
		//That way we know what index of the heroLife[] to take from
		for (i = 3; i > 0; i--) {
			if (heroLife[i] > 0) {
				break;
			}
		}
		//We check if the life of the hero will survive the attack
		if (heroLife[i] >= attack) {
			//We take life out of our array
			heroLife[i] -= attack;
			//We send our array to our hero with the method setHealth
			Map.hero.setHealth(heroLife);
			//We update the imageStatus
			Map.hero.changeImageLifeStatus();

		} else if (i >= 1 && heroLife[i - 1] >= attack) {
			//We check that the hero can take two attacks (i>0)
			//We check if the next hero life can take the attack
			//If the hero can't take one attack then we attack twice
			int secondAttack = attack - heroLife[i];
			heroLife[i] = 0;
			heroLife[i - 1] -= secondAttack;
			Map.hero.setHealth(heroLife);
			Map.hero.changeImageLifeStatus();
		} else {
			//If it doesn't enter the other if, it means it's the last life and the hero can't take the attack
			//Therefore it's dead
			heroLife[i] = 0;
			Map.hero.setHealth(heroLife);
			Map.hero.changeImageLifeStatus();
			Map.gui.setText(new JLabel(""), false);
		}

	}


	private void moveSideToSide() {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		if (!changePosition(x, y, lastDirection)) {
			if (lastDirection.equals(Direction.LEFT.asVector())) {
				lastDirection = Direction.RIGHT.asVector();
			} else {
				lastDirection = Direction.LEFT.asVector();
			}
		}
	}

	private void moveHorse() {
		int actualX = getPosition().getX();
		int actualY = getPosition().getY();
		ArrayList<Position> pos = new ArrayList<>();
		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				if ((Math.abs(x) + Math.abs(y)) != 3)
					continue;
				if (actualX + x > 9 || actualX + x < 0 ||
						actualY + y > 9 || actualY + y < 0)
					continue;
				pos.add(new Position(actualX + x, actualY + y));
			}
		}
		Random random = new Random();
		int r;
		r = random.nextInt(pos.size());
		if (!changePosition(pos.get(r).getX(), pos.get(r).getY())) {
			moveHorse();
		}

	}

	private void moveBishop() {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		Random random = new Random();
		int r;
		r = random.nextInt((4 - 1) + 1) + 1;
		switch (r) {
			case 1:
				for (int i = 0; i <= this.walkSpeed; i++) {
					changePosition(x + 1, y - 1, Direction.UPRIGHT.asVector());
				}
				break;
			case 2:
				for (int i = 0; i <= this.walkSpeed; i++) {
					changePosition(x + 1, y + 1, Direction.DOWNRIGHT.asVector());
				}
				break;
			case 3:
				for (int i = 0; i <= this.walkSpeed; i++) {
					changePosition(x - 1, y - 1, Direction.UPLEFT.asVector());
				}
				break;
			case 4:
				for (int i = 0; i <= this.walkSpeed; i++) {
					changePosition(x - 1, y + 1, Direction.DOWNLEFT.asVector());
				}
				break;
			default:
				break;
		}
	}

	private void moveMeele() {
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		Random random = new Random();
		int r;
		r = random.nextInt((8 - 1) + 1) + 1;
		switch (r) {
			case 1:
				changePosition(x - 1, y, Direction.LEFT.asVector());
				break;
			case 2:
				changePosition(x + 1, y, Direction.RIGHT.asVector());
				break;
			case 3:
				changePosition(x, y - 1, Direction.UP.asVector());
				break;
			case 4:
				changePosition(x, y + 1, Direction.DOWN.asVector());
				break;
			case 5:
				changePosition(x + 1, y - 1, Direction.UPRIGHT.asVector());
				break;
			case 6:
				changePosition(x + 1, y + 1, Direction.DOWNRIGHT.asVector());
				break;
			case 7:
				changePosition(x - 1, y - 1, Direction.UPLEFT.asVector());
				break;
			case 8:
				changePosition(x - 1, y + 1, Direction.DOWNLEFT.asVector());
				break;
			default:
				changePosition(x, y, Direction.RIGHT.asVector());
				break;
		}

	}


	/**
	 * Method that moves us to the hero
	 */
	protected void moveToHero() {
		//Position nextPosition = this.getPosition().plus(Map.hero.getDirection().asVector());
		// changePosition(nextPosition);
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		if (this.getPosition().getY() < Map.hero.getPosition().getY()) {
			if (this.getPosition().getX() < Map.hero.getPosition().getX()) {
				changePosition(x + 1, y + 1, Direction.DOWNRIGHT.asVector());
			} else if (this.getPosition().getX() > Map.hero.getPosition().getX()) {
				changePosition(x - 1, y + 1, Direction.DOWNLEFT.asVector());
			} else {
				changePosition(x, y + 1, Direction.DOWN.asVector());
			}
		} else if (this.getPosition().getY() > Map.hero.getPosition().getY()) {
			if (this.getPosition().getX() < Map.hero.getPosition().getX()) {
				changePosition(x + 1, y - 1, Direction.UPRIGHT.asVector());
			} else if (this.getPosition().getX() > Map.hero.getPosition().getX()) {
				changePosition(x - 1, y - 1, Direction.UPLEFT.asVector());
			} else {
				changePosition(x, y - 1, Direction.LEFT.asVector());
			}
		} else {
			if (this.getPosition().getX() < Map.hero.getPosition().getX()) {
				changePosition(x + 1, y, Direction.RIGHT.asVector());
			} else if (this.getPosition().getX() > Map.hero.getPosition().getX()) {
				changePosition(x - 1, y, Direction.LEFT.asVector());
			}
		}
	}

	/**
	 * Graphic made by Jõao Figueiredo
	 * This method changes the image of the life of the enemy
	 */
	public void changeLifeStatus() {
		Map.tiles.remove(life);
		Map.gui.removeImage(life);
		double pieceOfHealth = (double) baseHealth / 8;
		ImageTile[] typesOfHealth = new ImageTile[9];
		typesOfHealth[0] = new Health1(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[1] = new Health2(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[2] = new Health3(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[3] = new Health4(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[4] = new Health5(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[5] = new Health6(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[6] = new Health7(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[7] = new Health8(new Position(this.getPosition().getX(), this.getPosition().getY()));
		typesOfHealth[8] = new Health9(new Position(this.getPosition().getX(), this.getPosition().getY()));
		int j = 0;
		for (int i = 7; i > 0; i--) {
			if (this.health > pieceOfHealth * i) {
				this.life = typesOfHealth[j];
				break;
			}
			j++;
		}
		if (this.health <= 0) {
			Map.tiles.remove(life);
			Map.gui.removeImage(life);
		} else {
			Map.tiles.add(life);
			Map.gui.addImage(life);
		}
	}

	/**
	 * @return a list containing two drops gold and a random item
	 */
	public List<ImageTile> getDrops() {
		Position newPosition = new Position(this.getPosition().getX(), this.getPosition().getY());
		List<ImageTile> drops = new ArrayList<>();
		List<Artefact> randomDrop = new ArrayList<>();
		randomDrop.add(new Key(newPosition));
		randomDrop.add(new Fire(newPosition));
		randomDrop.add(new Ration(newPosition));
		randomDrop.add(new FrozenCarpaccio(newPosition));
		randomDrop.add(new Ration(newPosition));
		randomDrop.add(new FrozenCarpaccio(newPosition));
		randomDrop.add(new Ration(newPosition));
		randomDrop.add(new FrozenCarpaccio(newPosition));
		randomDrop.add(new Ration(newPosition));
		randomDrop.add(new FrozenCarpaccio(newPosition));
		randomDrop.add(new Cloth(newPosition));
		randomDrop.add(new Potion(newPosition));
		randomDrop.add(new CurareDart(newPosition));
		randomDrop.add(new Bomb(newPosition));
		randomDrop.add(new Mace(newPosition));
		randomDrop.add(new Key(newPosition));
		randomDrop.add(new Key(newPosition));
		randomDrop.add(new Key(newPosition));
		if ("level2artefacts".equals(this.drops.toLowerCase())) {
			randomDrop.add(new Leather(newPosition));
			randomDrop.add(new Sword(newPosition));
			randomDrop.add(new MysteryMeat(newPosition));
			randomDrop.add(new GoldenKey(newPosition));
			randomDrop.add(new Potion(newPosition));
			randomDrop.add(new Mail(newPosition));
			randomDrop.add(new Mail(newPosition));
			randomDrop.add(new BattleAxe(newPosition));
			randomDrop.add(new Hammer(newPosition));
			randomDrop.add(new Boomerang(newPosition));
		}
		//To add difficulty add randomDrop.null here
		for (int i = 0; i < 40; i++) {
			randomDrop.add(null);
		}
		int count = 0;
		List<ImageTile> tiles = new ArrayList<>(Map.tiles);
		for (ImageTile tile : tiles) {
			if (tile instanceof HollowMuse) {
				count++;
			}
		}
		if (this instanceof Pig && Map.level < 2) {
			drops.add(new StairsDown(new Position(getPosition().getX(), getPosition().getY() + 1)));
		} else if (this instanceof HollowMuse && count == 0) {
			drops.add(new SkullKey(new Position(getPosition().getX(), getPosition().getY() + 1)));
		} else {
			drops.add(getGold());
			randomDrop.add(new Ration(newPosition));
			Random random = new Random();
			int r = random.nextInt((randomDrop.size() - 1) + 1);
			drops.add(randomDrop.get(r));
		}

		return drops;
		/*Position newPosition = new Position(this.getPosition().getX(), this.getPosition().getY());
		RandomBox<ImageTile> randomBox = new RandomBox<>();
		randomBox.addObject(new Key(newPosition),5);
		randomBox.addObject(new Fire(newPosition),1);
		randomBox.addObject(new Ration(newPosition),20);
		List<ImageTile> drops = new ArrayList<>();
		drops.add(getGold());
		drops.add(randomBox.getRandomObject());
		return drops;*/

	}

	public int getExperience() {
		return experience;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth() {
		return health;
	}

	public void setParalytic(boolean paralytic) {
		this.paralytic = paralytic;
	}

	@Override
	public String toString() {
		return getName() + "," + getPosition().getX() + "," + getPosition().getY() + "," + getHealth() + ";";
	}


}
