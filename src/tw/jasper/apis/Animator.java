// Animator.java
package tw.jasper.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Animator {
    private static final Map<String, BufferedImage[]> animations = new HashMap<>();
    private static final Map<String, Integer> actionSpeeds = new HashMap<>();
    private BufferedImage[] frames;
    private int frameIndex = 0;
    private int animationCounter = 0;
    private int animationSpeed;
    private int frameCount;
    private boolean hasPlayedOnce = false; // 新增變數紀錄動畫是否播放過一次

    static {
        preloadAnimations();
    }

    public Animator(String action) {
        if (!animations.containsKey(action)) {
            throw new IllegalArgumentException("動作未找到: " + action);
        }
        this.frames = animations.get(action);
        this.frameCount = frames.length;
        this.animationSpeed = actionSpeeds.getOrDefault(action, 10); // 預設速度
    }

    public BufferedImage updateAnimation() {
        animationCounter++;
        if (animationCounter >= animationSpeed) {
            frameIndex = (frameIndex + 1) % frameCount;
            animationCounter = 0;
            if (frameIndex == frameCount - 1) {
                hasPlayedOnce = true; // 當動畫播放到最後一幀時，將變數設為 true
            }
        }
        return frames[frameIndex];
    }

    public void reset() {
        frameIndex = 0;
        animationCounter = 0;
        hasPlayedOnce = false; // 重置時將變數設回 false
    }

    public boolean isAnimationFinished() {
        return hasPlayedOnce && frameIndex == frameCount - 1 && animationCounter == 0;
    }

    private static void preloadAnimations() {
        try {
            // Preload "run_right" animation
            int runRightFrames = 8;
            BufferedImage[] runRight = new BufferedImage[runRightFrames];
            for (int i = 0; i < runRightFrames; i++) {
                runRight[i] = ImageIO.read(new File(Config.IMAGE_PATH + "run_right/run_right_" + i + ".png"));
            }
            animations.put("run_right", runRight);
            actionSpeeds.put("run_right", 6); // Set speed for "run_right"

            // Preload "run_left" animation
            int runLeftFrames = 8;
            BufferedImage[] runLeft = new BufferedImage[runLeftFrames];
            for (int i = 0; i < runLeftFrames; i++) {
                runLeft[i] = ImageIO.read(new File(Config.IMAGE_PATH + "run_left/run_left_" + i + ".png"));
            }
            animations.put("run_left", runLeft);
            actionSpeeds.put("run_left", 6); // Set speed for "run_left"

            // Preload "idle_right" animation
            int idleRightFrames = 8;
            BufferedImage[] idleRight = new BufferedImage[idleRightFrames];
            for (int i = 0; i < idleRightFrames; i++) {
                idleRight[i] = ImageIO.read(new File(Config.IMAGE_PATH + "idle_right/idle_right_" + i + ".png"));
            }
            animations.put("idle_right", idleRight);
            actionSpeeds.put("idle_right", 10); // Set speed for "idle_right"

            // Preload "idle_left" animation
            int idleLeftFrames = 8;
            BufferedImage[] idleLeft = new BufferedImage[idleLeftFrames];
            for (int i = 0; i < idleLeftFrames; i++) {
                idleLeft[i] = ImageIO.read(new File(Config.IMAGE_PATH + "idle_left/idle_left_" + i + ".png"));
            }
            animations.put("idle_left", idleLeft);
            actionSpeeds.put("idle_left", 10); // Set speed for "idle_left"
            
            // attack_right
            int attackRightFrames = 8;
            BufferedImage[] attackRight = new BufferedImage[attackRightFrames];
            for (int i = 0; i < attackRightFrames; i++) {
                attackRight[i] = ImageIO.read(new File(Config.IMAGE_PATH + "attack_right/attack_right_" + i + ".png"));
            }
            animations.put("attack_right", attackRight);
            actionSpeeds.put("attack_right", 4);

            // attack_left
            int attackLeftFrames = 8;
            BufferedImage[] attackLeft = new BufferedImage[attackLeftFrames];
            for (int i = 0; i < attackLeftFrames; i++) {
                attackLeft[i] = ImageIO.read(new File(Config.IMAGE_PATH + "attack_left/attack_left_" + i + ".png"));
            }
            animations.put("attack_left", attackLeft);
            actionSpeeds.put("attack_left", 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}