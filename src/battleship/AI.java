package battleship;

import java.util.ArrayList;
import java.util.Random;
/** AI class for design making. 
 * @author Kellin McAvoy, Nathan Kelderman, Sean Thomas */
public class AI {	
	
	//AI Logic for setting the five ships
	//Preferably random with ships not touching
//	public Ship[] setShips(){
//		Random randomGenerator = new Random();
//		int randx = randomGenerator.nextInt(9);
//		System.out.println("Random x = " + randx);
//		int randy = randomGenerator.nextInt(9);
//		System.out.println("Random y = " + randy);
//		Point p = new Point(randx, randy);
//		boolean randomdirection = Math.random() < 0.5;		
//		int shipAmount = 5;
//		int length = 5;
//		Ship [] aiShip = new Ship[3];
//		while (shipAmount > 0){
//			Ship s = new Ship(p, randomdirection, length);
//			shipAmount--;
//			length--;
//		}
//		aiShip[0] = p;
//		
//
//		return aiShip;
//	}
	
	public static int [] randomship(Ship ships){
		Random randomGenerator = new Random();
		int randx = randomGenerator.nextInt(10);
		System.out.println("Random x = " + randx);
		int randy = randomGenerator.nextInt(10);
		System.out.println("Random y = " + randy);
		
		int shipSize = 0;
		//int shipSize = ships.getShipLength();
		int valid = 11-shipSize;
		
		System.out.println("ship size" + shipSize);
		if(randx <= valid){
			System.out.println("valid ship placement");
		}
		else if(randy <= valid){
			System.out.println("valid placement");
		}
		else{
			System.out.println("no valid");
			
		}
		int [] start = new int[2];
		start[0] = randx;
		start[1] = randy;
		return start;
	}
	
	
	/** Generates a move for next turn based on status of board. 
	 * @param board The current instance of the board. 
	 * @return Point The next point for the AI to choose. */
	public Point nextTurn(Board board) {
		Point shot = randomPoint();
		while (board.getPoint(shot)) {
			shot = randomPoint();
		}
		return shot;
	}
	
	/** Creates a random point with two integers between 0-9.
	 * @return Point New random point. */
	private Point randomPoint() {
		return new Point((int) (Math.random() * 9.99), (int) (Math.random() * 9.99)); 
	}
	
	/*	Good code for parity later when hunt/target is applied.
	    int row;
		int col;
		//checks to see if currentRow is out of bounds
		if ( currentRow == 10){
			row = 0;
			currentRow = 0;
		} else {
			row = currentRow++;
		}
		//randomly selects a column in the given row
		//if the row is even it selects an even column and vice versa
		if (row % 2 == 0) {
			col = 0 + 2 * (int) (Math.random() * ((8 - 0) / 2 + 1)); 
		} else {
			col = 1 + 2 * (int) (Math.random() * ((9 - 1) / 2 + 1)); 
		}
	*/
}
