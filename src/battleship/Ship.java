package battleship;

public class Ship {
	
	/* Ships are an x and y point with direction, length
	 * and amount of times hit.
	 */
	
	private int x;
	private int y;
	private boolean direction; //0 horizontal, 1 vertical
	private int length;
	private int hit;
	
	public Ship(int x, int y, boolean direction, int length){
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.length = length;
	}
	
	/* Does not keep track of which points are hit, the board will
	 * do that, only keeps of track of how many times been hit
	 * 
	 * returns true if ship has been sunk, false otherwise.
	 */
	public boolean hit(int x, int y){
		if (this.y == y && direction && (this.x - x <= length && this.x - x >= 0))
			hit++;
		else if (this.x == x && !direction && (this.y - y <= length && this.y - y >= 0))
			hit++;
		return hit == length;
	}
	
	public boolean hasSank(){
		return hit == length;
	}
}
