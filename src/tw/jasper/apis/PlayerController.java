package tw.jasper.apis;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerController extends KeyAdapter {
    private Player player;
    private boolean leftPressed, rightPressed;
    private boolean jumpPressed, attackPressed;
    private boolean attackTriggered;
    private Animator attackRight, attackLeft;
    private StringProvider directionProvider;

    public interface StringProvider {
        String get();
    }

    public PlayerController(Player player, Animator attackRight, Animator attackLeft, StringProvider directionProvider) {
        this.player = player;
        this.attackRight = attackRight;
        this.attackLeft = attackLeft;
        this.directionProvider = directionProvider;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            if (!attackPressed) {
                attackTriggered = true;
            }
            attackPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jumpPressed = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_X) {
            attackPressed = false;
            attackTriggered = false;
        }
    }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isJumpPressed() { return jumpPressed; }
    public boolean isAttackPressed() { return attackPressed; }
    public boolean isAttackTriggered() { return attackTriggered; }
    public void resetAttackTriggered() { attackTriggered = false; }
}