package pt.upskills.projeto.objects;


import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.RoomTexture.DoorClosed;
import pt.upskills.projeto.objects.RoomTexture.DoorOpen;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;

public abstract class Door implements Obstacle, ImageTile, Openable {
    // IMPORTANT!!!!!!!!!!!!! remember everytime you use a key you create a new open door
    //SOO EVERYTIME you change something here, make sure you are also changing the new door
    //Before your life gets full with NullPointerDammitExceptions
    // :sadpepe: and also in the closed doors in this same class
    // really Daniel??? this what life has come to
    //Treasures also extend door and stairs down
    /**
     * the fields indicate what it need to pas to the other side
     */
    private final String requiredKey;
    private String leadsToo;
    private boolean state;
    private Position heroPosition;
    private String key;
    /**
     *
     * @param requiredKey the name of the key it needs to be open
     */
    public Door(String requiredKey) {
        this.requiredKey = requiredKey;
        //By default all the doors are closed
        this.state = false;
    }

    /**
     * where this door goes
     * @param leadsToo room code
     */
    public void setLeadsToo(String leadsToo) {
        this.leadsToo = leadsToo;
    }

    public Position getHeroPosition() {
        return heroPosition;
    }

    public void setHeroPosition(Position heroPosition) {
        this.heroPosition = heroPosition;
    }

    public boolean isState() {
        return state;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * this method sets the state of the door to true, making it able to be used aka pass throw it
     */
    public void openDoor() {
        this.state = true;
    }

    public String getRequiredKey() {
        return requiredKey;
    }

    public String getLeadsToo() {
        return leadsToo;
    }

    /**
     * if the door is open it changes the room
     * @return whether you can pass throw the door or not
     */
    public boolean use(){
        if (this instanceof DoorClosed){
            Door newDoor = new DoorOpen(new Position(this.getPosition().getX(),this.getPosition().getY()));
            newDoor.setLeadsToo(this.getLeadsToo());
            newDoor.openDoor();
            newDoor.setHeroPosition(this.getHeroPosition());
            newDoor.setKey(this.getKey());
            Map.tiles.add(newDoor);
            Map.gui.addImage(new DoorOpen(new Position(this.getPosition().getX(),this.getPosition().getY())));
            Map.tiles.remove(this);
            Map.gui.removeImage(this);
            Map.gui.setText(new JLabel("Door opened!"),true);
        }

        if(state && leadsToo != null && heroPosition != null){
            System.out.println(this);
            Map.setRoom(leadsToo);
            Map.hero.setPosition(this.getHeroPosition());
            Map.gui.setText(new JLabel("Got into the door, you have now appeared in the room: "+leadsToo),true);
            return true;
        }
        System.out.println(this);
        if (this instanceof DoorClosed){
            Map.gui.setText(new JLabel("The door is closed"),false);
        }else {
            Map.gui.setText(new JLabel("The door is locked, requires key "+requiredKey),false);
        }

        return false;
    }

    @Override
    public String toString() {
        try {
            return getName()+","+getPosition().getX()+","+getPosition().getY()+","+isState()+","+getLeadsToo()+","+getHeroPosition().getX()+","+getHeroPosition().getY()+","+getKey()+";";
        }catch (Exception e){
            System.out.println("Error in door"+getKey()+";");
            return "";
        }
    }
}
