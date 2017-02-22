package battleship;

public class Board {
	
	private boolean[][] grid;
	
	public Board() {
		grid = new boolean[10][10];
	}
	
	public void setPoint(int x, int y) {
		grid[x][y] = true;
	}
	
	public boolean getPoint(int x, int y){
		return grid[x][y];
	}
	
}
