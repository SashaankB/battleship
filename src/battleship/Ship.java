package battleship;

public class Ship {
	
	/* Ships are a point with direction, length
	 * and amount of times hit.
	 */
	
	private Point p;
	private boolean direction; //0 horizontal, 1 vertical
	private int length;
	private int hit;
	
	public Ship(Point p, boolean direction, int length){
		this.p = p;
		this.direction = direction;
		this.length = length;
	}
	
	/* Increments hit if point is on the ship.
	 * 
	 * returns true if ship was hit.
	 */
	public boolean hit(Point i){
		if (i.y == p.y && direction && (i.x - p.x <= length && i.x - p.x >= 0))
			hit++;
		else if (i.x == p.x && !direction && (i.y - p.y <= length && i.y - p.y >= 0))
			hit++;
		else
			return false;
		return true;
	}
	
	public boolean hasSank(){
		return hit == length;
	}
}
