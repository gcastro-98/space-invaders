import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Element{
	private int VEL_PLAYER=10;
	private int width=40,height=30;
	private int N_MAX_SHOOTS=2;
	private int N_MISSED_SHOOTS=0;
	static int N_MAX_MISSED_SHOOTS=4;
	public Player(int x, int y) {
		super(x,y,3);
		N_MISSED_SHOOTS=0;
	}
	
	void moveRight() {
		if(x<=Window.WIDTH-width)
			x+=VEL_PLAYER;
		else
			x=Window.WIDTH-width;
	}
	
	void moveLeft() {
		if(x>=0)
			x-=VEL_PLAYER;
		else
			x=0;
	}

	void shoot(ArrayList<Bullet> b) {
		if(b.size()<=N_MAX_SHOOTS) {
			b.add(new NormalBullet(this.x+width/2,this.y,-Bullet.VEL_BULLET));
			Game.reproduceSound("shoot", 0);
			}
	}
	
	void addMissedShoot() { // count how many shoots have been missed consecutively and, if it equals to 
							// maximum number, reset it and subtract 1 to the punctuation
		N_MISSED_SHOOTS+=1;
		if(N_MISSED_SHOOTS==N_MAX_MISSED_SHOOTS) {
			if(Game.PUNCTUATION>0)
				Game.PUNCTUATION-=1;
			resetMissedShoots();
		}
	}
	
	void resetMissedShoots() {
		this.N_MISSED_SHOOTS=0;
	}
	
	void paint(Graphics g) {
		try {
			Image image=ImageIO.read(new File("sprites/player_"+Integer.toString(getLife())+".png"));
			g.drawImage(image,x,y,width,height,null);
			} catch (IOException e) {}
	}
	
	int GetWidth() {
		return width;
	}
	int GetHeight() {
		return height;}
	
	void subtractLife(int damage) {
	setLife(getLife()-damage);
	if(getLife()<=0) {
		Game.OUTCOME=0;
		Game.PUNCTUATION=0;
		Game.GameOver(); // if life equals 0, then the game is lost
		}
	}
	
}
