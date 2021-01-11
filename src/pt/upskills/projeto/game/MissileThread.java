package pt.upskills.projeto.game;

import pt.upskills.projeto.objects.MissileWeapon;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;

public class MissileThread extends Thread {

    private final Direction direction;
    private final MissileWeapon missile;

    /**
     * @param direction in which the hero last moved
     * @param missile   that the user wants to throw
     */
    public MissileThread(Direction direction, MissileWeapon missile) {
        this.direction = direction;
        this.missile = missile;
    }

    @Override
    public void run() {
        //Flag
        boolean control = true;
        //While the missile haven't hit an obstacle
        while (control) {
            //This continues moving it in the direction the missile is going, square by square
            //till it hits something

            Position nextPosition = missile.getPosition().plus(direction.asVector());
            missile.setPosition(nextPosition);
            try {
                //If the missile haven't hit anything it will wait 300 millisecond till it moves
                //to the next squared
                if (missile.validateImpact(nextPosition)) {
                    // FireBall continue
                    sleep(300);
                } else {
                    //When the missile hits an obstacle it will remove the missile from the gui
                    Map.gui.removeImage(missile);
                    Map.tiles.remove(missile);
                    //Internally the missile explode and if it was an enemy it caused damage
                    //After 500 millisecond we set our flag to false
                    sleep(500);
                    control = false;
                    //We remove our explosion
                    Map.gui.removeImage(missile.getExplosion());
                    Map.tiles.remove(missile.getExplosion());
                }

            } catch (InterruptedException e) {
                //Threads can cause InterruptedException
                e.printStackTrace();
            }
        }
    }
}
