import java.awt.Color;
import java.awt.Graphics;

public class MortalBullet extends Bullet{
	int width=5;
	int height=15;
	public MortalBullet(int x, int y, int v) {
		super(x,y,v,3);
	}
	
	void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}

}