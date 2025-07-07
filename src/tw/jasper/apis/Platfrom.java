package tw.jasper.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platfrom {
	private float platX, platY;
	private int platWidth, platHeight;
	private BufferedImage platImg;
	private float platTop, platBottom, platLeft, platRight;
	


	public Platfrom(float x, float y) {
		platX = platLeft = x;
		platY = platTop = y;
		try {
			platImg = ImageIO.read(new File("image/platform.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		platWidth = platImg.getWidth();
		platHeight = platImg.getHeight();
		platBottom = y + platHeight;
		platRight = x + platWidth;
		System.out.println("create platform");
	}
	
	
	public float getBottom() {
		return platBottom;
	}


	public float getTop() {
		return platTop;
	}


	public float getLeft() {
		return platLeft;
	}


	public float getRight() {
		return platRight;
	}


	public float getX() {
		return platX;
	}


	public float getY() {
		return platY;
	}


	public int getWidth() {
		return platWidth;
	}


	public int getHeight() {
		return platHeight;
	}


	public BufferedImage getImg() {
		return platImg;
	}
}
