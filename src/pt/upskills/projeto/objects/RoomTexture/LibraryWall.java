package pt.upskills.projeto.objects.RoomTexture;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Obstacle;
import pt.upskills.projeto.rogue.utils.Position;

public class LibraryWall implements ImageTile, Obstacle {

	private Position position;


	public LibraryWall(Position position) {
			this.position = position;
		}

		@Override
		public String getName() {
			return "Bookshelves";
		}

		@Override
		public Position getPosition() {
			return position;
		}
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	@Override
	public String toString() {
		return getName()+","+getPosition().getX()+","+getPosition().getY()+";";
	}
}
