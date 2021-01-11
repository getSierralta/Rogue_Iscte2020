package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Enemy;
import pt.upskills.projeto.objects.Powerful;

public class PowerThread extends Thread {

    private final Powerful powerful;
    private int i = 0;


    public PowerThread(Powerful powerful) {
        this.powerful = powerful;
    }

    @Override
    public void run() {
        if (!(powerful.getName().equals("Trap_triggered_prison")) && !(powerful.getName().equals("Trap_triggered_sewers"))) {
            boolean control = true;
            try {
                powerful.activatePower();
                Map.gui.addImage(powerful.getExplosion());
            }catch (Exception e){
                e.printStackTrace();
            }
            while (control) {
                try {
                    if (i < 5) {
                        powerful.activatePower();
                        powerful.activateBomb();
                        sleep(powerful.getTime());
                        i++;
                        if(powerful.getExplosion().getName().equals("lightning")){
                            control = false;
                            powerful.changeStatus();
                            Map.gui.removeImage(powerful.getExplosion());
                        }
                    } else {
                        sleep(500);
                        control = false;
                        powerful.changeStatus();
                        Map.gui.removeImage(powerful.getExplosion());
                        if (powerful.getExplosion().getName().equals("paralyticGas")){
                            for (ImageTile tile: Map.tiles) {
                                if(tile instanceof Enemy){
                                    ((Enemy) tile).setParalytic(false);
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
