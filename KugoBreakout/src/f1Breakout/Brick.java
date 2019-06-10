package f1Breakout;

import javax.swing.ImageIcon;

import sf.Sound;
import sf.SoundFactory;

public class Brick extends Sprite {

    String brickie = "/res/brickie.png";
    String DIR = "/res/";
    String BRICK_SOUND;

    boolean destroyed;
    int hp;
    Sound sound;


    public Brick(int x, int y, int hp) {
      this.x = x;
      this.y = y;
      this.hp = hp;
      
      String brick = DIR + hp + ".png";

      ImageIcon ii = new ImageIcon(this.getClass().getResource(brick));
      image = ii.getImage();
      
      width = image.getWidth(null);
      heigth = image.getHeight(null);
    
      BRICK_SOUND = "src" + DIR + "Brick" + hp + ".wav";
      sound = SoundFactory.getInstance(BRICK_SOUND);

      destroyed = false;
    }

    public int getHp() {
    	return hp;
    }
    
    public boolean isDestroyed() {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
      this.destroyed = destroyed;
      SoundFactory.play(sound);
    }

}