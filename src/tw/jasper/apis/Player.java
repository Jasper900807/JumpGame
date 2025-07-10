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
    private final float GRAVITY = 1.0f;
    private final float JUMP_STRENGTH = -17;
    private final int MOVE_SPEED = 7;
    private boolean onGround = false;
    private BufferedImage playerImg;
    private GamePanel panel;

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
    }

    public BufferedImage getImg() {
        return playerImg;
    }

    public void checkCollision(LinkedList<Platform> platforms) {
        for (Platform p : platforms) {
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

        int bottomY = panel.getHeight() - Config.GROUND_HEIGHT - playerHeight;

        if (playerY >= bottomY) {
            playerY = bottomY;
            velocityY = 0;
            onGround = true;
            return;
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
            velocityY = JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void moveRight() {
        playerX += MOVE_SPEED;
    }

    public void moveLeft() {
        playerX -= MOVE_SPEED;
    }
}
