package f1Breakout;

import javax.swing.ImageIcon;

import sf.Sound;
import sf.SoundFactory;

public class Item extends Sprite {

	protected String item = "/res/item.png";
	protected String png;
	public final static String DIR = "src/res/";
	public final static String ITEM_SOUND = DIR + "item.wav";
	public final static String PNGDIR = "/res/";
	Sound sound = SoundFactory.getInstance(ITEM_SOUND);
	boolean destroyed;
	boolean visable = false;

    public Item(int x, int y, String itemExt) {
      this.x = x;
      this.y = y;
      png = itemExt;
      item = PNGDIR + png;

      ImageIcon ii = new ImageIcon(this.getClass().getResource(item));
      image = ii.getImage();
      
	    width = image.getWidth(null);
	    heigth = image.getHeight(null); 
    }
    
    public boolean isDestroyed() {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
      this.destroyed = destroyed;
    }
    
    // return the paddle png with the associated effect
    public String getEffect() {
    	String effect = "";
    	if ("PaddleL.png" == png) {
    		effect = "paddleLong.png";
    	}
    	if ("PaddleS.png" == png) {
    		effect = "paddleSmall.png";
    	}
    	if ("StickyPaddle.png" == png) {
    		effect = "paddleSick.png";
    	}
    	if ("BallSpeedUp.png" == png) {
    		effect = "paddleSpeed.png";
    	}
    	
    	return effect;
    }
    
    public boolean isVisable() {
    	return visable;
    }
    
    public void setVisable(boolean visable) {
    	this.visable =visable;
    }
    
    public String getItemName() {
    	return png;
    }
    
    public void playSound() {
    	SoundFactory.play(sound);
    }
}
