// Main.java
package tw.jasper.game;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import tw.jasper.apis.GamePanel;

public class GameMain extends JFrame {

    public GameMain() {
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
        new GameMain();
    }
}
