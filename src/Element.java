
public abstract class Element {
	int x,y;
	private int life;
	public Element(int x, int y, int vida) {
		this.x=x;
		this.y=y;
		this.life=vida;
	}
	
	abstract int GetWidth();
	abstract int GetHeight();
	int getLife() {
		return life;
	}
	void setLife(int n) {
		life=n;
	}
	
	boolean checkElementBullet(Bullet b2) {
		return (b2.y-this.y>=0 && b2.y-this.y<=this.GetHeight() && b2.x-this.x>=0 && b2.x-this.x<=this.GetWidth());
	}

}
