import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends Element {
	private int width=64,height=16;
	public Wall(int x, int y) {
		super(x,y,4);
	}
	int GetWidth() {
		return width;
	}
	int GetHeight() {
		return height;
	}
	
	void paint(Graphics g) {
		try {
			Image image=ImageIO.read(new File("sprites/wall_" + Integer.toString(getLife())+ ".png"));
			g.drawImage(image,x,y,width,height,null);
			} catch (IOException e) {}
	}
	
}