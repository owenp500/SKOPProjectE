
public class Player1 extends Player {

	public Player1(int centerX, int centerY) {
		
		super(centerX, centerY);
		//assigning the control scheme for this player type
		//attack: i || left: j || right l|| down: k
		super.setKeys(73,74,76,75);
	}
	
	
	
	
}