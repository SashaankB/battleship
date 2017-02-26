package battleship;

import java.util.Random;

public class AI {	
	//Keeps track of either hunt/target mode
	private boolean hunt;
	private int currentRow;
	
	public AI() {
		currentRow = 0;
		hunt = true;
	}
	

	
	
	
	
	//AI Logic for setting the five ships
	//Preferably random with ships not touching
	public Ship[] setShips(){
		
		return null;
		
	}
	
//	public static int [] randomship(Ship ships){
//		Random randomGenerator = new Random();
//		int randx = randomGenerator.nextInt(10);
//		System.out.println("Random x = " + randx);
//		int randy = randomGenerator.nextInt(10);
//		System.out.println("Random y = " + randy);
//		
//		int shipSize = ships.getShipSize();
//		int valid = 11-shipSize;
//		
//		System.out.println("ship size" + shipSize);
//		if(randx <= valid){
//			System.out.println("valid ship placement");
//		}
//		else{
//			System.out.println("no valid");
//			
//		}
//		int [] start = new int[2];
//		start[0] = randx;
//		start[1] = randy;
//		return start;
//	}
	

	public void randomShip(){
		Random randomGenerator = new Random();
		int randx = randomGenerator.nextInt(10);
		System.out.println("Random x = " + randx);
		int randy = randomGenerator.nextInt(10);
		System.out.println("Random y = " + randy);
		Point p = new Point(randx, randy);
		boolean randomdirection = Math.random() < 0.5;		
		
		Ship s = new Ship(p, randomdirection, location);

	}
	
	/*Parameter is a board instance
	 * Returns point to hit for next turn
	 * should eventually utilize hunt/target
	 * mode to make better decisions.
	 */
	public Point nextTurn(Board board){
		int row;
		int col;
		//checks to see if currentRow is out of bounds
		if ( currentRow == 10){
			row = 0;
			currentRow = 0;
		}
		else
			row = currentRow++;
		//randomly selects a column in the given row
		//if the row is even it selects an even column and vise versa
		if ( row%2 == 0)
			col = 0 + 2*(int)(Math.random()*((8-0)/2+1)); 
		else
			col = 1 + 2*(int)(Math.random()*((9-1)/2+1)); 
		Point shot = new Point(col, row);
		if ( !board.getPoint(shot))
			return shot;
		return nextTurn(board);
	}
}
