package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefacts.Gold;
import pt.upskills.projeto.objects.Artefacts.Keys.SkullKey;
import pt.upskills.projeto.objects.Artefacts.Missiles.Bomb;
import pt.upskills.projeto.objects.Artefacts.Potion;
import pt.upskills.projeto.objects.Enemies.Bat;
import pt.upskills.projeto.objects.RoomTexture.StairsDown;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Powers extends Artefact implements Powerful {


    public Powers(int price, int sell, int experience) {
        super(price, sell, experience);
    }

    protected abstract void setHitDamage(int i);

    protected abstract void setTime(int i);

    public void toxicGas() {
        this.setTime(500);
        this.setHitDamage(5);
        this.setExplosion("toxicGas");
        Map.gui.setText(new JLabel("Toxic gas Activated"), false);
    }

    public void liquidFlame() {
        this.setTime(500);
        this.setHitDamage(10);
        this.setExplosion("flame");
        if (this instanceof Potion){
            this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        }
        Map.gui.setText(new JLabel("Liquid flame activated"), false);
    }

    public void paralyticGas() {
        this.setTime(500);
        this.setHitDamage(0);
        this.setExplosion("paralyticGas");
        for (ImageTile tile : Map.tiles) {
            if (tile instanceof Enemy) {
                ((Enemy) tile).setParalytic(true);
            }
        }
        Map.gui.setText(new JLabel("Paralytic gas Activated"), false);
    }

    public void poison() {
        this.setTime(500);
        this.setHitDamage(5);
        this.setExplosion("poison");
        if (this instanceof Potion){
            this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        }
        Map.gui.setText(new JLabel("Poison gas Activated"), false);
    }

    public void lightning() {
        this.setTime(500);
        this.setHitDamage(Map.hero.getHealthBoxValue());
        this.setExplosion("lightning");
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(), Map.hero.getPosition().getY()));
        Map.gui.setText(new JLabel("Lighting Activated"), false);
    }

    public void summoning() {
        this.setTime(500);
        this.setHitDamage(0);
        this.setExplosion("summoning");
        Bat bat = new Bat(new Position(this.getPosition().getX(), this.getPosition().getY()));
        Map.gui.addImage(bat);
        Map.tiles.add(bat);
        Map.gui.setText(new JLabel("Summoning gas Activated"), false);
    }

    public void experience() {
        Map.hero.setPoints(900);
        this.setHitDamage(0);
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        this.setExplosion("chispitas");
        Map.gui.setText(new JLabel("Potion of experience, you have level up"), true);
    }

    public void strength() {
        Map.hero.setStrength(Map.hero.getStrength() + 1);
        this.setHitDamage(0);
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        this.setExplosion("chispitas");
        Map.gui.setText(new JLabel("Potion of strength, you are now stronger"), true);
    }

    public void healing() {
        Map.hero.setHealthBoxValue(Map.hero.getHealthBoxValue());
        this.setHitDamage(0);
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        this.setExplosion("chispitas");
        Map.gui.setText(new JLabel("Potion of healing, you feel fresh as a lettuce"), true);
    }


    public void might() {
        this.setTime(500);
        this.setHitDamage(0);
        Map.hero.setHealthBoxValue(Map.hero.getHealthBoxValue() + 1);
        Map.hero.setStrength(Map.hero.getStrength() + 1);
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        this.setExplosion("chispitas");
        Map.gui.setText(new JLabel("Potion of might, you feel better suddenly"), true);
    }

    public void levitation() {
        Map.hero.setLevitation(true);
        this.setHitDamage(0);
        this.getExplosion().setPosition(new Position(Map.hero.getPosition().getX(),Map.hero.getPosition().getY()));
        this.setExplosion("chispitas");
        Map.gui.setText(new JLabel("Potion of levitation, ain't no trap stopping me... for now"), true);
    }

    public void activateBomb() {
        if (!(this.getName().equals("Trap_triggered_prison")) && !(this.getName().equals("Trap_triggered_sewers"))) {
            if (this.getHitDamage() > 0) {
                List<ImageTile> tiles = new ArrayList<>(Map.tiles);
                for (ImageTile tile : tiles) {
                    int dx = this.getPosition().getX() - tile.getPosition().getX();
                    int dy = this.getPosition().getY() - tile.getPosition().getY();
                    if (Math.abs(dx) <= 4 && Math.abs(dy) <= 4) {
                        if (tile instanceof Enemy) {
                            attackEnemy((Enemy) tile);
                        }
                        if (tile instanceof Hero) {
                            attackHero();
                        }

                    }
                }
            }
        }
    }

    public void attackHero() {

        Random random = new Random();
        int originalAttack = (random.nextInt((this.getHitDamage()) + 1));
        int attack;
        if (Map.hero.getArmor() != null) {
            int defend = random.nextInt(Map.hero.getArmor().getHitDamage());
            attack = Math.max(originalAttack - defend, 0);
            if (this instanceof Potion || this instanceof Bomb) {
                Map.gui.setText(new JLabel("You hurt yourself in your confusion but your armor adsorbed " + defend + " final attack " + attack), false);
            } else {
                Map.gui.setText(new JLabel(this.getName() + " but your armor adsorbed " + defend + " final attack " + attack), false);
            }

        } else {
            attack = originalAttack;
            if (this instanceof Potion || this instanceof Bomb) {
                Map.gui.setText(new JLabel("You hurt yourself in your confusion final attack " + attack), false);
            } else {
                Map.gui.setText(new JLabel(this.getName() + " attacked " + attack), false);
            }

        }
        int i;
        int[] heroLife = Map.hero.getHealth();
        for (i = 3; i > 0; i--) {
            if (heroLife[i] > 0) {
                break;
            }
        }
        if (heroLife[i] >= attack) {
            heroLife[i] -= attack;
            Map.hero.setHealth(heroLife);
            Map.hero.changeImageLifeStatus();

        } else if (i >= 1 && heroLife[i - 1] >= attack) {
            int secondAttack = attack - heroLife[i];
            heroLife[i] = 0;
            heroLife[i - 1] -= secondAttack;
            Map.hero.setHealth(heroLife);
            Map.hero.changeImageLifeStatus();
        } else {
            heroLife[i] = 0;
            Map.hero.setHealth(heroLife);
            Map.hero.changeImageLifeStatus();
            Map.gui.setText(new JLabel(""), false);
        }
    }

    private void attackEnemy(Enemy enemy) {
        Map.moveEnemies();
        int enemyLife = enemy.getHealth();
        Random random = new Random();
        int attack;
        attack = random.nextInt((this.getHitDamage()) + 1);
        Map.gui.setText(new JLabel(this.getName() + " hit " + enemy.getName() + " damage -" + attack), true);
        if (enemyLife - attack > 0) {
            enemy.setHealth(enemyLife - attack);
        }
        //Drops and life
        if (enemyLife - attack <= 0) {
            enemy.setHealth(enemyLife - attack);
            Map.tiles.remove(enemy);
            Map.gui.removeImage(enemy);
            Map.hero.setPoints(enemy.getExperience());
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
                Map.hero.setGold(gold.getAmount());
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
            Map.hero.setPoints(enemy.getExperience());
        }
        enemy.changeLifeStatus();
    }

    public abstract long getTime();


    public void activatePower() {
        switch (this.getPower()) {
            case "toxicGas":
                toxicGas();
                break;
            case "liquidFlame":
                liquidFlame();
                break;
            case "paralyticGas":
                paralyticGas();
                break;
            case "poison":
                poison();
                break;
            case "lightning":
                lightning();
                break;
            case "summoning":
                summoning();
                break;
            case "levitation":
                levitation();
                break;
            case "healing":
                healing();
                break;
            case "might":
                might();
                break;
            case "experience":
                experience();
                break;
            default:
                strength();
                break;
        }
    }

    protected abstract String getPower();


}
