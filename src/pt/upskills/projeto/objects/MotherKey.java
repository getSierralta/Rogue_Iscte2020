package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Map;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.RoomTexture.DoorOpen;
import pt.upskills.projeto.rogue.utils.Position;

import javax.swing.*;
import java.util.List;

public abstract class MotherKey extends Artefact{

	public MotherKey(int price, int sell,int experience) {
		super(price, sell, experience);
	}


	@Override
	protected boolean use(int indexArtefact) {
		Position position = Map.hero.getPosition().plus(Map.hero.getDirection().asVector());
		List<ImageTile> tiles = Map.tiles;
		for (ImageTile tile: tiles){
			if(tile instanceof Openable && tile.getPosition().getY() == position.getY() && tile.getPosition().getX() == position.getX()){
				if (Map.hero.getObject(indexArtefact).getName().equals(((Openable) tile).getRequiredKey())){
					Map.gui.removeStatusImage(Map.hero.getObject(indexArtefact));
					Map.hero.setObject(indexArtefact,null);
					((Openable) tile).openDoor();
					Map.gui.setText(new JLabel(tile.getName()+" has been open"),true);
					if (tile instanceof Door){
						Door newDoor = new DoorOpen(new Position(tile.getPosition().getX(),tile.getPosition().getY()));
						newDoor.setLeadsToo(((Door) tile).getLeadsToo());
						newDoor.setHeroPosition(((Door) tile).getHeroPosition());
						newDoor.setKey(((Door) tile).getKey());
						Map.tiles.add(newDoor);
						Map.gui.addImage(new DoorOpen(new Position(tile.getPosition().getX(),tile.getPosition().getY())));
						Map.tiles.remove(tile);
						Map.gui.removeImage(tile);
					}
					return true;
				}
			}
		}
		Map.gui.setText(new JLabel("Didn't find anything to open with "+getName()),false);
		return false;
	}
}
