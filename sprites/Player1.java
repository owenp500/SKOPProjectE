import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player1 implements DisplayableSprite, MovableSprite, CollidingSprite { 
	private double elapsedFrames = 0;
	private int currentFrame = 0;

	private final static int FRAMES = 4;
	private double framesPerSecond = 30;
	private static Image[] frames = new Image[FRAMES];
	private static boolean framesLoaded = false;	

	

	private double centerX = 0;
	private double centerY = 0;
	private double height = 50;
	private double width = 50;
	private boolean dispose = false;
	private boolean isAtExit = false;
	private static String proximityMessage;
	
	
	private double velocityX = 0;
	private double velocityY = 0;
	private double revolutions;
	private long score =  0;
	private int health = 5;
	
	//y acceleration affects jump height and how fast you can slam to the ground
	private final double ACCELERATION_Y = 750;
	private final double GRAVITY = 1500;
	
	public Player1(int centerX, int centerY) { 
		this.centerX = centerX;
		this.centerY = centerY;
		
		if (framesLoaded == false) {
			for (int frame = 0; frame < FRAMES; frame++) {
				String filename = String.format("res/OCP/saw(%d).png" , frame);
				
				try {
					frames[frame] = ImageIO.read(new File(filename));
				}
				catch (IOException e) {
					System.err.println(e.toString());
				}		
			}
			
			if (frames[0] != null) {
				framesLoaded = true;
			}
		}		
	}	
	public Image getImage() {		
		return frames[currentFrame];
	}
	public void setCenterX(double centerX) {		
		this.centerX = centerX;
	}
	
	public void setVelocityY(double pixelsPerSecond) {		
		this.velocityY = pixelsPerSecond;
	}
	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}
	
	public void setVelocityX(double pixelsPerSecond) {		
		this.velocityX = pixelsPerSecond;
	}
	
	public boolean getVisible() {
		return true;
	}

	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		
		return height;
	}
	
	public double getWidth() {
		
		return width;
	}
	
	public double getCenterX() {
		
		return centerX;
	}
	
	public double getCenterY() {
		
		return centerY;
	}
	public double getVelocityX() {
		return velocityX;
	}
	public double getVelocityY() {
		return velocityY;
	}
	public double getRevolutions() {
		return revolutions;
	}
	
	public boolean getDispose() {
		
		return dispose;
	}
	public int getHealth() {
		return health;
	}

	
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}
						
	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;
		
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.pixelBasedOverlaps(this,sprite , deltaX, deltaY)) {
					colliding = true;
					
					break;					
				}
			}
		}		
		return colliding;		
	}
	
	public long getScore() {
		return score;
	}
	public String getProximityMessage() {	
		return proximityMessage;
	}
	
	public boolean getIsAtExit() {
		return isAtExit;
	}
	
	
	//this method is designed so that you have less grip while on the ceiling.
	//rather than calculating the average between the rev and vel I sort of calculate the 'average of the average'.
	// if v = 0 and r = 50
	// on the ground they both average out to 25
	// while on the roof in the same situation v = 12.5 and r= 37.5;
	//(r + v) / 2 = average
	//((r + v) / 2 + v) / 2 = average between average and velocity
	// simplified: (r + 3v) / 4 
	
	
	
	
	//TODO! start of update function 
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		velocityX -= velocityX/8;
		//constant downward y velocity for gravity
		if (keyboard.keyDown(74)) {
			if (velocityX>= -100) {
			velocityX -= 40; 
			}
		}
		//
		if (keyboard.keyDown(76)) {
			if(velocityX <= 100) {
				velocityX += 40;
			}
		}
		//revolutions += revolutions/20;
		double deltaX = actual_delta_time * 0.001 * velocityX;
		
		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);		
		
		if (collidingBarrierX) {
			velocityX  = 0;
		}
		if (collidingBarrierX == false) {
			this.centerX += actual_delta_time * 0.001 * velocityX;
		}
					
		
		
		double frameDifference =  revolutions / 28 * actual_delta_time * 0.001;
		this.elapsedFrames +=  (frameDifference);
		currentFrame = (int) Math.abs(this.elapsedFrames % FRAMES);		
		
	}
	//TODO! end of update function!
}