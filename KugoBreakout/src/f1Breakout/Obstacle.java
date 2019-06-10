package f1Breakout;

import sf.SoundFactory;

public class Obstacle extends Brick implements Commons {
	int level;
	int xdir = 1;
	int ydir;
	private final int upperBound;

	public Obstacle(int x, int level) {
		super(x, levels[level][levels[level].length - 1][1] + 100, 0);
		this.level = level;
		
		upperBound = levels[level][levels[level].length - 1][1] + 100;
		
		// TODO Auto-generated constructor stub
	}
	
	public void move() {

	  // make the brick appear on the left side of the screen
      if (x >= WIDTH) {
    	  x = 0 - BRICK_WIDTH;
      }

      // move upwards when 300px from the bottom of the screen
      if (y >= HEIGTH - 300) {
    	  ydir = -1;
      }
      
      // move downwards from the upper bound of the obstacles
      if (y <= upperBound) {
    	  ydir = 1;
      }
      
      x += xdir;
      y += ydir;
	}
	
	public void playSound() {
		SoundFactory.play(sound);
	}

}
