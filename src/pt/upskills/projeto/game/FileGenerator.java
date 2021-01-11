package pt.upskills.projeto.game;


import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.*;
import pt.upskills.projeto.objects.Artefacts.*;
import pt.upskills.projeto.objects.Artefacts.Armor.*;
import pt.upskills.projeto.objects.Artefacts.Food.*;
import pt.upskills.projeto.objects.Artefacts.Keys.GoldenKey;
import pt.upskills.projeto.objects.Artefacts.Keys.Key;
import pt.upskills.projeto.objects.Artefacts.Keys.SkullKey;
import pt.upskills.projeto.objects.Artefacts.Missiles.Bomb;
import pt.upskills.projeto.objects.Artefacts.Missiles.Boomerang;
import pt.upskills.projeto.objects.Artefacts.Missiles.CurareDart;
import pt.upskills.projeto.objects.Artefacts.Missiles.Fire;
import pt.upskills.projeto.objects.Artefacts.Weapons.*;
import pt.upskills.projeto.objects.Enemies.*;
import pt.upskills.projeto.objects.NPC.Megumi;
import pt.upskills.projeto.objects.NPC.SadGhost;
import pt.upskills.projeto.objects.RoomTexture.*;
import pt.upskills.projeto.objects.Textures.Black;
import pt.upskills.projeto.objects.Textures.Green;
import pt.upskills.projeto.objects.Textures.Red;
import pt.upskills.projeto.objects.Textures.RedGreen;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileGenerator {
    //TODO REMEMBER TO ADD THINGS HERE WHEN YOU ADD NEW THINGS TO THE MAP
    //This field maximumScore help us find the maximum score in the findMaximumScore Method
    private int maximumScore;

    public FileGenerator() {
        this.maximumScore = 1;
    }

    /**
     * @return the maximum score in the FileGenerator
     */
    public int getMaximumScore() {
        return maximumScore;
    }

    /**
     * @param maximumScore new Maximum score
     */
    private void setMaximumScore(int maximumScore) {
        this.maximumScore = maximumScore;
    }

    /**
     * Read the leaderboard file and files the maximum score
     */
    private void findMaximumScore() {
        //Paths the leaderboard
        File file = new File("scores/leaderboard.txt");
        try {
            Scanner scanner = new Scanner(file);
            String[] newScore;
            //For each line in the file we check if the score if greater than the maximum score
            //in the FileGenerator at the end we get the maximum score recorded
            while (scanner.hasNext()) {
                newScore = scanner.nextLine().split(":");
                try {
                    //Gets the actual record and compares it to the new one the bigger gets
                    //saved in the maximumScore field
                    int otherScore = Integer.parseInt(newScore[1]);
                    setMaximumScore(Math.max(maximumScore, otherScore));
                } catch (NumberFormatException e) {
                    //If newScore[1] is not a number it can cause and exception
                    e.printStackTrace();
                }
            }
            //We close the scanner to avoid corruption
            scanner.close();
        } catch (FileNotFoundException e) {
            //If the file doesn't exits it can throw a FileNotFoundException
            e.printStackTrace();
        }
    }

    /**
     * It adds the new score in the leaderBoard
     *
     * @param score the score made in this game
     */
    public void scoreFile(int score) {
        //We invoke the roomScoreFile method and pass it the new score as a String
        roomScoreFile(String.valueOf(score), true);
        //We path the file
        File file = new File("scores/leaderboard.txt");
        //We create a new array list to save all the values in the file to avoid losing the information
        List<String> scores = new ArrayList<>();
        try {
            //We read the file and add each line to our list
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                scores.add(scanner.nextLine());
            }
            //We close the file to avoid corruption
            scanner.close();
        } catch (FileNotFoundException e) {
            //This can cause a FileNotFound exception if the file doesn't exist
            e.printStackTrace();
        }
        //We now rewrite the file with all the previous scores and adding the new score at the end
        try {
            PrintWriter writer = new PrintWriter(file);
            //We add all the previous information
            for (String scoreLine : scores) {
                writer.println(scoreLine);
            }
            //We add the new score [It is important to keep the format of ":" followed by the score
            //No spaces between for the findMaximumScore method to function properly
            //In case of edition it most edit this method, the findMaximumScore method
            //And all of the old scores, they three need to match
            writer.println("Game #" + scores.size() + " score:" + score);
            //We close the file to avoid corruption
            writer.close();
        } catch (FileNotFoundException e) {
            //This can cause a FileNotFound exception if the file doesn't exist
            e.printStackTrace();
        }
    }

    /**
     * The last screen of the game is the score screen, this method create the file used to
     * display this screen, it creates a file that contains the new score and the maximum score
     * formatted to match the numbers codes in the images tile codification
     *
     * @param flag  if is the score room or not
     * @param score the String value of the new score
     */
    public void roomScoreFile(String score, boolean flag) {
        File file;
        if (flag) {
            //We path the file
            file = new File("rooms/roomscore.txt");
        } else {
            file = new File("rooms/room3_0.txt");
        }

        //We create a Array for all of our lines in the file (10)
        String[] scores = new String[10];
        try {
            //We read each line of the file and save it in the array
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                scores[i] = scanner.nextLine();
                i++;
            }
            //We close the file to avoid corruption
            scanner.close();
        } catch (FileNotFoundException e) {
            //This ca cause a FileNotFoundException if the file doesn't exist
            e.printStackTrace();
        }
        if (flag) {
            //TODO this need to be revisited for the case of the score being bigger than 5 spaces
            //TODO cause it will overflow the screen
            //We made an array of all the values of our score
            String[] myScores = score.split("");
            //The line 4 in the array scores contains the space in which we display the score information
            String[] lineMyScore = scores[4].split("");
            //We call the method getNumbers which codify the numbers and then puts the split all together again
            scores[4] = getNumbers(myScores, lineMyScore);
            //We call the function findMaximumScore that will set the field maximumScore to the actual maximumScore
            findMaximumScore();
            //We made and array that will contain all the numbers of the maximumScore as a string
            String[] myHighScores = String.valueOf(getMaximumScore()).split("");
            //in scores[6] we display the maximumScore so we made and array that contains each character in that line
            String[] lineMyHighScores = scores[6].split("");
            //We call the getNumbers method to codify the maximumScore
            scores[6] = getNumbers(myHighScores, lineMyHighScores);
        } else {
            //We made an array of all the values of our score
            String[] myScores = score.split("");
            //The line 4 in the array scores contains the space in which we display the score information
            String[] lineMyScore = scores[7].split("");
            //We call the method getNumbers which codify the numbers and then puts the split all together again
            scores[7] = getNumbers(myScores, lineMyScore);
        }

        try {
            //Finally we reWrite the file with the new information in it
            PrintWriter writer = new PrintWriter(file);
            for (String scoreLine : scores) {
                writer.println(scoreLine);
            }
            //We close our file to avoid corruption
            writer.close();
        } catch (FileNotFoundException e) {
            //This can cause a fileNotFoundException if the file doesn't exist
            e.printStackTrace();
        }
    }


    /**
     * If the file is open this method doesn't work
     * This method takes all the information from our map and saves it into a file
     */
    public void saveGame() {

        File map = new File("lastGame/Map.txt");
        try {
            PrintWriter writer = new PrintWriter(map);
            writer.println(Map.nowRoom + "-" + Map.level + "-" + Map.hero.toString());
            Map.roomMap.forEach((String, Room) -> writer.println(Map.roomMap.get(String)));
            writer.close();
            System.out.println("Game saved");
            Map.gui.setText(new JLabel("Game saved :D"), true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method read the lastGame/Map.txt and initiates the whole map
     * the way it was in the last game
     */
    public void loadGame() {
        Map.hero.setLevitation(false);
        File file = new File("lastGame/Map.txt");
        String nowRoom = "0";
        try {
            Map.gui.clearImages();
            Map.gui.clearStatus();
            Map.gameOver = true;
            Map.messageStatus();
            Map.roomMap.clear();
            boolean flag = true;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                //We only do this for the first line
                if (flag) {
                    String[] map = scanner.nextLine().split("-");
                    //We set the room to the last room we were
                    Map.nowRoom = map[0];
                    nowRoom = map[0];
                    //We set the level to the last level
                    try {
                        Map.level = Integer.parseInt(map[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("Problem parsing the level");
                    }
                    //We split our hero
                    String[] hero = map[2].split(",");
                    try {
                        int x = Integer.parseInt(hero[1]);
                        int y = Integer.parseInt(hero[2]);
                        Map.hero.setPosition(new Position(x, y));
                    } catch (NumberFormatException e) {
                        System.out.println("Problem parsing the position of the hero");
                    }
                    //We set the artefacts of our hero
                    try {
                        //We take two securities, theres a chance our number will have a ; next to it so we replace it
                        //We put it inside a try catch in case the artefact is null
                        //It doesn't crash the application
                        //We verify if it isn't a floor (getTile can return a Floor)
                        if (!(getTile(hero[3], Integer.parseInt(hero[4].replaceAll(";", "")), Integer.parseInt(hero[5].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            Artefact obj = (Artefact) getTile(hero[3], Integer.parseInt(hero[4].replaceAll(";", "")), Integer.parseInt(hero[5].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setObject(0, obj);
                            Map.gui.removeStatusImage(Map.hero.getObject(0));
                            Map.gui.addStatusImage(obj);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("There's no artefact 0");
                        Map.hero.setObject(0, null);
                        Map.gui.removeStatusImage(Map.hero.getObject(0));
                        Map.gui.addStatusImage(new Black(new Position(7, 0)));
                    }
                    try {
                        if (!(getTile(hero[6], Integer.parseInt(hero[7].replaceAll(";", "")), Integer.parseInt(hero[8].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            Artefact obj = (Artefact) getTile(hero[6], Integer.parseInt(hero[7].replaceAll(";", "")), Integer.parseInt(hero[8].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setObject(1, obj);
                            Map.gui.removeStatusImage(Map.hero.getObject(1));
                            Map.gui.addStatusImage(obj);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("There's no artefact 1");
                        Map.hero.setObject(1, null);
                        Map.gui.removeStatusImage(Map.hero.getObject(1));
                        Map.gui.addStatusImage(new Black(new Position(8, 0)));
                    }
                    try {
                        if (!(getTile(hero[9], Integer.parseInt(hero[10].replaceAll(";", "")), Integer.parseInt(hero[11].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            Artefact obj = (Artefact) getTile(hero[9], Integer.parseInt(hero[10].replaceAll(";", "")), Integer.parseInt(hero[11].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setObject(2, obj);
                            Map.gui.removeStatusImage(Map.hero.getObject(2));
                            Map.gui.addStatusImage(obj);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("There's no artefact 2");
                        Map.hero.setObject(2, null);
                        Map.gui.removeStatusImage(Map.hero.getObject(2));
                        Map.gui.addStatusImage(new Black(new Position(9, 0)));
                    }
                    //We set our missiles
                    try {
                        //We take the same security measures as our artefacts
                        if (!(getTile(hero[12], Integer.parseInt(hero[13].replaceAll(";", "")), Integer.parseInt(hero[14].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            MissileWeapon obj1 = (MissileWeapon) getTile(hero[12], Integer.parseInt(hero[13].replaceAll(";", "")), Integer.parseInt(hero[14].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setMissiles(0, obj1);
                            Map.gui.removeStatusImage(Map.hero.getMissiles(0));
                            Map.gui.addStatusImage(obj1);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("There's no missile 0");
                        Map.hero.setMissiles(0, null);
                        Map.gui.removeStatusImage(Map.hero.getMissiles(0));
                        Map.gui.addStatusImage(new Black(new Position(0, 0)));
                    }
                    try {

                        if (!(getTile(hero[15], Integer.parseInt(hero[16].replaceAll(";", "")), Integer.parseInt(hero[17].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            MissileWeapon obj1 = (MissileWeapon) getTile(hero[15], Integer.parseInt(hero[16].replaceAll(";", "")), Integer.parseInt(hero[17].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setMissiles(1, obj1);
                            Map.gui.removeStatusImage(Map.hero.getMissiles(1));
                            Map.gui.addStatusImage(obj1);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("There's no missile 1");
                        Map.hero.setMissiles(1, null);
                        Map.gui.removeStatusImage(Map.hero.getMissiles(1));
                        Map.gui.addStatusImage(new Black(new Position(1, 0)));
                    }
                    try {
                        if (!(getTile(hero[18], Integer.parseInt(hero[19].replaceAll(";", "")), Integer.parseInt(hero[20].replaceAll(";", "")), 0, false, null, 0, 0, null) instanceof Floor)) {
                            MissileWeapon obj1 = (MissileWeapon) getTile(hero[18], Integer.parseInt(hero[19].replaceAll(";", "")), Integer.parseInt(hero[20].replaceAll(";", "")), 0, false, null, 0, 0, null);
                            Map.hero.setMissiles(2, obj1);
                            Map.gui.removeStatusImage(Map.hero.getMissiles(2));
                            Map.gui.addStatusImage(obj1);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("There's no missile 2");
                        Map.hero.setMissiles(2, null);
                        Map.gui.removeStatusImage(Map.hero.getMissiles(2));
                        Map.gui.addStatusImage(new Black(new Position(2, 0)));
                    }
                    //We set the health of our hero
                    int[] health = new int[4];
                    try {
                        health[0] = Integer.parseInt(hero[21].replaceAll(";", ""));
                        health[1] = Integer.parseInt(hero[22].replaceAll(";", ""));
                        health[2] = Integer.parseInt(hero[23].replaceAll(";", ""));
                        health[3] = Integer.parseInt(hero[24].replaceAll(";", ""));
                        Map.hero.setAbsoluteHealthBoxValue(Integer.parseInt(hero[25].replaceAll(";", "")));
                    } catch (NumberFormatException e) {
                        System.out.println("Problem parsing the health of the hero");
                    }
                    Map.hero.setHealth(health);
                    //We set the stats
                    try {
                        Map.hero.setStrength(Integer.parseInt(hero[26].replaceAll(";", "")));
                        Map.hero.setAbsolutePoints(Integer.parseInt(hero[27].replaceAll(";", "")));
                        Map.hero.setHit(Integer.parseInt(hero[28].replaceAll(";", "")));
                        Map.hero.setAbsoluteLevel(Integer.parseInt(hero[29].replaceAll(";", "")));
                        Map.hero.setGold(Integer.parseInt(hero[30].replaceAll(";", "")));
                       /* if (!hero[31].equals("null")){
                            Map.hero.setWeapon((MeeleWeapon) getTile(hero[31],0,0,0,false,null,0,0,null));
                        }
                        if (!hero[32].equals("null")){
                            Map.hero.setArmor((Armor) getTile(hero[32],0,0,0,false,null,0,0,null));
                        }*/

                    } catch (NumberFormatException e) {
                        System.out.println("Problem parsing the stats of the hero");

                    }

                    flag = false;
                    continue;
                }
                //We set our rooms
                String[] room = scanner.nextLine().split("#");
                //We create a new arraylist of tiles
                List<ImageTile> tiles = new ArrayList<>();
                try {
                    //We split our line into tiles
                    String[] tilesConfiguration = room[2].split(";");
                    //We iterate each tile
                    for (String s : tilesConfiguration) {
                        //We separate our tile to get each attribute
                        String[] tile = s.split(",");
                        try {
                            int x = Integer.parseInt(tile[1]);
                            int y = Integer.parseInt(tile[2]);
                            //If a tile have 3 position then is a regular tile: name, new Position(x,y)
                            if (tile.length == 3) {
                                ImageTile newTile = getTile(tile[0], x, y, 0, false, null, 0, 0, null);
                                tiles.add(newTile);
                                //If a tile have 4 position then is a enemy or a coin;
                                //name, new Position(x,y), health or amount
                            } else if (tile.length == 4) {
                                try {

                                    if (tile[3].equals("true")) {
                                        tiles.add(getTile(tile[0], x, y, 0, true, null, 0, 0, null));
                                    } else if (tile[3].equals("false")) {
                                        tiles.add(getTile(tile[0], x, y, 0, false, null, 0, 0, null));
                                    } else {
                                        int amount = Integer.parseInt(tile[3]);
                                        tiles.add(getTile(tile[0], x, y, amount, false, null, 0, 0, null));
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Error parsing the health/amount of the tile " + tile[0] + " of the room " + room[0]);
                                }
                                //If a tile have 8 positions then is a door
                                //name, new Position(x,y), boolean state, leadsTo, heroPosition(x,y), String key
                            } else if (tile.length == 8) {
                                try {
                                    int heroX = Integer.parseInt(tile[5]);
                                    int heroY = Integer.parseInt(tile[6]);
                                    boolean state = tile[3].equals("true");
                                    tiles.add(getTile(tile[0], x, y, 0, state, tile[4], heroX, heroY, tile[7]));
                                } catch (NumberFormatException e) {
                                    System.out.println("error parsing the position of the hero in the door " + tile[0] + " of the room " + room[0]);
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("error parsing the position of the tile " + tile[0] + " of the room " + room[0]);
                        }

                    }
                    //We create a new room with our tiles
                    Room newRoom = new Room(tiles);
                    //We establish if we have been to that room or not
                    if (room[1].equals("true")) {
                        newRoom.setStatus();
                    }
                    newRoom.setKey(room[0]);
                    Map.roomMap.put(room[0], newRoom);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //if for some reason the room didn't save, them it causes an exception
                    e.printStackTrace();
                }
            }
            if (!(nowRoom.equals("0"))) {
                Map.roomMap.get(nowRoom).getTiles().add(Map.hero);
                // Map.setRoom(nowRoom);
                Map.tiles = Map.roomMap.get(nowRoom).getTiles();
                Map.gui.newImages(Map.roomMap.get(nowRoom).getTiles());
                System.out.println("Game loaded");
                Map.gui.setText(new JLabel("Game loaded :D Level: " + Map.level + " Room: " + Map.nowRoom), true);
                Map.roomMap.forEach((String, Room) -> System.out.println(Map.roomMap.get(String)));
                Map.hero.changeImageLifeStatus();
            } else {
                Map.gui.setText(new JLabel("Unable to load :("), false);
                System.out.println("Unable to load ");
            }
            //We close the scanner to avoid corruption
            scanner.close();

        } catch (FileNotFoundException e) {
            Map.gui.setText(new JLabel("There was no save, new game started :("), false);
            Engine engine = new Engine();
            engine.init();
            e.printStackTrace();
        }
    }

    /**
     * Codifies the numbers in an array to be put into a file containing the scores
     *
     * @param myScore     an Array with the number to be codify
     * @param lineMyScore and Array with the line of the file to be codified in
     * @return the new line to be put into the file
     */
    private String getNumbers(String[] myScore, String[] lineMyScore) {
        //This is will represent the first index in the myScores Array
        int i = 0;
        int k = myScore.length;
        //We put our number between the index 4 and 8
        for (int j = 4; j < lineMyScore.length - 1; j++) {
            //TODO if the number is bigger than the space provided it will simply get cut down which is not ideal
            //this avoid having a NullPointerException if myScore is shorter than the space provided
            if (i < k) {
                //We codify each number respectably
                switch (myScore[i]) {
                    case "0":
                        lineMyScore[j] = "Î";
                        break;
                    case "1":
                        lineMyScore[j] = "Ï";
                        break;
                    case "2":
                        lineMyScore[j] = "Ð";
                        break;
                    case "3":
                        lineMyScore[j] = "Ñ";
                        break;
                    case "4":
                        lineMyScore[j] = "Ò";
                        break;
                    case "5":
                        lineMyScore[j] = "Ó";
                        break;
                    case "6":
                        lineMyScore[j] = "Ô";
                        break;
                    case "7":
                        lineMyScore[j] = "Õ";
                        break;
                    case "8":
                        lineMyScore[j] = "Ö";
                        break;
                    case "9":
                        lineMyScore[j] = "×";
                        break;
                    default:
                        lineMyScore[j] = "*";
                }
            } else {
                //Once we fill all of our number in the space given if we still have space in it
                //We fill it with black to avoid having numbers leftover from previous games
                lineMyScore[j] = "*";
            }
            //We add one to our index myScore counter
            i++;
        }
        //We put together back all of our array into one line
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lineMyScore) {
            stringBuilder.append(line);
        }
        //We return the line built
        return stringBuilder.toString();
    }

    private ImageTile getTile(String name, int x, int y, int healthAmount, boolean state, String leadsTo, int xHero, int yHero, String code) {
        switch (name) {
            case "Floor":
            case "Floor_prison":
                return new Floor(new Position(x, y));
            case "Grass":
            case "Grass_long_prison":
                return new Grass(new Position(x, y));
            case "Trap":
            case "Trap_alarm_prison":
            case "Trap_fire_prison":
            case "Trap_fire_sewers":
            case "Trap_paralytic_gas_prison":
            case "Trap_paralytic_gas_sewers":
            case "Trap_poison_dart_prison":
            case "Trap_poison_dart_sewers":
            case "Trap_toxic_gas_prison":
            case "Trap_toxic_gas_sewers":
            case "Trap_triggered_prison":
            case "Trap_triggered_sewers":
            case "Lightning_prison":
            case "Lightning_sewers":
                return new Trap(new Position(x, y));
            case "Water":
            case "Water_prison":
                return new Water(new Position(x, y));
            case "Chasm":
            case "Chasm_floor_prison":
                return new Chasm(new Position(x, y));
            case "Grass_trampled":
            case "Grass_trampled_prison":
                return new GrassT(new Position(x, y));
            case "Wall":
            case "Wall_prison":
                return new Wall(new Position(x, y));
            case "Chasm_wall":
            case "Chasm_wall_prison":
                return new ChasmWall(new Position(x, y));
            case "StairsDown":
            case "Stairs_down_prison":
                return new StairsDown(new Position(x, y));
            case "StairsUp":
            case "Stairs_up_prison":
                return new StairsUp(new Position(x, y));
            case "DoorClosed":
            case "Door_closed_prison":
                DoorClosed newDoorClosed = new DoorClosed(new Position(x, y));
                newDoorClosed.setHeroPosition(new Position(xHero, yHero));
                newDoorClosed.setKey(code);
                newDoorClosed.setLeadsToo(leadsTo);
                newDoorClosed.setState(state);
                return newDoorClosed;
            case "DoorOpen":
            case "Door_open_prison":
                DoorOpen newDoorOpen = new DoorOpen(new Position(x, y));
                newDoorOpen.setHeroPosition(new Position(xHero, yHero));
                newDoorOpen.setKey(code);
                newDoorOpen.setLeadsToo(leadsTo);
                newDoorOpen.setState(state);
                return newDoorOpen;
            case "Door_locked":
            case "Door_locked_prison":
                DoorLocked newDoorLocked = new DoorLocked(new Position(x, y));
                newDoorLocked.setHeroPosition(new Position(xHero, yHero));
                newDoorLocked.setKey(code);
                newDoorLocked.setLeadsToo(leadsTo);
                newDoorLocked.setState(state);
                return newDoorLocked;
            case "Door_boss_locked":
            case "Door_boss_locked_prison":
                DoorLockedBoss newDoorLocketBoss = new DoorLockedBoss(new Position(x, y));
                newDoorLocketBoss.setHeroPosition(new Position(xHero, yHero));
                newDoorLocketBoss.setKey(code);
                newDoorLocketBoss.setLeadsToo(leadsTo);
                newDoorLocketBoss.setState(state);
                return newDoorLocketBoss;
            case "Bistre":
            case "Charcoal":
            case "Crimson":
            case "Golden":
            case "Indigo":
            case "Ivory":
            case "Jade":
            case "Magenta":
            case "Silver":
            case "Azure":
            case "Amber":
            case "Turquoise":
                return new Potion(new Position(x, y));
            case "Bookshelves":
                return new LibraryWall(new Position(x, y));
            //Colors
            case "Black":
                return new Black(new Position(x, y));
            case "Green":
                return new Green(new Position(x, y));
            case "Red":
                return new Red(new Position(x, y));
            case "RedGreen":
                return new RedGreen(new Position(x, y));
            //Objects
            case "Gold":
                return new Gold(new Position(x, y), healthAmount);
            case "OpenChest":
                Treasure treasure = new Treasure(new Position(x, y));
                treasure.setState(state);
                return treasure;
            //Elements
            case "Fire":
                return new Fire(new Position(x, y));
            //Food
            case "GoodMeat":
                return new GoodMeat(new Position(x, y));
            case "Mystery_meat":
                return new MysteryMeat(new Position(x, y));
            case "Food":
                return new Ration(new Position(x, y));
            case "Frozen_carpaccio":
                return new FrozenCarpaccio(new Position(x, y));
            case "Pasty":
                return new Pasty(new Position(x, y));
            case "GoldApple":
                return new GoldApple(new Position(x, y));
            //Weapons
            case "Hammer":
                return new Hammer(new Position(x, y));
            case "Sword":
                return new Sword(new Position(x, y));
            case "BattleAxe":
                return new BattleAxe(new Position(x, y));
            case "Mace":
                return new Mace(new Position(x, y));
            case "ShortSword":
                return new ShortSword(new Position(x, y));
            case "Bomb":
                return new Bomb(new Position(x, y));
            case "Boomerang":
                return new Boomerang(new Position(x, y));
            case "Curare_dart":
                return new CurareDart(new Position(x, y));
            //Armor
            case "Cloth_armor":
                return new Cloth(new Position(x, y));
            case "Leather_armor":
                return new Leather(new Position(x, y));
            case "Mail_armor":
                return new Mail(new Position(x, y));
            case "Plate_armor":
                return new Plate(new Position(x, y));
            case "Scale_armor":
                return new Scale(new Position(x, y));
            //Keys
            case "Key":
                return new Key(new Position(x, y));
            case "Golden_key":
                return new GoldenKey(new Position(x, y));
            case "Skull_key":
                return new SkullKey(new Position(x, y));
            //Enemies
            case "Skeleton":
                Skeleton skeleton = new Skeleton(new Position(x, y));
                skeleton.setHealth(healthAmount);
                return skeleton;
            case "Bat":
                Bat bat = new Bat(new Position(x, y));
                bat.setHealth(healthAmount);
                return bat;
            case "Hole":
                Hole hole = new Hole(new Position(x, y));
                hole.setHealth(healthAmount);
                return hole;
            case "Pig":
                Pig pig = new Pig(new Position(x, y));
                pig.setHealth(healthAmount);
                return pig;
            case "BadGuy":
                BadGuy badGuy = new BadGuy(new Position(x, y));
                badGuy.setHealth(healthAmount);
                return badGuy;
            case "PlagueDoctor":
                PlagueDoctor plagueDoctor = new PlagueDoctor(new Position(x, y));
                plagueDoctor.setHealth(healthAmount);
                return plagueDoctor;
            case "Thief":
                Thief thief = new Thief(new Position(x, y));
                thief.setHealth(healthAmount);
                return thief;
            case "HollowMuse":
                HollowMuse hollowMuse = new HollowMuse(new Position(x, y));
                hollowMuse.setHealth(healthAmount);
                return hollowMuse;
            //NPC
            case "Megumi":
                return new Megumi(new Position(x, y));
            case "Sad_ghost":
                return new SadGhost(new Position(x, y));
            default:
                System.out.println("Problem in " + name);
                return new Floor(new Position(x, y));

        }
    }


}
