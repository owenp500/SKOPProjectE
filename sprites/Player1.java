
public class Player1 extends Player {
	
	public int health = 5;

	public Player1(int centerX, int centerY, String imageFolder) {
		
		super(centerX, centerY, imageFolder);
		//assigning the control scheme for this player type
		//attack: i || left: j || right l|| down: k
		super.setKeys(73,74,76,75);
		super.setFacingRight(false);
		
		//assigns whether the player is 'player1' or player'1'
		super.setPlayer(1);
		
	}

	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int dmg) {
		health -= dmg;
	}

}