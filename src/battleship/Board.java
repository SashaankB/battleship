package battleship;

public class Board {
	
	private boolean[][] grid;
	private Ship[] ships;
	
	public Board() {
		grid = new boolean[10][10];
		ships = new Ship[5];
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
	
	public void setShips(Ship[] s){
		ships = s;
	}
	
}
