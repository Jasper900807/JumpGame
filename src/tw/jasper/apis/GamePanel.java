package tw.jasper.apis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private final int FPS = 60;
	private int windowW, windowH;
	final static int GROUND_HEIGHT = 120;
	private BufferedImage playerImg;
	private boolean leftPressed, rightPressed;
	
	private Player player;
	private LinkedList<Platfrom> platforms;
	private Animator runRight;
	
	
	
	public GamePanel() {
		setBackground(Color.LIGHT_GRAY);
		
		setFocusable(true);
		requestFocusInWindow(); 
		
		player = new Player(500, 450, this);
		playerImg = player.getImg();
		platforms = new LinkedList<>();
		platforms.add(new Platfrom(500, 480));
		platforms.add(new Platfrom(300, 430));
		
		runRight = new Animator("run_right");
		
		
		System.out.println("test panel");
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				player.applyGravity();
				player.checkCollision(platforms);
				player.updatePosition();
				repaint();
			}
			
		}, 0, 1000/FPS);
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (rightPressed) {
					player.moveRight();
					timer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							playerImg = runRight.updateAnimation();
							
						}
					}, 0, 300);
				}
				if (leftPressed) {
					player.moveLeft();
				}
			}
		}, 100, 50);
		
		// 鍵盤事件
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					player.jump();
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					leftPressed = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					rightPressed = true;
				}
				
			}
			
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		            leftPressed = false;
		        }
		        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		            rightPressed = false;
		        }
			}
		});
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		windowW = getWidth();
		windowH = getHeight();
		
		
		g.setColor(new Color(139, 69, 19));
		g.fillRect(0, windowH - GROUND_HEIGHT, windowW, GROUND_HEIGHT);
		
		g.setColor(Color.BLACK);
		g.drawLine(0, windowH - GROUND_HEIGHT, windowW, windowH - GROUND_HEIGHT);
		
		for (Platfrom p : platforms) {
			g.drawImage(p.getImg(), (int)p.getX(), (int)p.getY(), null);
		}
		
		g.drawImage(playerImg, (int)player.getX(), (int)player.getY(), null);
		

		
	}
	
	
	
	
}
