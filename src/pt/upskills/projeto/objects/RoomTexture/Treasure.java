package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Artefacts.Gold;
import pt.upskills.projeto.objects.Openable;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;

public class Treasure implements Openable, ImageTile {

    private Position position;
    private boolean state;
    private String requiredKey;

    public Treasure( Position position) {
        this.requiredKey = "Golden_key";
        this.position = position;
        state = false;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String getName() {
        return "OpenChest";
    }

    public Position getPosition() {
        return position;
    }

    public String getRequiredKey() {
        return requiredKey;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public void openDoor() {
        this.state = true;
    }

    public boolean use() {
        Position newPosition = new Position(this.getPosition().getX(),this.getPosition().getY());
        if (state){
            Gold gold = new Gold(newPosition, 100);
            Map.tiles.add(gold);
            Map.gui.addImage(gold);
            Gold gold1 = new Gold(newPosition, 100);
            Map.tiles.add(gold1);
            Map.gui.addImage(gold1);
            Map.gui.setText(new JLabel("The treasure has been open"),true);
            System.out.println("It open it");
            Map.tiles.remove(this);
            Map.gui.removeImage(this);
            return true;
        }
        Map.gui.setText(new JLabel("The chest is close, requires a golden key"),false);
        System.out.println("It didn't open it ");
       return false;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
            return getName()+","+getPosition().getX()+","+getPosition().getY()+","+isState()+";";

    }
}
