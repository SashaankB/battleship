package battleship;

public class Board {
	
	private final int B_SIZE = 10; 
	private int[][] coord;
	
	public Board() {
		
		coord = new int[B_SIZE][B_SIZE];
		
	}
	
	public boolean hit(int x, int y) {
		if ( coord[x][y] == 0) {
			coord[x][y] = 1;
			return true;
		}
		
		return false;
	}
	
}
