package f1Breakout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sf.Sound;
import sf.SoundFactory;
import java.util.List;


@SuppressWarnings("serial")
public class Board extends JPanel implements Commons {

    Image ii;
    Timer timer;
    String message = "Game Over";
    String titleMessage = "Breakout";
    String startMessage = "Press SPACE to start the game";
    String contMessage = "Press SPACE to continue the game";
    String backToMenu = "Press M to return to the menu";
    String quitMessage = "Press Q to quit";
    String livesMessage = "Extra Lives: ";
    String bricksMessage = "Bricks: ";
    String hsMessage = "No HS";
    String recordMessage = "HighScores";
    String pointsMessage = "Score: ";
    String levelMessage = "Press 1, 2, or 3 to select a level then hit SPACE";
    Ball ball;
    Paddle paddle;
    Brick bricks[];
    List<Item> items = new ArrayList<>();
    Item itemList[];
    Obstacle obstacles[];
    
    private Image level1;
    private Image level2;
    private Image level3;
    private int w, h;
    
    public final static String DIR = "src/res/";
    public final static String PADDLE_BOUNCE = DIR + "PaddleBounce.wav";
    public final static String BALL_DEATH = DIR + "BallDeath.wav";
    public final static String EXTRA_LIFE = DIR + "ExtraLife.wav";
    public final static String HIGHSCORES = DIR + "highscores.txt";
    public final static String ITEMS[] = {"PaddleL.png", "PaddleS.png", "StickyPaddle.png", "BallSpeedUp.png"};
    
    boolean ingame = false;
    boolean inMenu = true;
    boolean paused = false;
    boolean itemExists = false;
	boolean powerTimer = false;
	boolean stickyPaddle = false;
	boolean randomBounce = true;
    int timerId;
    int brickCount;
    int score;
    int level;
    int direction;
    private long powerStart;
    private int powerElapsed;
    ArrayList <String> powerUp = new ArrayList<>();
    Timer powerTime;
    
    public final static Sound soundExtraLife = SoundFactory.getInstance(EXTRA_LIFE);
	public final static Sound soundBallDeath = SoundFactory.getInstance(BALL_DEATH);
	
