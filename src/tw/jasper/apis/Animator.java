package tw.jasper.apis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Animator {
	private BufferedImage[] frames;
	private int frameIndex = 0;
	private final int ANIMATION_SPEED = 10; // 每幾幀切一次
	private int frameCount;
	private Map<String, BufferedImage[]> animations = new HashMap<>();
	private final Map<String, Integer> ACTION_FRAME = new HashMap<>();
	
	
	
	public Animator(String action) {
		addActionFrame();

		frameCount = ACTION_FRAME.get(action);
		frames = new BufferedImage[frameCount];
		System.out.println(action);
		for (int i=0; i<frameCount; i++) {
			try {
				System.out.println("LOAD...image/"+ action + "/" + action + "_" + (i) + ".png");
				frames[i] = ImageIO.read(new File("image/"+ action + "/" + action + "_" + i + ".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public BufferedImage updateAnimation() {
		
		frameIndex = (frameIndex + 1) % frameCount;
		return frames[frameIndex];
	}
	
	public void addActionFrame() {
		ACTION_FRAME.put("run_right", 8);
	}
}


