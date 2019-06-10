package f1Breakout;

import javax.swing.JFrame;

import sf.Sound;
import sf.SoundFactory;

@SuppressWarnings("serial")
public class Breakout extends JFrame {
	
	public final static String DIR = "src/res/";
    public final static String BGM1 = DIR + "BreakoutBGM1.wav";
    public final static String BGM2 = DIR + "BreakoutBGM2.wav";
    
    Sound soundBGM1 = SoundFactory.getInstance(BGM1);
	Sound soundBGM2 = SoundFactory.getInstance(BGM2);

    public Breakout() {
        add(new Board());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGTH);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
        loop(200);
    }
    
    // loop the background music n times
    public void loop(int n) {
		float v= (float) -8;
		soundBGM1.setVolume(v);
		soundBGM2.setVolume(v);
    	new Thread(new Runnable() {
			public void run() {
				for(int loop=0; loop < n; loop++) {
					soundBGM1.play();
					soundBGM2.play();
				}
			}
		}).start();
    }

    public static void main(String[] args) {
        new Breakout();
    }
}