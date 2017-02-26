package battleship;

public class Board {
	
	private boolean[][] grid;
	private Ship[] ships;
	private int shipIndex;
	private boolean setShip;
	private boolean[] shipsAlive;
	
	public Board() {
		grid = new boolean[10][10];
		ships = new Ship[5];
		setShip = true;
		shipsAlive = new boolean[5];
	}
	
	//returns true if point hit a ship
	public boolean setPoint(Point p) {
		grid[p.x][p.y] = true;
		for (Ship s : ships)
			if (s.hit(p))
				return true;
		return false;
	}
	
	public boolean getPoint(Point p){
		return grid[p.x][p.y];
	}
	
	public boolean addShip(Ship s){
		if (shipIndex > 4)
			return false;
		ships[shipIndex] = s;
		shipIndex++;
		return true;
	}
	
	public boolean isShip(Point p){
		if (!setShip || shipIndex == 0)
			return false;
		else
			for (int i = 0; i < shipIndex; i++)
				if (ships[i].isShip(p))
					return true;
		return false;
		
	}
	
	//returns true if all ships have been added
	public boolean allShips(){
		return shipIndex == 5;
	}
	
	//ships can no longer be placed
	public void stopPlacing(){
		setShip = false;
	}
	
	//returns true if another ship has been sank !SINCE LAST CALL!
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
	
	//returns true if all ships are dead
	public boolean gameOver(){
		for (boolean b : shipsAlive)
			if (!b)
				return false;
		return true;
	}
	
}
