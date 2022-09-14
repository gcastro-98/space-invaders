import java.awt.Color;
import java.awt.Graphics;

public class NormalBullet extends Bullet{
	int width=3;
	int height=15;
	public NormalBullet(int x, int y, int v) {
		super(x,y,v,1);
	}
	
	void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}

}