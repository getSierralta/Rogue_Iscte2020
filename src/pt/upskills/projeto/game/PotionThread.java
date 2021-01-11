package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.objects.Powerful;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

public class PotionThread extends Thread {

    private final Powerful powerful;
    private final Direction direction;
    private int i = 0;


    public PotionThread(Powerful powerful, Direction direction) {
        this.powerful = powerful;
        this.direction = direction;
    }

    @Override
    public void run() {
        //Flag
        boolean control = true;
        try {
            powerful.activatePower();
            Map.gui.addImage(powerful.getExplosion());
        }catch (Exception e){
            e.printStackTrace();
        }
        while (control) {
            Position nextPosition = powerful.getPosition().plus(direction.asVector());
            powerful.setPosition(nextPosition);
            try {
                if (powerful.validateImpact(nextPosition)) {
                    sleep(300);
                } else {
                    Map.gui.removeImage((ImageTile) powerful);
                    Map.tiles.remove(powerful);
                    powerful.activatePower();
                    if (powerful.getExplosion().getName().equals("toxicGas") || powerful.getExplosion().getName().equals("flame")
                    || powerful.getExplosion().getName().equals("paralyticGas") || powerful.getExplosion().getName().equals("poison")) {
                        powerful.activateBomb();
                    } else {
                        powerful.attackHero();
                    }
                    sleep(500);
                    control = false;
                    powerful.changeStatus();
                    Map.gui.removeImage(powerful.getExplosion());
                    Map.tiles.remove(powerful.getExplosion());
                    if (powerful.getExplosion().getName().equals("paralyticGas")) {
                        for (ImageTile tile : Map.tiles) {
                            if (tile instanceof Enemy) {
                                ((Enemy) tile).setParalytic(false);
                            }
                        }
                    }
                }

            } catch (InterruptedException e) {
                //Threads can cause InterruptedException
                e.printStackTrace();
            }
        }
    }
}
