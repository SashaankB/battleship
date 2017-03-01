package battleship;

/** Model class which contains information about a specific ship.
 * @author Kellin McAvoy, Nathan Kelderman, Sean Thomas */
public class Ship {
	
	/** The starting point of the ship. */
	private Point p;
	
	/** The direction of the ship, true is horizontal and false is 
	 * vertical. */
	private boolean direction;
	
	/** Length of the ship. */
	private int length;
	
	/** Amount of times ship has been hit. */
	private int hit;
	
	/** Constructor for the ship.
	 * @param point Point from which ship starts.
	 * @param direct Direction of the ship.
	 * @param len Length of the ship. 
	 * @exception IndexOutOfBoundsException Ship's length exceeds grid 
	 * size. */
	public Ship(Point point, boolean direct, int len) {
		p = point; 
		direction = direct;
		length = len;
		if (direction) {
			if (point.x + length > 10) {
				throw new IndexOutOfBoundsException();
			}
		} else {
			if (point.y + length > 10) {
				throw new IndexOutOfBoundsException();
			}
		}
	}
	
	/** Increments hit if point contains ship.
	 * @param point Point to check for ship. 
	 * @return true if hit was incremented. */
	public boolean hit(Point point) {
		if (isShip(point)) {
			hit++;
			return true;
		}
		return false;
		
	}
	
	/** Checks if point contains ship.
	 * @param i Point which to check for ship.
	 * @return True if point contains the ship. */
	public boolean isShip(Point i) {
		//horizontal
		if (i.y == p.y && direction && i.x - p.x < length && i.x - p.x >= 0) {
			return true;
		} else if (i.x == p.x && !direction && i.y - p.y < length && i.y - p.y >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/** Checks if ship has been sank.
	 * @return true if ship has been sank. */
	public boolean hasSank() {
		return hit == length;
	}
	
	/** Get method for length.
	 * @return Length of ship. */
	public int getLength() {
		return length;
	}
	
	/** Get method for direction.
	 * @return Direction boolean, true is horizontal and false is vertical.*/
	public boolean getDirection() {
		return direction;
	}
	
	/** Get method for ship point. 
	 * @return */
	public Point getPoint() {
		return p;
	}
}
