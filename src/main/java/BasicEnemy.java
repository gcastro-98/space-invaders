public class BasicEnemy extends Ship{
	int width=50;
	int height=30;
	public BasicEnemy(int x, int y) {
		super(x,y,1,1); // it has just 1 points of life
	}
	
	int GetWidth() {
		return width;
	}
	int GetHeight() {
		return height;
	}
	

}
