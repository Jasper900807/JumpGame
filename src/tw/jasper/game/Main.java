package tw.jasper.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tw.jasper.apis.GamePanel;

public class Main extends JFrame {
	private int FPS = 120;

	
	public Main() {
		// 建立視窗
		super("Jump Game");
		setSize(1080, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setLayout(new BorderLayout());
		
		GamePanel mainPanel = new GamePanel();
		add(mainPanel);
		
		setVisible(true);
		
	}
	
	
	
	public static void main(String[] args) {
		new Main();
	}

}
