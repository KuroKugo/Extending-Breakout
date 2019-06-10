package f1Breakout;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import sf.Sound;
import sf.SoundFactory;


public class Paddle extends Sprite implements Commons {

	public final static String DIR = "src/res/";
	public final static String PADDLE_BOUNCE = DIR + "PaddleBounce.wav";
    public final static String paddle = "/res/paddle.png";
    
    Sound sound = SoundFactory.getInstance(PADDLE_BOUNCE);

    int dx;

    public Paddle() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(paddle));
        image = ii.getImage();

        width = image.getWidth(null);
        heigth = image.getHeight(null);
        
        sound.setVolume(-8);

        resetState();

    }
    
    // recreate the paddle with the png that was passed
    public Paddle(int xCor, int yCor, String png) {
    	String path = "/res/"+ png;
    	x = xCor;
    	y = yCor;

        ImageIcon ii = new ImageIcon(this.getClass().getResource(path));
        image = ii.getImage();

        width = image.getWidth(null);
        heigth = image.getHeight(null);
        
        sound.setVolume(-8);
    }

    public void move() {
        x += dx;
        if (x <= 2) { 
          x = 2;
        }
        
        // don't let the paddle move father than right most point of the screen
        if (x >= Commons.WIDTH - width) {
          x = Commons.WIDTH - width;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -2;

        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
        
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
    
    public void paddleBounceS() {
    	SoundFactory.play(sound);
    }

    public void resetState() {
        x = 400;
        y = 720;
    }
}
