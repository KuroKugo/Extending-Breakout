package f1Breakout;

public interface Commons {
    public static final int WIDTH = 600;
    public static final int HEIGTH = 800;
    public static final int BOTTOM = 780;
    public static final int PADDLE_RIGHT = 520;
    public static final int BRICK_WIDTH = 75;
    public static final int BRICK_HEIGHT = 15;
    public static final int levels[][][] = { 
    		{	{60, 100, 5}, {140, 100, 5}, {220, 100, 5}, {300, 100, 5}, {380, 100, 5}, {460, 100, 5},
    			{60, 120, 4}, {140, 120, 4}, {220, 120, 4}, {300, 120, 4}, {380, 120, 4}, {460, 120, 4},
    			{60, 140, 3}, {140, 140, 3}, {220, 140, 3}, {300, 140, 3}, {380, 140, 3}, {460, 140, 3},
    			{60, 160, 2}, {140, 160, 2}, {220, 160, 2}, {300, 160, 2}, {380, 160, 2}, {460, 160, 2},
    			{60, 180, 1}, {140, 180, 1}, {220, 180, 1}, {300, 180, 1}, {380, 180, 1}, {460, 180, 1} 
    			},
    		
    		{	{WIDTH/2 - BRICK_WIDTH/2, 100, 2}, 
    			{WIDTH/8 * 4 - BRICK_WIDTH - 10, 120, 2}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 120, 2},
    			{WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 140, 2}, {WIDTH/2 - BRICK_WIDTH/2, 140, 4}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 140, 2},
    			{WIDTH/8 * 3 - BRICK_WIDTH - 30, 160, 2}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 160, 4}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 160, 4}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 160, 2},
    			{WIDTH/2 - BRICK_WIDTH/2 * 5 - 40, 180, 2}, {WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 180, 4}, {WIDTH/2 - BRICK_WIDTH/2, 180, 5}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 180, 4}, {WIDTH/2 + BRICK_WIDTH/2 * 3  + 40, 180, 2}, 
    			{WIDTH/8 * 2 - BRICK_WIDTH - 50, 200, 1}, {WIDTH/8 * 3 - BRICK_WIDTH - 30, 200, 3}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 200, 5}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 200, 5}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 200, 3}, {WIDTH/8 * 7 - BRICK_WIDTH + 50, 200, 1}, 
    			{WIDTH/2 - BRICK_WIDTH/2 * 5 - 40, 220, 1}, {WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 220, 3}, {WIDTH/2 - BRICK_WIDTH/2, 220, 5}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 220, 3}, {WIDTH/2 + BRICK_WIDTH/2 * 3  + 40, 220, 1},
    			{WIDTH/8 * 3 - BRICK_WIDTH - 30, 240, 1}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 240, 3}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 240, 3}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 240, 1},
    			{WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 260, 1}, {WIDTH/2 - BRICK_WIDTH/2, 260, 3}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 260, 1}, 
    			{WIDTH/8 * 4 - BRICK_WIDTH - 10, 280, 1}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 280, 1}, 
    			{WIDTH/2 - BRICK_WIDTH/2, 300, 1} 
    			},
    		{ 	{WIDTH/2 - BRICK_WIDTH/2, 100, 5}, 
    			{WIDTH/8 * 4 - BRICK_WIDTH - 10, 120, 5}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 120, 5},
    			{WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 140, 4}, {WIDTH/2 - BRICK_WIDTH/2, 140, 4}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 140, 4},
    			{WIDTH/8 * 3 - BRICK_WIDTH - 30, 160, 4}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 160, 4}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 160, 4}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 160, 4},
    			{WIDTH/2 - BRICK_WIDTH/2 * 5 - 40, 180, 3}, {WIDTH/2 - BRICK_WIDTH/2 * 3 - 20, 180, 3}, {WIDTH/2 - BRICK_WIDTH/2, 180, 3}, {WIDTH/2 + BRICK_WIDTH/2 + 20, 180, 3}, {WIDTH/2 + BRICK_WIDTH/2 * 3  + 40, 180, 3}, 
    			{WIDTH/8 * 2 - BRICK_WIDTH - 50, 220, 2}, {WIDTH/8 * 3 - BRICK_WIDTH - 30, 220, 2}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 220, 2}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 220, 2}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 220, 2}, {WIDTH/8 * 7 - BRICK_WIDTH + 50, 220, 2}, 
    			{WIDTH/8 * 2 - BRICK_WIDTH - 50, 240, 1}, {WIDTH/8 * 3 - BRICK_WIDTH - 30, 240, 1}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 240, 1}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 240, 1}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 240, 1}, {WIDTH/8 * 7 - BRICK_WIDTH + 50, 240, 1}, 
    			{WIDTH/8 * 2 - BRICK_WIDTH - 50, 260, 1}, {WIDTH/8 * 3 - BRICK_WIDTH - 30, 260, 1}, {WIDTH/8 * 4 - BRICK_WIDTH - 10, 260, 1}, {WIDTH/8 * 5 - BRICK_WIDTH + 10, 260, 1}, {WIDTH/8 * 6 - BRICK_WIDTH + 30, 260, 1}, {WIDTH/8 * 7 - BRICK_WIDTH + 50, 260, 1} 
    			}
    		};
}
