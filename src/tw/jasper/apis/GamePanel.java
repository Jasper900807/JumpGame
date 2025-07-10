// GamePanel.java (整合動畫更新邏輯)
package tw.jasper.apis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private int windowW, windowH;
    private BufferedImage playerImg;
    private Player player;
    private LinkedList<Platform> platforms;
    private Animator runRight, runLeft, idleRight, idleLeft;
    private PlayerController controller;
    private String currentDirection = "stand"; // 初始方向為靜止

    public GamePanel() {
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        requestFocusInWindow();

        player = new Player(500, 450, this);
        platforms = new LinkedList<>();
        platforms.add(new Platform(500, 480));
        platforms.add(new Platform(300, 430));

        runRight = new Animator("run_right");
        runLeft = new Animator("run_left");
        idleRight = new Animator("idle_right");
        idleLeft = new Animator("idle_left");

        // Set initial state to idleRight
        playerImg = idleRight.updateAnimation();
        currentDirection = "right";

        controller = new PlayerController(player);
        addKeyListener(controller);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.applyGravity();
                player.checkCollision(platforms);
                player.updatePosition();

                if (controller.isRightPressed() && !controller.isLeftPressed()) {
                    if (!currentDirection.equals("right")) {
                        currentDirection = "right";
                    }
                    player.moveRight();
                    playerImg = runRight.updateAnimation();
                } 
                else if (controller.isLeftPressed() && !controller.isRightPressed()) {
                    if (!currentDirection.equals("left")) {
                        currentDirection = "left";
                    }
                    player.moveLeft();
                    playerImg = runLeft.updateAnimation();
                } 
                else {
                    if (currentDirection.equals("right")) {
                        playerImg = idleRight.updateAnimation();
                    } else if (currentDirection.equals("left")) {
                        playerImg = idleLeft.updateAnimation();
                    } else {
                        playerImg = player.getImg(); // Default standing image
                    }
                }

                repaint();
            }
        }, 0, 1000 / Config.FPS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        windowW = getWidth();
        windowH = getHeight();

        g.setColor(new Color(139, 69, 19));
        g.fillRect(0, windowH - Config.GROUND_HEIGHT, windowW, Config.GROUND_HEIGHT);

        g.setColor(Color.BLACK);
        g.drawLine(0, windowH - Config.GROUND_HEIGHT, windowW, windowH - Config.GROUND_HEIGHT);

        for (Platform p : platforms) {
            g.drawImage(p.getImg(), (int) p.getX(), (int) p.getY(), null);
        }

        g.drawImage(playerImg, (int) player.getX(), (int) player.getY(), null);
    }
}
