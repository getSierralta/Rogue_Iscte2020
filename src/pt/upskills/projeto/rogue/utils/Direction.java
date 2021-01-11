package pt.upskills.projeto.rogue.utils;

/**
 * @author POO2016
 * 
 * Cardinal directions
 *
 */
public enum Direction {
	LEFT, UP, RIGHT, DOWN, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT;

	public Vector2D asVector() {
		if(this==Direction.UP){
			return new Vector2D(0, -1);
		}
		if(this==Direction.DOWN){
			return new Vector2D(0, 1);
		}
		if(this==Direction.LEFT){
			return new Vector2D(-1, 0);
		}
		if(this==Direction.RIGHT){
			return new Vector2D(1, 0);
		}
		if(this==Direction.UPLEFT){
			return new Vector2D(-1, -1);
		}
		if(this==Direction.DOWNLEFT){
			return new Vector2D(-1, 1);
		}
		if(this==Direction.UPRIGHT){
			return new Vector2D(1, -1);
		}
		if(this==Direction.DOWNRIGHT){
			return new Vector2D(1, 1);
		}
		return new Vector2D(0, 0);
	}
}
