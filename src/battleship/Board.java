package battleship;

/** * Model Class for board object.
 * @author Kellin McAvoy, Nathan Kelderman, Sean Thomas */
public class Board {
	
	/** 2 dimensional grid, true for has been selected, false for not selected. */
	private boolean[][] grid;
	
	/** Stack which holds 5 ship objects that exist on the board. */
	private Ship[] ships;
	
	/** Index for ship stack. */
	private int shipIndex;
	
	/** Flag for whether game has started. */
	private boolean gameStarted;
	
	/** Array which keeps record of which ships are alive, true is alive. */
	private boolean[] shipsAlive;
	
	/** Creates a default board.  */
	public Board() {
		grid = new boolean[10][10];
		ships = new Ship[5];
		shipsAlive = new boolean[5];
	}
	
	/** Marks a point on the board, toggling the point to true.
	 * @param point Point which to toggle.
	 * @return Returns true if point marked a ship. */
	public boolean setPoint(Point point) {
		grid[point.x][point.y] = true;
		for (Ship s : ships)
			if (s.hit(point))
				return true;
		return false;
	}
	
	/** Gets a point on the board.
	 * @param point Point to return from board.
	 * @return boolean true if point has been marked before. */
	public boolean getPoint(Point point){
		return grid[point.x][point.y];
	}
	
	/** Adds ship to top of stack.
	 * @param ship Ship to add to stack.
	 * @return False if stack is full. */
	public boolean addShip(Ship ship){
		if (shipIndex > 4)
			return false;
		ships[shipIndex] = ship;
		shipIndex++;
		return true;
	}
	
	/** Checks whether point contains a ship, only works before game has started.
	 * @param point Point to check for ship.
	 * @return True if ship is on that point. */
	public boolean isShip(Point point){
		if (gameStarted || shipIndex == 0)
			return false;
		else
			for (int i = 0; i < shipIndex; i++)
				if (ships[i].isShip(point))
					return true;
		return false;
		
	}
	
	/** Checks for full ship stack.
	 * @return True if ship stack is full. */
	public boolean allShips(){
		return shipIndex == 5;
	}
	
	/** Starts game, stops ship from being placed. */
	public void startGame(){
		gameStarted = true;
	}
	
	/** Checks if a ship has been sank since last call of this method,
	 * @return True if ship has been sank since last
	 * call of this method. */
	public boolean sankShip(){
		boolean[] newShips = new boolean[5];
		for (int i = 0; i < 5; i++){
			newShips[i] = ships[i].hasSank();
		}
		for (int i = 0; i < 5; i++){
			if (newShips[i] != shipsAlive[i]) {
				shipsAlive = newShips;
				return true;
			}
		}
		return false;
	}
	
	/** Checks if all ships have been sank.
	 * @return boolean true if all ships are dead. */
	public boolean gameOver(){
		for (boolean b : shipsAlive)
			if (!b)
				return false;
		return true;
	}
	
}
