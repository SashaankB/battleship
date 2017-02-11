package battleship;

public enum Stages {
	/* NE - position not shot at and there is no ship
	 * NS - position not shot at and there is a ship there
	 * HE - position was shot at and there is no ship
	 * HS - position shot at and there is a ship there
	 */
	
	NE, NS, HE, HS;
	
	public Stages switchStage() {
		if ( this == NE)
			return HE;
		if ( this == NS)
			return HS;
		return this;
		
	}
	

}
