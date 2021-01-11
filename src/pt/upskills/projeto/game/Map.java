package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.ImageMatrixGUI;
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
import pt.upskills.projeto.objects.Enemies.Hole;
import pt.upskills.projeto.objects.Message.*;
import pt.upskills.projeto.objects.NPC.*;
import pt.upskills.projeto.objects.RoomTexture.*;
import pt.upskills.projeto.objects.Textures.*;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Map {


    //it was a cool night of november, the bird had go to sleep, the wind
    //whispered nothing but emptiness madness, the cold, oh the cold was the worst of it
    // after so many years of tropical nights the cold has come to wrap me in nullPointerExceptions
    //TODO the enemies sometimes can pass throw the walls  in the other levels
    //TODO power class



    public static List<ImageTile> tiles = new ArrayList<>();
    //Our hero
    public static Hero hero = new Hero(new Position(5, 7));
    //Our Graphical interface
    public static ImageMatrixGUI gui;
    //The room we are in
    public static String nowRoom = "0_1";
    //Is the game is over or not, true means is not over
    public static boolean gameOver = true;
    //Our FileGenerator
    public static final FileGenerator fileGenerator = new FileGenerator();
    //A map containing all of our Rooms in the level we are in
    //The key is the number of the room, codified as the level we are in + _ + the number of the room
    protected static HashMap<String, Room> roomMap = new HashMap<>();
    //The level we are right now, starting with 0
    public static int level = -1;
    //We only use this one to save the doors of the room we are reading, so we can assign it to our rooms
    //Key the number in which it appears in the file, from left to right, up to down
    private static final HashMap<Integer, Door> doors = new HashMap<>();
    protected static HashMap<String, Door> doorsMap = new HashMap<>();

    public static void moveEnemies() {
        if (gameOver) {
            List<Enemy> enemies = new ArrayList<>();
            for (ImageTile enemy : Map.tiles) {
                if (enemy instanceof Enemy) {
                    enemies.add((Enemy) enemy);
                }
            }
            for (Enemy enemy : enemies) {
                enemy.moveRandomly();
            }
        }
    }

    /**
     * this method updates the level we are in
     * clears the list of the rooms
     */
    private static boolean setLevel(String room) {
        String[] split = room.split("_");
        try {
            if (Integer.parseInt(split[0]) > level) {
                //Level is one higher
                level++;
                //Clears all the rooms
                roomMap.clear();
                //Clears the graphical interface
                gui.clearImages();
                doorsMap.clear();
                nowRoom = room;
                //Call the readLevelConfigurations() which structures the new level
                readLevelConfigurations();
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void printRoomMap() {
        System.out.println(roomMap.keySet());
    }

    /**
     * Verify if is a new room or not
     * then it uploads the new tiles correspondingly
     *
     * @param room the new room
     */
    public static void setRoom(String room) {
        //TODO new plan lets save the file and then when we get to the room we load it :)
        System.out.println("Rooms: ");
        printRoomMap();
        //roomMap.forEach((String,Room) -> System.out.println(roomMap.get(String)));
        //Rooms numbers are coded as level+"_"+room
        //split[0] contains the level of the room we are trying to get into
        //if split[0] is greater than the field level, it means we have entered a new level
        if (room.equals("0") || room.equals("666") || room.equals("score") || room.equals("3_0")) {
            if (room.equals("3_0")){
                level++;
                Map.gui.setText(new JLabel(""),false);
            }
            gui.clearImages();
            gui.newImages(readMapFile(room));
        } else {
            if (!setLevel(room)) {
                if (nowRoom.equals("0") || level == 0) {
                    System.out.println("the old room was the screen");
                } else {
                    try {
                        roomMap.get(nowRoom).setStatus();
                        roomMap.get(nowRoom).setTiles(tiles);
                        gui.clearImages();
                    } catch (Exception e) {
                        Map.gui.setText(new JLabel("New level "+level),true);
                        System.out.println("New level");
                    }
                }
                nowRoom = room;
                tiles = roomMap.get(room).getTiles();
                tiles.add(hero);
                gui.newImages(tiles);
                Map.gui.setText(new JLabel("You have entered the room -> "+room),true);

            } else {
                setRoom(room);
            }
        }
    }


    /**
     * this method reads a file contains all the levels and the configurations
     * and then it creates the structure of the level
     * Doors most be saved in the file in the same way as they appear (From left to right,Up to down)
     */

    private static void readLevelConfigurations() {
        //We path our level
        File file = new File("levels/level" + level + ".txt");
        try {
            Scanner scanner = new Scanner(file);
            //This will help us identify the doors in our hashmap of doors (key order of appearances)
            int i = 0;
            while (scanner.hasNext()) {
                //We split the line
                String[] split = scanner.nextLine().split(";");
                //The first position always indicate the room we are in
                String roomWeAre = split[0];

                //if it doesn't exist them we create it
                if (roomMap.get(roomWeAre) == null) {
                    //We clear our doors
                    doors.clear();
                    //We reset our door counter
                    i = 0;
                    Room newRoom = new Room(readMapFile(roomWeAre));
                    newRoom.setKey(split[0]);
                    roomMap.put(split[0], newRoom);
                }
                //We set the leadsTo of our door, split[2] indicates the door it leads to
                try {
                    doors.get(i).setLeadsToo(split[2]);
                    try {
                        int x = Integer.parseInt(split[3]);
                        //If this gives ArrayIndexOutOfBoundsException is because you put a ";"
                        // wrong in the level configuration file
                        int y = Integer.parseInt(split[4]);
                        System.out.println(new Position(x, y));
                        doors.get(i).setHeroPosition(new Position(x, y));
                        String code = roomWeAre + "_" + i;
                        doors.get(i).setKey(code);
                        //We add our door to the room
                        i++;
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        System.out.println("Problem trying to read the level configurations"
                                + "It can be either you have a letter/symbol where a number is supposed to be " +
                                "or you put the wrong type of regex");
                    }
                } catch (NullPointerException e) {
                    System.out.println("There's a door giving null");
                }
            }
            //We close our file to avoid corruption
            scanner.close();
            if (level != 0 && level != 3) {
                tiles = roomMap.get(level + "_0").getTiles();
                gui.newImages(tiles);
            }

        } catch (FileNotFoundException e) {
            //This can make a FileNotFoundException if the file doesn't exist
            e.printStackTrace();
        }

    }


    /**
     * It read a file containing the map of a room and decodes it from symbols to tiles
     *
     * @param room the room you wish to read
     * @return a List of ImageTile
     */
    private static List<ImageTile> readMapFile(String room) {
        //We point to the file
        File file = new File("rooms/room" + room + ".txt");
        //We make a list of ImageTile
        List<ImageTile> tiles = new ArrayList<>();
        //We make a list of enemies, that way the order of tiles is tiles, enemies, and hero
        //Avoiding bugs of hero and enemies walking under tiles
        List<Enemy> enemies = new ArrayList<>();
        //For each square in the map we add a floor
        //This avoid things like the hero, enemies getting under floors, or having to manually
        //put a floor under hero/enemies
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles.add(new Floor(new Position(j, i)));
            }
        }
        try {
            //We read the file
            Scanner scanner = new Scanner(file);
            //This counter will contain the y coordinate
            int i = 0;
            while (scanner.hasNext()) {
                //We split the line so we can read each character
                String[] split = scanner.nextLine().split("");
                //the j will contain the x coordinate
                for (int j = 0; j < split.length; j++) {
                    //We decoy each part of our file and add it to our tiles
                    //Creating our objects and setting it coordinates
                    switch (split[j]) {
                        //Floors
                        case "g":
                            tiles.add(new Grass(new Position(j, i)));
                            break;
                        case "T":
                            tiles.add(new Trap(new Position(j, i)));
                            break;
                        case "â":
                            tiles.add(new Hole(new Position(j, i)));
                            break;
                        case "ã":
                            tiles.add(new Water(new Position(j, i)));
                            break;
                        case "õ":
                            tiles.add(new Chasm(new Position(j, i)));
                            break;
                        //Walls
                        case "W":
                            tiles.add(new Wall(new Position(j, i)));
                            break;
                        case "ô":
                            tiles.add(new ChasmWall(new Position(j, i)));
                            break;
                        case "ò":
                            tiles.add(new LibraryWall(new Position(j, i)));
                            break;
                        case "ï":
                            tiles.add(new Statue(new Position(j, i)));
                            break;
                        //Stairs
                        case "D":
                            tiles.add(new StairsDown(new Position(j, i)));
                            break;
                        case "U":
                            tiles.add(new StairsUp(new Position(j, i)));
                            break;
                        //Doors
                        case "d":
                            DoorClosed door = new DoorClosed(new Position(j, i));
                            tiles.add(door);
                            doors.put(doors.size(), door);
                            break;
                        case "E":
                            DoorOpen doorOpen = new DoorOpen(new Position(j, i));
                            tiles.add(doorOpen);
                            doors.put(doors.size(), doorOpen);
                            break;
                        case "L":
                            DoorLocked doorLocked = new DoorLocked(new Position(j, i));
                            tiles.add(doorLocked);
                            doors.put(doors.size(), doorLocked);
                            break;
                        case "l":
                            DoorLockedBoss doorLockedBoss = new DoorLockedBoss(new Position(j, i));
                            tiles.add(doorLockedBoss);
                            doors.put(doors.size(), doorLockedBoss);
                            break;
                        //Colors
                        case "*":
                            tiles.add(new Black(new Position(j, i)));
                            break;
                        case "+":
                            tiles.add(new Green(new Position(j, i)));
                            break;
                        case "-":
                            tiles.add(new Red(new Position(j, i)));
                            break;
                        case "%":
                            tiles.add(new RedGreen(new Position(j, i)));
                            break;
                        //Objects
                        case "á":
                            tiles.add(new Gold(new Position(j, i), 5));
                            break;
                        case "à":
                            tiles.add(new Treasure(new Position(j, i)));
                            break;
                        //Elements
                        case "F":
                            tiles.add(new Fire(new Position(j, i)));
                            break;
                        //Potions
                        case "ö":
                            tiles.add(new Potion(new Position(j, i)));
                            break;
                        //Food
                        case "M":
                            tiles.add(new GoodMeat(new Position(j, i)));
                            break;
                        case "w":
                            tiles.add(new MysteryMeat(new Position(j, i)));
                            break;
                        case "R":
                            tiles.add(new Ration(new Position(j, i)));
                            break;
                        case "C":
                            tiles.add(new FrozenCarpaccio(new Position(j, i)));
                            break;
                        case "s":
                            tiles.add(new Pasty(new Position(j, i)));
                            break;
                        case "G":
                            tiles.add(new GoldApple(new Position(j, i)));
                            break;
                        //Weapons
                        case "h":
                            tiles.add(new Hammer(new Position(j, i)));
                            break;
                        case "o":
                            tiles.add(new Sword(new Position(j, i)));
                            break;
                        case "e":
                            tiles.add(new BattleAxe(new Position(j, i)));
                            break;
                        case "c":
                            tiles.add(new Mace(new Position(j, i)));
                            break;
                        case "!":
                            tiles.add(new ShortSword(new Position(j, i)));
                            break;
                        case "O":
                            tiles.add(new Bomb(new Position(j, i)));
                            break;
                        case "r":
                            tiles.add(new Boomerang(new Position(j, i)));
                            break;
                        case "u":
                            tiles.add(new CurareDart(new Position(j, i)));
                            break;
                        //Armor
                        case "é":
                            tiles.add(new Cloth(new Position(j, i)));
                            break;
                        case "è":
                            tiles.add(new Leather(new Position(j, i)));
                            break;
                        case "ê":
                            tiles.add(new Mail(new Position(j, i)));
                            break;
                        case "ë":
                            tiles.add(new Scale(new Position(j, i)));
                            break;
                        case "ó":
                            tiles.add(new Plate(new Position(j, i)));
                            break;
                        //Keys
                        case "K":
                            tiles.add(new Key(new Position(j, i)));
                            break;
                        case "k":
                            tiles.add(new GoldenKey(new Position(j, i)));
                            break;
                        case "y":
                            tiles.add(new SkullKey(new Position(j, i)));
                            break;
                        //NPC
                        case "a":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new AnimeGirlRunning(new Position(j, i)));
                            break;
                        case "i":
                            tiles.add(new Megumi(new Position(j, i)));
                            break;
                        case "í":
                            tiles.add(new SadGhost(new Position(j, i)));
                            break;
                        case "(":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new AnimeGirlDancing2(new Position(j, i)));
                            break;
                        case ")":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new AnimeGirlDancing(new Position(j, i)));
                            break;
                        case "]":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new Jake(new Position(j, i)));
                            break;
                        case "{":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new Calamardo(new Position(j, i)));
                            break;
                        case "}":
                            tiles.add(new Black(new Position(j, i)));
                            tiles.add(new Pinguin(new Position(j, i)));
                            break;
                        //Enemies
                        case "S":
                            enemies.add(new Skeleton(new Position(j, i)));
                            break;
                        case "b":
                            enemies.add(new Bat(new Position(j, i)));
                            break;
                        case "P":
                            enemies.add(new Pig(new Position(j, i)));
                            break;
                        case "B":
                            enemies.add(new BadGuy(new Position(j, i)));
                            break;
                        case "p":
                            enemies.add(new PlagueDoctor(new Position(j, i)));
                            break;
                        case "t":
                            enemies.add(new Thief(new Position(j, i)));
                            break;
                        case "m":
                            enemies.add(new HollowMuse(new Position(j, i)));
                            break;
                        //Of course i could have spent around 4 hours learning how to work with interfaces
                        //But i though it is only a few images and oh boy by the time i noticed how much
                        //Work this was taking me i had already invested so much time and effort
                        //To this archaic way of showing something in the screen i had to stick to it
                        //I pinky promise i will never in my life do something like this again
                        // when they are so many elegant ways to do it
                        //But oh boy if you all wanted me to do something with what i had at the moment
                        //Well this is it, this is what i had i am shaken, i am revolted
                        //It actually not that much if you think about it
                        // it did kinda ended up good so there's that
                        //Message -----------------------------------------------
                        case "~":
                            tiles.add(new Uno(new Position(j, i)));
                            break;
                        case "€":
                            tiles.add(new Dos(new Position(j, i)));
                            break;
                        case "‚":
                            tiles.add(new Tres(new Position(j, i)));
                            break;
                        case "ƒ":
                            tiles.add(new Cuatro(new Position(j, i)));
                            break;
                        case "„":
                            tiles.add(new Cinco(new Position(j, i)));
                            break;
                        case "…":
                            tiles.add(new Seis(new Position(j, i)));
                            break;
                        case "†":
                            tiles.add(new Siete(new Position(j, i)));
                            break;
                        case "‡":
                            tiles.add(new Ocho(new Position(j, i)));
                            break;
                        case "ˆ":
                            tiles.add(new Nueve(new Position(j, i)));
                            break;
                        case "Š":
                            tiles.add(new Diez(new Position(j, i)));
                            break;
                        case "‹":
                            tiles.add(new Once(new Position(j, i)));
                            break;
                        case "Œ":
                            tiles.add(new Doce(new Position(j, i)));
                            break;
                        case "Ž":
                            tiles.add(new Trece(new Position(j, i)));
                            break;
                        case "‘":
                            tiles.add(new Catorce(new Position(j, i)));
                            break;
                        case "’":
                            tiles.add(new Quince(new Position(j, i)));
                            break;
                        case "“":
                            tiles.add(new Dieciseis(new Position(j, i)));
                            break;
                        case "”":
                            tiles.add(new Diecisiete(new Position(j, i)));
                            break;
                        case "•":
                            tiles.add(new Diesiocho(new Position(j, i)));
                            break;
                        case "_":
                            tiles.add(new Diesinueve(new Position(j, i)));
                            break;
                        case "˜":
                            tiles.add(new Veinte(new Position(j, i)));
                            break;
                        case "™":
                            tiles.add(new Veintiun(new Position(j, i)));
                            break;
                        case "š":
                            tiles.add(new Veintidos(new Position(j, i)));
                            break;
                        case "›":
                            tiles.add(new Veintitres(new Position(j, i)));
                            break;
                        case "œ":
                            tiles.add(new Veinticuatro(new Position(j, i)));
                            break;
                        case "ž":
                            tiles.add(new Veinticinco(new Position(j, i)));
                            break;
                        case "¦":
                            tiles.add(new Veintiseis(new Position(j, i)));
                            break;
                        case "©":
                            tiles.add(new Veintisiete(new Position(j, i)));
                            break;
                        case "¬":
                            tiles.add(new Veintiocho(new Position(j, i)));
                            break;
                        case "°":
                            tiles.add(new Veintinueve(new Position(j, i)));
                            break;
                        case "³":
                            tiles.add(new Treinta(new Position(j, i)));
                            break;
                        case "¡":
                            tiles.add(new Treintaiun(new Position(j, i)));
                            break;
                        case "§":
                            tiles.add(new Treintaidos(new Position(j, i)));
                            break;
                        case "ª":
                            tiles.add(new Treintaitres(new Position(j, i)));
                            break;
                        case "®":
                            tiles.add(new Treintaicuatro(new Position(j, i)));
                            break;
                        case "±":
                            tiles.add(new Treintaicinco(new Position(j, i)));
                            break;
                        case "´":
                            tiles.add(new Treintaiseis(new Position(j, i)));
                            break;
                        case "¨":
                            tiles.add(new Treintaiocho(new Position(j, i)));
                            break;
                        case "«":
                            tiles.add(new Treintainueve(new Position(j, i)));
                            break;
                        case "¯":
                            tiles.add(new Cuarenta(new Position(j, i)));
                            break;
                        case "²":
                            tiles.add(new Cuarentaiun(new Position(j, i)));
                            break;
                        case "¤":
                            tiles.add(new Cuarentaidos(new Position(j, i)));
                            break;
                        case "¢":
                            tiles.add(new Cuarentaitres(new Position(j, i)));
                            break;
                        case "£":
                            tiles.add(new Cuarentaicuatro(new Position(j, i)));
                            break;
                        case "Ÿ":
                            tiles.add(new Cuarentaicinco(new Position(j, i)));
                            break;
                        case "µ":
                            tiles.add(new Cuarentaiseis(new Position(j, i)));
                            break;
                        case "·":
                            tiles.add(new Cuarentaisiete(new Position(j, i)));
                            break;
                        case "¶":
                            tiles.add(new Cuarentaiocho(new Position(j, i)));
                            break;
                        case "¸":
                            tiles.add(new Cuarentainueve(new Position(j, i)));
                            break;
                        case "¹":
                            tiles.add(new Cincuenta(new Position(j, i)));
                            break;
                        case "º":
                            tiles.add(new Cincuentaiun(new Position(j, i)));
                            break;
                        case "»":
                            tiles.add(new Cincuentaidos(new Position(j, i)));
                            break;
                        case "¼":
                            tiles.add(new Cincuentaitres(new Position(j, i)));
                            break;
                        case "½":
                            tiles.add(new Cincuentaicuatro(new Position(j, i)));
                            break;
                        case "¾":
                            tiles.add(new Cincuentaicinco(new Position(j, i)));
                            break;
                        case "¿":
                            tiles.add(new Cincuentaiseis(new Position(j, i)));
                            break;
                        case "À":
                            tiles.add(new Cincuentaisiete(new Position(j, i)));
                            break;
                        case "Á":
                            tiles.add(new Cincuentaiocho(new Position(j, i)));
                            break;
                        case "Â":
                            tiles.add(new Cincuentainueve(new Position(j, i)));
                            break;
                        case "Ã":
                            tiles.add(new Sesenta(new Position(j, i)));
                            break;
                        case "Ä":
                            tiles.add(new Sesentaiun(new Position(j, i)));
                            break;
                        case "Å":
                            tiles.add(new Sesentaidos(new Position(j, i)));
                            break;
                        case "Æ":
                            tiles.add(new Sesentaitres(new Position(j, i)));
                            break;
                        case "Ç":
                            tiles.add(new Sesentaicuatro(new Position(j, i)));
                            break;
                        case "É":
                            tiles.add(new Seseintaicinco(new Position(j, i)));
                            break;
                        case "Ê":
                            tiles.add(new Sesentaiseis(new Position(j, i)));
                            break;
                        case "Ë":
                            tiles.add(new Sesentaisiete(new Position(j, i)));
                            break;
                        case "Ì":
                            tiles.add(new Sesentaiocho(new Position(j, i)));
                            break;
                        case "Í":
                            tiles.add(new Sesentainueve(new Position(j, i)));
                            break;
                        case "Î":
                            tiles.add(new Setenta(new Position(j, i)));
                            break;
                        case "Ï":
                            tiles.add(new Setentaiun(new Position(j, i)));
                            break;
                        case "Ð":
                            tiles.add(new Setentaidos(new Position(j, i)));
                            break;
                        case "Ñ":
                            tiles.add(new Setentaitres(new Position(j, i)));
                            break;
                        case "Ò":
                            tiles.add(new Setentaicuatro(new Position(j, i)));
                            break;
                        case "Ó":
                            tiles.add(new Setentaicinco(new Position(j, i)));
                            break;
                        case "Ô":
                            tiles.add(new Setentaiseis(new Position(j, i)));
                            break;
                        case "Õ":
                            tiles.add(new Setentaisiete(new Position(j, i)));
                            break;
                        case "Ö":
                            tiles.add(new Setentaiocho(new Position(j, i)));
                            break;
                        case "×":
                            tiles.add(new Setentainueve(new Position(j, i)));
                            break;
                        case "A":
                            tiles.add(new Ochenta(new Position(j, i)));
                            break;
                        case "È":
                            tiles.add(new Ochentaiun(new Position(j, i)));
                            break;
                        case "I":
                            tiles.add(new Ochentaidos(new Position(j, i)));
                            break;
                        case "ç":
                            tiles.add(new Ochentaitres(new Position(j, i)));
                            break;
                        case "=":
                            tiles.add(new Ochentaicuatro(new Position(j, i)));
                            break;
                        case "'":
                            tiles.add(new Ochentaicinco(new Position(j, i)));
                            break;
                        default:
                            if (split[j].equals(" ")) {
                                break;
                            }
                            System.out.println("Error identifying: " + split[i]);
                            break;
                    }
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tiles.addAll(enemies);
        return tiles;
    }
    //oh right, i have more stuff here gg

    /**
     * it starts the StatusImage with full life
     */
    public static void initialStatus() {
        for (int i = 3; i < 7; i++) {
            gui.addStatusImage(new Green(new Position(i, 0)));
        }
    }

    /**
     * We set the status image to black
     */
    public static void messageStatus() {
        for (int i = 0; i < 10; i++) {
            gui.addStatusImage(new Black(new Position(i, 0)));
        }
    }
    /**
     * We set the information image to chasm
     */

    public static void messageStatus2() {
        for (int i = 0; i < 10; i++) {
            gui.addStatusImage2(new ChasmWater(new Position(i, 0)));
        }

    }


}
