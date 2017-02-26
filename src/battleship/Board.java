package battleship;

public class Board {
	
	private boolean[][] grid;
	private Ship[] ships;
	private int shipIndex;
	private boolean setShip;
	
	public Board() {
		grid = new boolean[10][10];
		ships = new Ship[5];
		setShip = true;
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
	
	//returns if all ships have been added
	public boolean allShips(){
		return shipIndex == 4;
	}
	
	//ships can no longer be placed
	public void stopPlacing(){
		setShip = false;
	}
	
}
