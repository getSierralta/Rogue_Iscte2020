package pt.upskills.projeto.objects;


import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Textures.Health;
import java.util.List;

public class Room {
	public boolean status;
	//The idea of moving through the rooms by saving all the tiles in the room
	// was of Nuno Cruz
	private List<ImageTile> tiles;
	private String key;

	public Room(List<ImageTile> tiles){
		this.tiles = tiles;
		this.status = false;
	}


	public void setTiles(List<ImageTile> tiles) {
		this.tiles = tiles;
	}

	public List<ImageTile> getTiles() {
		return tiles;
	}

	public void setStatus(){
		this.status = true;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isStatus() {
		return status;
	}

	@Override
	public String toString() {
		StringBuilder tilesString = new StringBuilder();
		for (ImageTile tile: tiles) {
			if (tile instanceof Hero || tile instanceof Health || tile == null)
				continue;
			tilesString.append(tile.toString());
		}
		return getKey()+"#"+isStatus()+"#"+tilesString;
	}
}