	HighScores highscores = new HighScores(HIGHSCORES);
	ArrayList<Record> records = highscores.load();


    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
        initImages();
        
    } 
    // calls the function to load images and sets standard size
    private void initImages() {
        loadImages();
        
        w = level1.getWidth(this);
        h =  level1.getHeight(this);
        
        setPreferredSize(new Dimension(w, h)); 
    }

    public void addNotify() {
        super.addNotify();
        gameInit();
    }

    // Setup the Game
    public void gameInit() {
    	// Load the Highscores file
    	highscores = new HighScores(HIGHSCORES);
    	records = highscores.load();
    	
    	// Get a new ball and paddle and set the score to 0
        ball = new Ball();
        paddle = new Paddle();
        score = 0;
        
        // Set amount of bricks from the chosen level
        bricks = new Brick[Commons.levels[level].length];
        
        // Create bricks from the selected level
    	for (int j = 0; j < Commons.levels[level].length; j++) {
			bricks[j] = new Brick(Commons.levels[level][j][0],
					Commons.levels[level][j][1],
					Commons.levels[level][j][2]);
        }
    	
    	// Create 3 obstacles and initialize them
    	obstacles = new Obstacle [3];
    	int obstacleCount = obstacles.length;
    	
    	for (int i = 0; i < obstacleCount; i++) {
    		obstacles[i] = new Obstacle(Commons.WIDTH/obstacleCount - Commons.BRICK_WIDTH  + i*150, level);
    	}
    	
        brickCount = bricks.length;
        
        // Initialize the items
        itemList = new Item [ITEMS.length];
        
        for (int i = 0; i < itemList.length; i ++) {
        	itemList[i] = new Item((int) (Math.random() * 550) + 20,
        			(int) (Math.random() * 600) + 40,
        			ITEMS[i]);
        }
        
        // Load the Highscores
        records = highscores.load();
    }


    public void paint(Graphics g) {
        super.paint(g);

        if (ingame) {
        	// When game has started and paused
        	if (paused) {
        		
        		// Show the message on how to continue
            	Font font = new Font("Verdana", Font.PLAIN, 14);
                FontMetrics metr = this.getFontMetrics(font);

                g.setColor(Color.BLACK);
                g.setFont(font);
                g.drawString(contMessage,
                        (Commons.WIDTH - metr.stringWidth(startMessage)) / 2,
                        Commons.WIDTH / 2 + 100);
            }
        	
        	feedback(g);
        	
        	g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                    ball.getWidth(), ball.getHeight(), this);
	        g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
	                    paddle.getWidth(), paddle.getHeight(), this);
	
	        // draw the bricks that are not destroyed
	        for (int i = 0; i < bricks.length; i++) {
	            if (!bricks[i].isDestroyed()) {
	            	g.drawImage(bricks[i].getImage(), bricks[i].getX(),
                            bricks[i].getY(), bricks[i].getWidth(),
                            bricks[i].getHeight(), this);
	            }
	        }
	        // draw items that exits and not destroyed
	        for (Item item : items) {
	        	if (!item.isDestroyed()) {
	        	g.drawImage(item.getImage(), item.getX(), item.getY(),
	                    item.getWidth(), item.getHeight(), this);
	        	}
	        }
	        
	        // draw the obstacle
	        for (Obstacle obstacle : obstacles) {
	        	g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(),
	        			obstacle.getWidth(), obstacle.getHeight(), this);
	        }
        } else {
        	selectMenu(g);
        }


        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    // do something when a key is pressed or released
    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                direction = paddle.dx;
            }

            if (key == KeyEvent.VK_RIGHT) {
                direction = paddle.dx;
            }
        }

        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
            int key = e.getKeyCode();
            
            if(key == KeyEvent.VK_1 && !ingame) {
            	level = 0;
            	brickCount = Commons.levels[level].length;
            }
            if(key == KeyEvent.VK_2 && !ingame) {
            	level = 1;
            	brickCount = Commons.levels[level].length;
            }
            if(key == KeyEvent.VK_3 && !ingame) {
            	level = 2;
            	brickCount = Commons.levels[level].length;
            }
            
            if (key == KeyEvent.VK_R) {
        		randomBounce = !randomBounce;
            }
            if (key == KeyEvent.VK_SPACE) {
            	
            	if (!ingame) {
            		gameInit();
            		inMenu = false;
                	ingame = true;
                	if (timer == null) {
                		timer = new Timer();
	                    timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
                	}	
            	}
            	if (paused) {
            		paused = false;
            	}
            	stickyPaddle = false;
            }
            if (key == KeyEvent.VK_Q) {
            	System.exit(0);;
            }
            if (key == KeyEvent.VK_LEFT) {
                direction = paddle.dx;

            }

            if (key == KeyEvent.VK_RIGHT) {
                direction = paddle.dx;
            }
            if (key == KeyEvent.VK_M) {
            	if (!ingame) {
	                ingame = false;
	                inMenu = true;
	                paused = false;
            	}
            }
            if (key == KeyEvent.VK_Z) {
            	highscores.clear();
            	highscores.save();
            }
          
        }
    }


    class ScheduleTask extends TimerTask {
	
    	public void run() {
        	
        	
        	if (ingame && !paused) {
                paddle.move();
        		ball.move();
        		moveObstacles();
        		checkCollision();
        	}
        	if (paused) {
        		paddle.move();
        	}

            repaint();

        }
    }

    // stop the game and display the end game screen
    public void stopGame() {
    	if (powerTime != null) {
			powerTime.cancel();
			powerUp.remove("paddleSpeed.png");
		}
    	items  = new ArrayList<>();
    	
    	// ask for a name and if no input enter default name
    	String name = JOptionPane.showInputDialog("Enter First 3 Initials");
    	System.out.println(name);
    	if (name != null) {
    		records.add(new Record(name, score));
    	} else {
    		name = "CAS";
    	}
    	
    	// save the highscores
    	int count = highscores.save();
		System.out.println("saved:");
		System.out.println(highscores);
		System.out.println(count+" records");
		
		String allSaved = count == records.size() ? "YES" : "no";
		System.out.println("all saved: "+allSaved);
        ingame = false;
    }
    
    // when the ball is destroyed use a life or end the game
    public void useLife() {
    	if (ball.haslives()) {
        	ball.newLife();
        	paused = true;
    		SoundFactory.play(soundExtraLife);
        } else {
        	SoundFactory.play(soundBallDeath);
        	message = "Game Over";
        	stopGame();
        }
    }
    
    // manages all the feedback the user sees (Score, Bricks, Lives)
    public void feedback(Graphics g) {
    	
    	livesMessage = "Extra Lives: " + ball.getLives();
        bricksMessage = "Bricks: " + brickCount;
        pointsMessage = "Score: " + score;
        
    	Font font = new Font("Verdana", Font.BOLD, 15);
        FontMetrics metr = this.getFontMetrics(font);
        
        String highscore = "Highscorer: " + hsMessage;
              

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(highscore, metr.stringWidth(highscore) / 8,
                metr.getHeight());
        
        
        g.drawString(livesMessage, Commons.WIDTH - metr.stringWidth(livesMessage)
        		- metr.stringWidth(bricksMessage) - 15,
                metr.getHeight());
        
        g.drawString(bricksMessage, 
        		Commons.WIDTH - metr.stringWidth(bricksMessage) - 5,
                metr.getHeight());
        
        g.drawString(pointsMessage, metr.stringWidth(pointsMessage),
                metr.getHeight() * 2);
    }
    
    // show the start menu selection screen or the end screen
    public void selectMenu(Graphics g) {
    	if (inMenu) {
    		Font font = new Font("Verdana", Font.BOLD, 50);
            FontMetrics metr = this.getFontMetrics(font);
 
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(titleMessage,
                         (Commons.WIDTH - metr.stringWidth(titleMessage)) / 2,
                         Commons.WIDTH / 2);
            
            font = new Font("Verdana", Font.PLAIN, 14);
            metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(startMessage,
                    (Commons.WIDTH - metr.stringWidth(startMessage)) / 2,
                    Commons.WIDTH / 2 + 100);
            g.drawString(quitMessage,
                    (Commons.WIDTH - metr.stringWidth(quitMessage)) / 2,
                    Commons.WIDTH / 2 + 140);
            
            g.drawString(levelMessage,
                    (Commons.WIDTH - metr.stringWidth(levelMessage)) / 2,
                     550);

        	Graphics2D g2d = (Graphics2D)g;
        	AffineTransform a = AffineTransform.getScaleInstance(0.3, 0.3);
        	g2d.setTransform(a);
        	g2d.drawImage(level1, 100, 4000, null);
        	g2d.drawImage(level2, 1400, 4000, null);
        	g2d.drawImage(level3, 2600, 4000, null);
    	} else {

            Font font = new Font("Verdana", Font.BOLD, 30);
            FontMetrics metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(message,
                         (Commons.WIDTH - metr.stringWidth(message)) / 2,
                         Commons.WIDTH / 2);
            
            g.drawString(recordMessage,
                    (Commons.WIDTH - metr.stringWidth(recordMessage)) / 2,
                    30);
            
            font = new Font("Verdana", Font.PLAIN, 14);
            metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(backToMenu,
                    (Commons.WIDTH - metr.stringWidth(backToMenu)) / 2,
                    Commons.WIDTH / 2 + 100);
            g.drawString(quitMessage,
                    (Commons.WIDTH - metr.stringWidth(quitMessage)) / 2,
                    Commons.WIDTH / 2 + 140);
            
            int i = 40;
            for (Record record : records) {
            	String strRecord = record.getName() + ": " + record.getScore();
            	g.drawString(strRecord,
                        (Commons.WIDTH - metr.stringWidth(strRecord)) / 2,
                        i += 20);
            }
    	}
    }
    
    // Load all the images to view
    private void loadImages() {
        
        ImageIcon i1 = new ImageIcon("src/res/Level1.png");
        level1 = i1.getImage();  
        
        ImageIcon i2 = new ImageIcon("src/res/Level2.png");
        level2 = i2.getImage(); 
        
        ImageIcon i3 = new ImageIcon("src/res/Level3.png");
        level3 = i3.getImage(); 
    }
    
    // Move the Obstacles
    private void moveObstacles() {
		// TODO Auto-generated method stub
		for (Obstacle obstacle : obstacles) {
			obstacle.move();
		}
	}

    public void checkCollision() {

    	// Check if the ball hit the bottom of the screen
        if (ball.getRect().getMaxY() > Commons.BOTTOM) {
            useLife();
        }
        
        
        // check if each item as intersected the ball
        for (int i = 0; i < items.size(); i++) {
            if ((ball.getRect()).intersects(items.get(i).getRect()) && !items.get(i).isDestroyed()) {
            	System.out.println("Hit Item");
        		int minY = (int) paddle.getRect().getMinY();
        		int minX = (int) paddle.getRect().getMinX();
        		
        		// get the new paddle for the power
        		paddle = new Paddle(minX, minY, items.get(i).getEffect());
        		
        		// start the timer for power ups
        		powerStart = System.currentTimeMillis();
        		powerTimer = true;
        		
        		// If the power is the sticky paddle set the trigger to true
        		if (powerUp.contains("paddleSick.png") && items.get(i).getEffect() == "paddleSick.png") {
        			System.out.println("Activate");
        			stickyPaddle = true;
        		}
        		// if the power is speed up create a new timer to increase the framerate
        		if (powerUp.contains("paddleSpeed.png")&& items.get(i).getEffect() == "paddleSpeed.png") {
        			if (powerTime == null) {
	        			powerTime = new Timer();
	        			powerTime.scheduleAtFixedRate(new ScheduleTask(), 1000, 5);
    	    		}
        		}
        		
        		// set the items to destroyed and play the sound
        		items.get(i).setDestroyed(true);
        		items.get(i).playSound();
        		
            }
            // if the timer is on check if 10 seconds have passed and turn them off
            if (powerTimer) {
            	powerElapsed = (int) (System.currentTimeMillis() - powerStart);
    			if (powerElapsed >= 10000) {
    				int minY = (int) paddle.getRect().getMinY();
    	    		int minX = (int) paddle.getRect().getMinX();
    	    		paddle = new Paddle(minX, minY, "paddle.png");
    	    		powerTimer = false;
    	    		if (powerTime != null) {
    	    			powerTime.cancel();
    	    			powerTime = null;
    	    			powerUp.remove("paddleSpeed.png");
    	    		}
    	    		for (Item item : items) {
    	    			if (item.getItemName() == "StickyPaddle.png" && items.get(i).isDestroyed()) {
            	    		stickyPaddle = false;
            	    		powerUp.remove("paddleSick.png");
        	    		}
    	    		}
    			}
            }
        }
        
        // check if all the bricks have been destroyed
        for (int i = 0, j = 0; i < bricks.length; i++) {
            if (bricks[i].isDestroyed()) {
                j++;
            }
            if (j == bricks.length) {
                message = "Victory";
                stopGame();
            }
            
        }
        
        // check if the ball hits the paddle
        if ((ball.getRect()).intersects(paddle.getRect())) {
        	
        	// make the ball stick to the paddle if power up is on
        	if (stickyPaddle && powerTimer) {
    			ball.moveWithPaddle(direction);
        	} else { 
        		// play the paddle sound when the ball is heading up the screen
        		if (ball.getYDir() > 0) {
	            	paddle.paddleBounceS();
	            }
        		
        		// if random bounce is on set the random bounce to the ball
        		int randBounce = (int) (Math.random() * 100);
        		
        		if (randomBounce && randBounce <= 25) {
        			int r = (int) (Math.random() * 3);
        			System.out.println(r);
        			
        			int paddleLPos = (int)paddle.getRect().getMinX();
    	            int ballLPos = (int)ball.getRect().getMinX();
    	            
    	            int paddleWidth = (int) paddle.getWidth();
    	
    	            int third = paddleLPos + paddleWidth/7 * 3;
    	            int fourth = paddleLPos + paddleWidth/7 * 4;
    	            	
    	            if (ballLPos < third) {
    	            	ball.setXDir(r*-1);
    	                ball.setYDir(-2);
    	            }
    	
    	            if (ballLPos >= third && ballLPos < fourth) {
    	            	ball.setXDir(0);
    	                ball.setYDir(-2);
    	            }
    	            if (ballLPos >= fourth) {
    	                ball.setXDir(r*1);
    	                ball.setYDir(-2);
    	            }
    	            
    	            if (ball.getYDir() > 1) {
    	            	paddle.paddleBounceS();
    	            }
        		} else {
        			// bounce the ball like in original breakout
        			int paddleLPos = (int)paddle.getRect().getMinX();
    	            int ballLPos = (int)ball.getRect().getMinX();
    	            
    	            int paddleWidth = (int) paddle.getWidth();
    	
    	            int first = paddleLPos + paddleWidth/7;
    	            int second = paddleLPos + paddleWidth/7 * 2;
    	            int third = paddleLPos + paddleWidth/7 * 3;
    	            int fourth = paddleLPos + paddleWidth/7 * 4;
    	            int fifth = paddleLPos + paddleWidth/7 * 5;
    	            int sixth = paddleLPos + paddleWidth/7 * 6;
    	
    	            if (ballLPos < first) {
    	            	
    	                ball.setXDir(-2);
    	                ball.setYDir(-1);
    	            }
    	
    	            if (ballLPos >= first && ballLPos < second) {
    	                ball.setXDir(-2);
    	                ball.setYDir(-2);
    	            }
    	
    	            if (ballLPos >= second && ballLPos < third) {
    	            	ball.setXDir(-1);
    	                ball.setYDir(-2);
    	            }
    	
    	            if (ballLPos >= third && ballLPos < fourth) {
    	            	ball.setXDir(0);
    	                ball.setYDir(-2);
    	            }
    	            if (ballLPos >= fourth && ballLPos < fifth) {
    	                ball.setXDir(1);
    	                ball.setYDir(-2);
    	            }
    	            if (ballLPos >= fifth && ballLPos < sixth) {
    	                ball.setXDir(2);
    	                ball.setYDir(-2);
    	            }
    	            if (ballLPos > sixth) {
    	                ball.setXDir(2);
    	                ball.setYDir(-1);
    	            }
        		}  
        	}
        }

        // check if the ball has collided with a brick
        for (int i = 0; i < bricks.length; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {
            	// allow the ball to stick if it collides with brick
            	for (Item item : items) {
            		if (powerUp.contains("paddleSick.png") 
            				&& item.getEffect() == "paddleSick.png" 
            				&& powerTimer && item.isDestroyed()) {
            			stickyPaddle = true;
            		}
            	}
            	
            	int brickLPos = (int)bricks[i].getRect().getMinX();
                int ballMPos = (int)ball.getRect().getMinX() + ball.width/2;

                int first = brickLPos + 12;
                int second = brickLPos + 25;
                int third = brickLPos + 37;
                int fourth = brickLPos + 50;
                int fifth = brickLPos + 62;

                if (!bricks[i].isDestroyed()) {
                	int changeDir = (ball.getYDir() < 0) ? 1 : -1;
	                if (ballMPos < first) {
	                	
	                    ball.setXDir(-2);
	                    ball.setYDir(1 * changeDir);
	                }
	
	                if (ballMPos >= first && ballMPos < second) {
	                    ball.setXDir(-2);
	                    ball.setYDir(2 * changeDir);
	                }
	
	                if (ballMPos >= second && ballMPos < third) {
	                	ball.setXDir(-1);
	                    ball.setYDir(2 * changeDir);
	                }
	                if (ballMPos >= third && ballMPos < fourth) {
	                    ball.setXDir(1);
	                    ball.setYDir(2 * changeDir);
	                }
	                if (ballMPos >= fourth && ballMPos < fifth) {
	                    ball.setXDir(2);
	                    ball.setYDir(2 * changeDir);
	                }
	                if (ballMPos > fifth) {
	                    ball.setXDir(2);
	                    ball.setYDir(1 * changeDir);
	                }
	                
	                // have a 25% chance of spawning an item
	                if (25 >= ((int) (Math.random() * 100))) {
	                	int randItem = (int) (Math.random() * 100) % itemList.length;
	                	if (!items.contains(itemList[randItem])) {
	                    	items.add(itemList[randItem]);

	                    	String index = ITEMS[randItem];
	                    	if (!powerUp.contains(index)) {
	                    		powerUp.add(itemList[randItem].getEffect());
	                    	}
	                	}
	                }
                    
                    bricks[i].setDestroyed(true);
                    brickCount -=1;
                    score += bricks[i].getHp();
                }
              
            }
        }
        
      // check if the ball has collided with an obstacle ONLY if its heading upwards
      for (Obstacle obstacle : obstacles) {
      	
          if ((ball.getRect()).intersects(obstacle.getRect()) && ball.getYDir() <0) {
          	for (Item item : items) {
          	// allow the ball to stick if it collides with an obstacle
        		if (powerUp.contains("paddleSick.png") 
        				&& item.getEffect() == "paddleSick.png" 
        				&& powerTimer && item.isDestroyed()) {
        			stickyPaddle = true;
        		}
        	}
          	
          	// bounce the ball exactly back it was coming
            int x = ball.getXDir() * -1;
          	int y = ball.getYDir() * -1;
          	
          	ball.setXDir(x);
          	ball.setYDir(y);
            
              if (ball.getYDir() > 0) {
              	obstacle.playSound();
	            }
              
          }
      }
    }
}

