package battleship;

public class Ship {
	
	/* Ships are a point with direction, length
	 * and amount of times hit.
	 */
	
	private Point p;
	private boolean direction; //true == horizontal, false == vertical
	private int length;
	private int hit;
	
	public Ship(Point p, boolean direction, int length){
		this.p = p;
		this.direction = direction;
		this.length = length;
		if (direction) {
			if (p.x + length > 10)
				throw new IndexOutOfBoundsException();
		} else {
			if (p.y + length > 10)
				throw new IndexOutOfBoundsException();
		}
	}
	
	/* Increments hit if point is on the ship.
	 * 
	 * returns true if ship was hit.
	 */
	public boolean hit(Point i){
		if (isShip(i)) {
			hit++;
			return true;
		}
		return false;
		
	}
	
	public boolean isShip(Point i){
		//horizontal
		if (i.y == p.y && direction && i.x - p.x < length && i.x - p.x >= 0)
			return true;
		//vertical
		else if (i.x == p.x && !direction && i.y - p.y < length && i.y - p.y >= 0)
			return true;
		else
			return false;
	}
	
	public boolean hasSank(){
		return hit == length;
	}
}
