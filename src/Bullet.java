import java.awt.Graphics;

public abstract class Bullet {
	int x,y;
	int v,damage;
	static int VEL_BULLET=11;
	public Bullet(int x, int y, int v, int damage) {
		this.x = x;
		this.y = y;
		this.v = v;
		this.damage = damage;
	}
	
	void move() {
		y+=v;}
	
	abstract void paint(Graphics g);

}
