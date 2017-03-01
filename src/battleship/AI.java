package battleship;

import java.util.Random;
/** AI class for design making. 
 * @author Kellin McAvoy, Nathan Kelderman, Sean Thomas */
public class AI {	
	
	/** Constructs a board with all 5 ships placed on it in random locations.
	 * @return Board board with all 5 ships placed. */
	public Board setShips() {
		boolean[] addShips = new boolean[5];
		int[] lengths = {2, 3, 3, 4, 5};
		Board board = new Board();
		while (!boolArray(addShips)) {
			for (int i = 0; i < 5; i++) {
				if (!addShips[i]) {
					addShips[i] = board.addShip(randomShip(lengths[i]));
				}
			}
		}
		return board;
	}
	
	/** Evaluates an array of booleans.
	 * @param bool Array of booleans.
	 * @return true if and only if all values are true.*/
	private boolean boolArray(boolean[] bool) {
		for (boolean b : bool) {
			if (!b) {
				return false;
			}
		}
		return true;
	}
	
	/** Creates a ship with random point and direction.
	 * @param length Integer length of ship.
	 * @return Ship new random ship. */
	private Ship randomShip(int length) {
		Point p = randomPoint();
		Random random = new Random();
		boolean direction = random.nextBoolean();
		Ship ship;
		try {
			ship = new Ship(p, direction, length);
		} catch (IndexOutOfBoundsException e) {
			return randomShip(length);
		}
		return ship;

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
		Random random = new Random();
		return new Point(random.nextInt(10), random.nextInt(10)); 
	}
	
	/*	Unfinished Code for Release Two::
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
