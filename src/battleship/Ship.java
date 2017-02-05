package battleship;

public class Ship {
	
	/* Ships are an array of x,y points, starting at one coordinate
	 * and then extending outward length distance either in 
	 * the row or column*/
	
	private int[][] points;
	private int length;
	private int hit;
	
	public Ship(int length){
		this.length = length;
		points = new int[length-1][2];
	}
	
	public void setRow(int x, int y){
		for (int i = 0; i < points.length; i++){
			points[i][0] = x + i;
			points[i][1] = y;
		}
	}
	
	public void setCol(int x, int y){
		for (int i = 0; i < points.length; i++){
			points[i][0] = x;
			points[i][1] = y + i;
		}
	}
	
	/* Does not keep track of which points are hit, board will
	 * do that, only keeps of track of how many times been hit
	 * 
	 * returns true if ship has been sunk, false otherwise.
	 */
	public boolean hit(int x, int y){
		for (int[] arr : points) {
			if (arr[0] == x && arr[1] == y){
				return ++hit == length;
			}
		}
		return false;
	}
}
