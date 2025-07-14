// Player.java
package tw.jasper.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Player {
    private float playerX, playerY;
    private int playerWidth, playerHeight;
    private float velocityY = 0;
    private boolean onGround = false;
    private BufferedImage playerImg;
    private GamePanel panel;

    private boolean isAttacking = false;
    private long lastAttackEndTime = 0;
    private static final long ATTACK_COOLDOWN = Config.ATTACK_COOLDOWN; // 0.1秒冷卻

    public Player(float x, float y, GamePanel panel) {
        this.panel = panel;
        playerX = x;
        playerY = y;
        try {
            playerImg = ImageIO.read(new File(Config.IMAGE_PATH + "idle_right/idle_right_0.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerWidth = playerImg.getWidth();
        playerHeight = playerImg.getHeight();
        System.out.println("create player");
    }

    public BufferedImage getImg() {
        return playerImg;
    }

    public void checkCollision(LinkedList<Platform> platforms) {
    	boolean landed = false;
        for (Platform p : platforms) {
        	float playerCenter = playerX + playerWidth / 2.0f;
            boolean isFalling = velocityY > 0;
            boolean isAbove = playerY + playerHeight <= p.getTop();
            boolean willLand = playerY + playerHeight + velocityY >= p.getTop();
            float tolerance = Config.PLATFORM_TOLERANCE; // 使用Config
            boolean inHorizontalRange = playerCenter + tolerance >= p.getLeft() && playerCenter - tolerance <= p.getRight();

            if (isFalling && isAbove && willLand && inHorizontalRange) {
                playerY = p.getTop() - playerHeight;
                velocityY = 0;
                landed = true;
                break;
            }
        }
        onGround = landed;
    }

    public void applyGravity() {
        velocityY += Config.GRAVITY;
    }

    public void updatePosition() {
        playerY += velocityY;

        int bottomY = panel.getHeight() - Config.GROUND_HEIGHT - playerHeight;

        if (playerY >= bottomY) {
            playerY = bottomY;
            velocityY = 0;
            onGround = true;
            return;
        }
    }
    
    public void setPosition(float x, float y) {
		this.playerX = x;
		this.playerY = y;
	}

    public void updateAttackState() {
        // 只負責狀態，不再檢查動畫
        if (isAttacking) {
            // 由 GamePanel 控制動畫結束時機
        }
    }

    public float getX() {
        return playerX;
    }

    public float getY() {
        return playerY;
    }

    public void jump() {
        if (onGround) {
            velocityY = Config.JUMP_STRENGTH;
            onGround = false;
        }
    }
    
    public void attack() {
        isAttacking = true;
        // 動畫重置交由 GamePanel 控制
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean canAttack() {
        return !isAttacking && (System.currentTimeMillis() - lastAttackEndTime >= ATTACK_COOLDOWN);
    }

    public void moveRight() {
        playerX += Config.MOVE_SPEED;
    }

    public void moveLeft() {
        playerX -= Config.MOVE_SPEED;
    }
}