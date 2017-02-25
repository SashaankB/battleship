package battleship;

import java.util.Random;

public class AI {	
	//Keeps track of either hunt/target mode
	private boolean hunt;

	
	
	
	
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
	public static int[] randomShip(int [] s){
		Random randomGenerator = new Random();
		int randx = randomGenerator.nextInt(10);
		System.out.println("Random x = " + randx);
		int randy = randomGenerator.nextInt(10);
		System.out.println("Random y = " + randy);
		
		try{
			
		}
		catch(Exception e){
			
		}
		s = new int[2];
		s[0] = randx;
		s[1] = randy;
		return s;
	}
	
	/*Parameter is a board instance
	 * Returns point to hit for next turn
	 * should eventually utilize hunt/target
	 * mode to make better decisions.
	 */
	public Point nextTurn(Board board){
		return null;
		
	}
}
