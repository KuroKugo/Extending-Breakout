package f1Breakout;

import javax.swing.ImageIcon;
import sf.Sound;
import sf.SoundFactory;

public class Ball extends Sprite implements Commons {

   private int xdir;
   private int ydir;
   private int lives;
   boolean ballTrigger = true;
   
   public final static String DIR = "src/res/";
   public final static String BALL_BOUNCE = DIR + "BallBounce.wav";
   public final static String ball = "/res/ball.png";
   public final static Sound sound = SoundFactory.getInstance(BALL_BOUNCE);

   public Ball() {

     xdir = 2;
     ydir = -2;
     lives = 2;

     ImageIcon ii = new ImageIcon(this.getClass().getResource(ball));
     image = ii.getImage();

     width = image.getWidth(null);
     heigth = image.getHeight(null);

     resetState();
    }

   
    public void move() {
      // make the ball sound
      if (ballTrigger) {
    	  ballBounceS();
      	  ballTrigger = false;
      }
      x += xdir;
      y += ydir;

      // make the ball bounce to the right
      if (x <= 0) {
        setXDir((getXDir() < 0) ? getXDir() * -1 : getXDir());
        ballTrigger = true;
      }

      // make the ball bounce to the left
      if (x >= WIDTH - width) {
        setXDir((getXDir() < 0) ? getXDir() : getXDir() * -1);
        ballTrigger = true;
      }

      // make the ball bounce up
      if (y <= 0) {
        setYDir((getYDir() < 0) ? getYDir() * -1 : getYDir());
        ballTrigger = true;
      }
      
    }
    
    public void moveWithPaddle(int direction) {
      // move the ball with the paddle and play the sound
      if (ballTrigger) {
    	  ballBounceS();
      	  ballTrigger = false;
      }
      setYDir(0);
      setXDir(direction);
      
    }

    public void resetState() {
      x = (int) (Math.random() * 460) + 10;
      y = 710;
    }

    public void setXDir(int x) {
      xdir = x;
    }

    public void setYDir(int y) {
      ydir = y;
    }

    public int getYDir() {
      return ydir;
    }
    
    public int getXDir() {
      return xdir;
    }
    
    public void newLife() {
    	resetState();
    	setYDir((getYDir() < 0) ? getYDir(): getYDir() * -1);
    	lives -= 1;
    }
    
    public boolean haslives() {
    	return lives > 0; 
    }
    
    public int getLives() {
    	return lives;
    }
    
    public void ballBounceS(){
    	SoundFactory.play(sound);
    }
    
}