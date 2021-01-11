package pt.upskills.projeto.objects;


import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.game.PowerThread;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefacts.Gold;
import pt.upskills.projeto.objects.Artefacts.Keys.SkullKey;
import pt.upskills.projeto.objects.Artefacts.Potion;
import pt.upskills.projeto.objects.NPC.NPC;
import pt.upskills.projeto.objects.RoomTexture.*;
import pt.upskills.projeto.objects.Textures.*;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class Hero implements ImageTile, Observer {

    private Direction direction;
    private Position position;
    private final Artefact[] objects = new Artefact[3];
    private final MissileWeapon[] missiles = new MissileWeapon[3];
    private int[] health = new int[4];
    private int healthBoxValue;
    private int strength;
    private int points;
    private int hit;
    private MeeleWeapon weapon;
    private int level;
    private int gold;
    private Armor armor;
    private boolean levitation;

    public Hero(Position position) {
        this.position = position;
        this.health[0] = 100;
        this.health[1] = 100;
        this.health[2] = 100;
        this.health[3] = 100;
        this.healthBoxValue = 100;
        this.strength = 11;
        this.points = 0;
        this.weapon = null;
        this.hit = 5;
        this.level = 1;
        this.gold = 0;
        this.armor = null;
        this.levitation = false;
    }


    public void setHealthBoxValue(int value) {
        this.healthBoxValue = value;
        this.health[0] = value;
        this.health[1] = value;
        this.health[2] = value;
        this.health[3] = value;
    }

    public void setPoints(int points) {
        if (points > 0) {
            System.out.println("+" + points + "xp");

        }
        if (getPoints() + points >= 0) {
            this.points += points;
        }
        if (getPoints() > 800 * getLevel()) {
            setLevel();
            setStrength(getStrength() + (level * 2));
            setHealthBoxValue(getHealthBoxValue() + (level * 2));
            setHit(getHit() + (level * 2));
            changeImageLifeStatus();
            System.out.println("Level up " + getLevel()
                    + "\nStrength: " + getStrength()
                    + "\nHealth: " + getHealthBoxValue() * 4
                    + "\nHit: " + getHit());
            Map.gui.setText(new JLabel("Level Up! level " + getLevel() + " Health: +" + getHealthBoxValue() * 4), true);
        }

    }

    public boolean checkWhatsThere(int x, int y) {
        List<ImageTile> tiles = new ArrayList<>(Map.tiles);
            for (ImageTile tile : tiles) {
                if (tile.getPosition().getX() == x &&
                        tile.getPosition().getY() == y) {
                    if (tile instanceof MotherKey) {
                        for (int i = 0; i < 3; i++) {
                            if (this.objects[i] == null) {
                                this.objects[i] = (Artefact) tile;
                                tile.setPosition(new Position(i + 7, 0));
                                Map.tiles.remove(tile);
                                Map.gui.removeImage(tile);
                                Map.gui.addStatusImage(tile);
                                setPoints(((Artefact) tile).getExperience());
                                Map.gui.setText(new JLabel(tile.getName() + " equipped! press " + (i + 1) + " to use! +" + ((Artefact) tile).getExperience()), true);
                                break;
                            }
                        }
                        return true;
                    }
                    if (tile instanceof Enemy) {
                        attack((Enemy) tile, true, null);
                        return false;
                    }

                    if (tile instanceof NPC) {
                        ((NPC) tile).act();
                        return false;
                    }

                    if (tile instanceof Door) {
                        return ((Door) tile).use();
                    }
                    if (tile instanceof Grass) {
                        ((Grass) tile).use();
                        return true;
                    }
                    if (tile instanceof Treasure) {
                        ((Treasure) tile).use();
                        return false;
                    }
                    if (tile instanceof Gold) {
                        setGold(((Gold) tile).getAmount());
                        Map.tiles.remove(tile);
                        Map.gui.removeImage(tile);
                        return false;
                    }
                    if (tile instanceof MissileWeapon) {
                        for (int i = 0; i < 3; i++) {
                            if (this.missiles[i] == null) {
                                this.missiles[i] = (MissileWeapon) tile;
                                tile.setPosition(new Position(i, 0));
                                Map.tiles.remove(tile);
                                Map.gui.removeImage(tile);
                                Map.gui.addStatusImage(tile);
                                setPoints(((MissileWeapon) tile).getExperience());
                                Map.gui.setText(new JLabel(tile.getName() + " equipped! press " + (i + 1) + " to use! +" + ((MissileWeapon) tile).getExperience()), true);
                                break;
                            }
                        }
                        return true;
                    }
                    if (tile instanceof Artefact && !(tile instanceof  Trap)) {
                        for (int i = 0; i < 3; i++) {
                            if (this.objects[i] == null) {
                                this.objects[i] = (Artefact) tile;
                                tile.setPosition(new Position(i + 7, 0));
                                Map.tiles.remove(tile);
                                Map.gui.removeImage(tile);
                                Map.gui.addStatusImage(tile);
                                setPoints(((Artefact) tile).getExperience());
                                Map.gui.setText(new JLabel(tile.getName() + " equipped! press " + (i + 1) + " to use! +" + ((Artefact) tile).getExperience()), true);
                                break;
                            }
                        }
                    }
                    if (tile instanceof Trap && !levitation) {
                        PowerThread powerThread = new PowerThread((Powerful) tile);
                        powerThread.start();
                    }
                    if (tile instanceof Obstacle) {
                        if (tile instanceof Black && levitation || tile instanceof Chasm && levitation) {
                            return true;
                        }
                        Map.gui.setText(new JLabel("Can't pass " + tile
                                .getName() + " in the way"), false);
                        return false;
                    }
                    if (tile instanceof StairsDown) {
                        for (ImageTile tile1 : Map.tiles) {
                            if (tile1 instanceof Enemy) {
                                return false;
                            }
                        }
                        this.setPosition(new Position(5, 4));
                        Map.tiles.remove(this);
                        Map.gui.removeImage(this);
                        if (Map.level + 1 == 3) {
                            Map.fileGenerator.scoreFile(getPoints());
                            Map.fileGenerator.roomScoreFile(String.valueOf(getPoints()), false);
                        }
                        String room = Map.level + 1 + "_0";
                        Map.setRoom(room);
                        return true;
                    }



                }
            }

            return true;

    }

    /**
     * @param enemy   to attack
     * @param flag    true if is a weapon attack, false if is a missile attack
     * @param missile the missile that makes the attack
     */
    public void attack(Enemy enemy, boolean flag, Artefact missile) {
        Map.moveEnemies();

        int enemyLife = enemy.getHealth();
        Random random = new Random();
        //Random().nextInt(int bound) generates a random integer from i (inclusive) to f (exclusive)
        //To include the last value we add 1
        int attack;
        if (flag) {
            if (getWeapon() != null) {
                attack = random.nextInt(((getWeapon().getHitDamage() - getWeapon().getMinimumHit()) + 1)) + getWeapon().getMinimumHit();
            } else {
                attack = random.nextInt(((getHit() - 2) + 1)) + 2;
            }
            Map.gui.setText(new JLabel("You attacked " + enemy.getName() + " damage -" + attack), true);
        } else {
            attack = random.nextInt(((missile.getHitDamage() - missile.getMinimumHit()) + 1)) + missile.getMinimumHit();
            Map.gui.setText(new JLabel(missile.getName() + " hit " + enemy.getName() + " damage -" + attack), true);
        }
        if (enemyLife - attack > 0) {
            enemy.setHealth(enemyLife - attack);
        }
        if (enemyLife - attack <= 0) {
            enemy.setHealth(enemyLife - attack);
            Map.tiles.remove(enemy);
            Map.gui.removeImage(enemy);
            points += enemy.getExperience();
            if (enemy.getDrops().size() == 1) {
                if (enemy.getDrops().get(0) instanceof SkullKey) {
                    SkullKey special = (SkullKey) enemy.getDrops().get(0);
                    special.setPosition(new Position(enemy.getPosition().getX(), enemy.getPosition().getY()));
                    Map.tiles.add(special);
                    Map.gui.addImage(special);
                } else {
                    StairsDown special = (StairsDown) enemy.getDrops().get(0);
                    special.setPosition(new Position(enemy.getPosition().getX(), enemy.getPosition().getY()));
                    Map.tiles.add(special);
                    Map.gui.addImage(special);
                }

            } else {
                //Gold drop
                Gold gold = (Gold) enemy.getDrops().get(0);
                gold.setPosition(new Position(enemy.getPosition().getX(), enemy.getPosition().getY()));
                Map.tiles.add(gold);
                Map.gui.addImage(gold);
                setGold(gold.getAmount());
                //Other drop
                try {
                    if (enemy.getDrops().get(1) != null) {
                        Artefact object = (Artefact) enemy.getDrops().get(1);
                        object.setPosition(new Position(enemy.getPosition().getX(), enemy.getPosition().getY()));
                        Map.tiles.add(object);
                        Map.gui.addImage(object);
                        Map.gui.setText(new JLabel(enemy.getName() + " dropped " + object.getName()), true);
                    }
                } catch (NullPointerException e) {
                    System.out.println("The enemy dropped null");
                }
            }
            Map.gui.setText(new JLabel(enemy.getName() + " was killed! +" + enemy.getExperience()), true);
            setPoints(enemy.getExperience());
        }
        enemy.changeLifeStatus();


    }

    public void changeImageLifeStatus() {
        int[] heroHealth = getHealth();
        for (int i = 3; i >= 0; i--) {
            if (heroHealth[i] <= getHealthBoxValue() / 2 && heroHealth[i] > 0) {
                Map.gui.addStatusImage(new RedGreen(new Position(i + 3, 0)));
            } else if (heroHealth[i] <= 0) {
                Map.gui.addStatusImage(new Red(new Position(i + 3, 0)));
            } else if (heroHealth[i] > getHealthBoxValue() / 2) {
                Map.gui.addStatusImage(new Green(new Position(i + 3, 0)));
            }
        }
        if (heroHealth[0] <= 0) {
            Map.gui.clearImages();
            Map.tiles.clear();
            setPoints(gold);
            Map.gameOver = false;
            Map.gui.addStatusImage(new Red(new Position(3, 0)));
            Map.setRoom("666");
            Map.tiles.add(new Black(new Position(0, 0)));
            Map.fileGenerator.scoreFile(getPoints());
        }
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * This method is called whenever the observed object is changed. This function is called when an
     * interaction with the graphic component occurs {{@link ImageMatrixGUI}}
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     *            *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        Integer keyCode = (Integer) arg;
        if (keyCode == KeyEvent.VK_S) {
            Map.fileGenerator.saveGame();
        }
        if (keyCode == KeyEvent.VK_L) {
            //TODO loadGame sometimes causes bugs with the items
            Map.fileGenerator.loadGame();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (Map.gameOver) {
                this.position = new Position(3, 6);
                try {
                    Map.setRoom("0_1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map.initialStatus();
            } else {
                Map.tiles.remove(this);
                Map.gui.removeImage(this);
                Map.setRoom("score");
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            int i = 0;
            for (MissileWeapon missile : missiles) {
                if (missile != null) {
                    missile.use(i);
                    break;
                }
                i++;
            }
        }


        if (Map.gameOver) {
            if (keyCode == KeyEvent.VK_DOWN) {
                if (getPosition().getY() <= 8 && checkWhatsThere(this.position.getX(), this.position.getY() + 1)) {
                    position = position.plus(Direction.DOWN.asVector());
                    Map.moveEnemies();
                    setPoints(-1);
                    setDirection(Direction.DOWN);
                }

            }
            if (keyCode == KeyEvent.VK_UP) {
                if (getPosition().getY() > 0 && checkWhatsThere(this.position.getX(), this.position.getY() - 1)) {
                    position = position.plus(Direction.UP.asVector());
                    Map.moveEnemies();
                    setPoints(-1);
                    setDirection(Direction.UP);
                }

            }
            if (keyCode == KeyEvent.VK_LEFT) {
                if (getPosition().getX() > 0 && checkWhatsThere(this.position.getX() - 1, this.position.getY())) {
                    position = position.plus(Direction.LEFT.asVector());
                    Map.moveEnemies();
                    setPoints(-1);
                    setDirection(Direction.LEFT);
                }
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                if (getPosition().getX() <= 8 && checkWhatsThere(this.position.getX() + 1, this.position.getY())) {
                    position = position.plus(Direction.RIGHT.asVector());
                    Map.moveEnemies();
                    setPoints(-1);
                    setDirection(Direction.RIGHT);
                }
            }
        }

        if (keyCode == KeyEvent.VK_NUMPAD1) {
            if (objects[0] != null) {
                try {
                    if (objects[0].use(0)) {
                        System.out.println("Object used!");
                    } else {
                        this.objects[0].throwObject(0, true);
                    }
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD2) {
            if (objects[1] != null) {
                try {
                    if (objects[1].use(1)) {
                        System.out.println("Object used!");
                    } else {
                        this.objects[1].throwObject(1, true);
                    }
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD3) {
            if (objects[2] != null) {
                try {
                    if (objects[2].use(2)) {
                        System.out.println("Object used!");
                    } else {
                        this.objects[2].throwObject(2, true);
                    }
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD4) {
            if (objects[0] != null && objects[0] instanceof Potion) {
                try {
                    objects[0].throwObject(0,false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD5) {
            if (objects[1] != null && objects[1] instanceof Potion) {
                try {
                    objects[1].throwObject(1,false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        }
        if (keyCode == KeyEvent.VK_NUMPAD6) {
            if (objects[2] != null && objects[2] instanceof Potion) {
                try {
                    objects[2].throwObject(0,false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        }
        if (keyCode == KeyEvent.VK_Q) {
            if (objects[0] != null) {
                try {
                    Map.gui.removeStatusImage(objects[0]);
                    Map.gui.setText(new JLabel(objects[0].getName() + " has been eliminated"), true);
                    objects[0] = null;
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        }
        if (keyCode == KeyEvent.VK_W) {
            if (objects[1] != null) {
                try {
                    Map.gui.removeStatusImage(objects[1]);
                    Map.gui.setText(new JLabel(objects[1].getName() + " has been eliminated"), true);
                    objects[1] = null;
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
        if (keyCode == KeyEvent.VK_E) {
            if (objects[2] != null) {
                try {
                    Map.gui.removeStatusImage(objects[2]);
                    Map.gui.setText(new JLabel(objects[2].getName() + " has been eliminated"), true);
                    objects[2] = null;
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }

        if (keyCode == KeyEvent.VK_1) {
            if (missiles[0] != null) {
                try {
                    missiles[0].throwObject(0, false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

            }
        }
        if (keyCode == KeyEvent.VK_2) {
            if (missiles[1] != null) {
                try {
                    missiles[1].throwObject(1, false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
        if (keyCode == KeyEvent.VK_3) {
            if (missiles[2] != null) {
                try {
                    missiles[2].throwObject(2, false);
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }
    }

    public int getStrength() {
        return strength;
    }

    public int getPoints() {
        return points;
    }

    public ImageTile getObject(int index) {
        return this.objects[index];
    }

    public int[] getHealth() {
        return health;
    }

    public int getHealthBoxValue() {
        return healthBoxValue;
    }

    public int getHit() {
        return hit;
    }

    public MeeleWeapon getWeapon() {
        return weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public MissileWeapon getMissiles(int indexArtefact) {
        return missiles[indexArtefact];
    }

    public int getLevel() {
        return level;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getGold() {
        return gold;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHealth(int[] health) {
        this.health = health;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public void setWeapon(MeeleWeapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public void setMissiles(int index, MissileWeapon missileWeapon) {
        this.missiles[index] = missileWeapon;
    }

    public void setObject(int index, Artefact artefact) {
        this.objects[index] = artefact;
    }

    public void setGold(int gold) {
        this.gold += gold;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLevel() {
        this.level++;
    }

    public void setAbsoluteLevel(int level) {
        this.level = level;
    }

    public void setAbsolutePoints(int points) {
        this.points = points;
    }

    public void setAbsoluteHealthBoxValue(int health) {
        this.healthBoxValue = health;
    }

    public void setLevitation(Boolean levitation) {
        this.levitation = levitation;
    }


    private String objectsToString() {
        StringBuilder objects = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (getObject(i) == null) {
                objects.append("null,");
                objects.append("null,");
                objects.append("null,");
            } else {
                objects.append(getObject(i).getName()).append(",");
                objects.append(getObject(i).getPosition().getX()).append(",");
                objects.append(getObject(i).getPosition().getY()).append(",");
            }
        }
        return objects.toString();
    }

    private String missilesToString() {
        StringBuilder objects = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (getMissiles(i) == null) {
                objects.append("null,");
                objects.append("null,");
                objects.append("null,");
            } else {
                objects.append(getMissiles(i).getName()).append(",");
                objects.append(getMissiles(i).getPosition().getX()).append(",");
                objects.append(getMissiles(i).getPosition().getY()).append(",");
            }
        }
        return objects.toString();
    }


    @Override

    public String toString() {
        try {
            return getName() + "," + getPosition().getX() + "," + getPosition().getY()
                    + "," + objectsToString()
                    + missilesToString()
                    + health[0] + "," + health[1] + "," + health[2] + "," + health[3] + "," + getHealthBoxValue()
                    + "," + getStrength() + "," + getPoints() + "," + getHit() + "," + getLevel() + "," + getGold() + ","
                    + getWeapon().getName() + "," + getArmor().getName() + ";";
        } catch (NullPointerException e) {
            System.out.println("The hero had nulls inside him");
            return getName() + "," + getPosition().getX() + "," + getPosition().getY()
                    + "," + objectsToString()
                    + missilesToString()
                    + health[0] + "," + health[1] + "," + health[2] + "," + health[3] + "," + getHealthBoxValue()
                    + "," + getStrength() + "," + getPoints() + "," + getHit() + "," + getLevel() + "," + getGold() + ","
                    + "null,null";
        }
    }


}

