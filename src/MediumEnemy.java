public class MediumEnemy extends Ship{
	int width=40;
	int height=30;
	public MediumEnemy(int x, int y) {
		super(x,y,2,2); // it has 2 life points
	}
	
	int GetWidth() {
		return width;
	}
	
	int GetHeight() {
		return height;
	}

}