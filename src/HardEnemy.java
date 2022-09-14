public class HardEnemy extends Ship{
	int width=30;
	int height=30;
	public HardEnemy(int x, int y) {
		super(x,y,3,3); // it has 3 life points
	}
	
	int GetWidth() {
		return width;
	}
	
	int GetHeight() {
		return height;
	}
	

}
