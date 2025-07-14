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
    private Animator runRight, runLeft, idleRight, idleLeft, attackRight, attackLeft;
    private PlayerController controller;
    private String currentDirection = "stand"; // 初始方向為靜止
    private boolean lastAttackPressed = false;
    private String attackDirection = null; // 新增：攻擊時鎖定方向
    private boolean initializedPlayerPosition = false; // 新增：角色位置初始化旗標

    public GamePanel() {
        setBackground(Color.LIGHT_GRAY);
        setFocusable(true);
        requestFocusInWindow();

        player = new Player(500, 450, this);
        platforms = new LinkedList<>();
        platforms.add(new Platform(500, 480));
        platforms.add(new Platform(300, 400));
        platforms.add(new Platform(700, 350));
        

        runRight = new Animator("run_right");
        runLeft = new Animator("run_left");
        idleRight = new Animator("idle_right");
        idleLeft = new Animator("idle_left");
        attackRight = new Animator("attack_right");
        attackLeft = new Animator("attack_left");

        // Set initial state to idleRight
        playerImg = idleRight.updateAnimation();
        currentDirection = "right";

        controller = new PlayerController(
        	    player,
        	    attackRight,
        	    attackLeft,
        	    () -> currentDirection
        	);
        addKeyListener(controller);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.applyGravity();
                player.checkCollision(platforms);
                player.updatePosition();
                player.updateAttackState();

                // 先處理移動
                if (controller.isRightPressed() && !controller.isLeftPressed()) {
                    if (!player.isAttacking() && !currentDirection.equals("right")) {
                        currentDirection = "right";
                    }
                    player.moveRight();
                } else if (controller.isLeftPressed() && !controller.isRightPressed()) {
                    if (!player.isAttacking() && !currentDirection.equals("left")) {
                        currentDirection = "left";
                    }
                    player.moveLeft();
                }

                // 處理動畫顯示
                if (player.isAttacking()) {
                    // 攻擊期間只根據 attackDirection 顯示動畫
                    Animator attackAnimator = "right".equals(attackDirection) ? attackRight : attackLeft;
                    playerImg = attackAnimator.updateAnimation();
                    // 檢查動畫是否播放完畢，播放完畢後結束攻擊狀態
                    if (attackAnimator.isAnimationFinished()) {
                        playerImg = ("right".equals(attackDirection) ? idleRight : idleLeft).updateAnimation();
                        player.updateAttackState(); // 這裡可直接設 isAttacking = false
                        // 直接在 Player 內加一個 setAttacking(false) 會更乾淨
                        // 但目前 updateAttackState 只做空操作，這裡直接設
                        java.lang.reflect.Field f = null;
                        try {
                            f = player.getClass().getDeclaredField("isAttacking");
                            f.setAccessible(true);
                            f.set(player, false);
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    // 修正：攻擊時也能跳躍
                    if (controller.isJumpPressed()) {
                        player.jump();
                    }
                    repaint();
                    lastAttackPressed = controller.isAttackPressed();
                    return;
                } else {
                    // 攻擊結束，清空攻擊方向
                    attackDirection = null;
                }

                // 非攻擊時才切換 run/idle 動畫
                if (controller.isRightPressed() && !controller.isLeftPressed()) {
                    playerImg = runRight.updateAnimation();
                } 
                else if (controller.isLeftPressed() && !controller.isRightPressed()) {
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

                // 處理跳躍
                if (controller.isJumpPressed()) {
                    player.jump();
                }

                // 僅在X剛按下那一幀觸發攻擊
                if (controller.isAttackPressed() && !lastAttackPressed && player.canAttack()) {
                    // 攻擊時鎖定方向
                    attackDirection = currentDirection.equals("left") ? "left" : "right";
                    // 重置對應攻擊動畫
                    if ("left".equals(attackDirection)) {
                        attackLeft.reset();
                    } else {
                        attackRight.reset();
                    }
                    player.attack();
                }
                lastAttackPressed = controller.isAttackPressed();

                repaint();
            }
        }, 0, 1000 / Config.FPS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        windowW = getWidth();
        windowH = getHeight();

        // 初始化角色位置：讓角色站在地面正中央
        if (!initializedPlayerPosition) {
            int playerWidth = player.getImg().getWidth();
            int playerHeight = player.getImg().getHeight();
            int x = (windowW - playerWidth) / 2;
            int y = windowH - Config.GROUND_HEIGHT - playerHeight;
            player.setPosition(x, y);
            initializedPlayerPosition = true;
        }

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