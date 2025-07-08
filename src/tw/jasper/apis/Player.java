package tw.jasper.apis;

import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Player {
	private float playerX, playerY;
	private int playerWidth, playerHeight;
	private float velocityY = 0;
	private final float GRAVITY = 1.0f;
	private final float JUMP_STRENGTH = -17;
	private final int MOVE_SPEED = 20;
	private boolean onGround = false;
	private BufferedImage playerImg;
	private GamePanel panel;

	
	
	public Player(float x, float y, GamePanel panel) {
		this.panel = panel;
		playerX = x;
		playerY = y;
		try {
			playerImg = ImageIO.read(new File("image/player_stand.png"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		playerWidth = playerImg.getWidth();
		playerHeight = playerImg.getHeight();
		System.out.println("create player");
	}

	public BufferedImage getImg() {
		System.out.println("test getImg");
		return playerImg;
	}

	public void checkCollision(LinkedList<Platfrom> platforms) {
		for (Platfrom p : platforms) {
			boolean isFalling = velocityY > 0;
			boolean isAbove = playerY + playerHeight <= p.getTop();
			boolean willLand = playerY + playerHeight + velocityY >= p.getTop();
			boolean inHorizontalRange = playerX + playerWidth > p.getLeft() && playerX < p.getRight();
			
			if (isFalling && isAbove && willLand && inHorizontalRange) {
				playerY = p.getTop() - playerHeight;
				velocityY = 0;
				onGround = true;
				break;
			}
		}
	}

	public void applyGravity() {
		velocityY += GRAVITY;		
	}
	
	public void updatePosition() {
		playerY += velocityY;
		
		int bottomY = panel.getHeight() - GamePanel.GROUND_HEIGHT - playerHeight;
		
		if (playerY >= bottomY) {
			playerY = bottomY;
			velocityY = 0;
			onGround = true;
			return;
		}
//		System.out.printf("%f | %f\n", playerX, playerY);
	}
	
	public float getX() {
		return playerX;
	}



	public float getY() {
		return playerY;
	}



	public void jump() {
		if (onGround) {
			velocityY = JUMP_STRENGTH;
		}
	}
	
	public void moveRight() {
		playerX += MOVE_SPEED;
		
	}
	
	public void moveLeft() {
		playerX -= MOVE_SPEED;
	}
}
