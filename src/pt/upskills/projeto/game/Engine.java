package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageMatrixGUI;

public class Engine {
    /**
     * Set's the original configurations of the room
     * And keep updating the gui till the game is finished
     */
    public void init(){
        //We instantiate the gui
        Map.gui = ImageMatrixGUI.getInstance();
        //We set the name of our gui
        Map.gui.setName("Max");
        //We start in the loading scream
        Map.setRoom("0");
        //We add the new images to the gui
        Map.gui.newImages(Map.tiles);
        //We add our hero as the observer of the game
        Map.gui.addObserver(Map.hero);
        //We set the life status
        Map.messageStatus();
        Map.messageStatus2();
        //Makes the window visible
        Map.gui.go();
        //Keeps updating the gui
        while (Map.gameOver){
            Map.gui.update();
        }
    }


    /**
     *Instantiate the engine, set the game to start
     */
    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }

}
