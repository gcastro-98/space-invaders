import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

abstract public class Ship extends Element{
	int sprite_state = 1; // 'label' of the current sprint
	int sprite_change = 9; // 'period' which takes the sprite 
						// to change. It represents the number 
						// of times the ship has to move before changing the sprite
	int steps = 0; // number of steps performed from the sprite change
	int vertical_steps = 0; // number of vertical steps
	int points = 0; // punctuation the ship yields when is destroyed
	static int sense; // movement sense (of every ships' movement): 1 for right, -1 left
	static int MODULE_v; // velocity module (of every ship), which will grow the more time it passes
	static int PROBABILITY; // inverse of the shooting probability
	static int none_right = Window.WIDTH+333; // value returned by RightShip in case there is None to the right
	static int none_left = -33; // value returned by LeftShip in case there is None to the left
	
	public Ship(int x, int y, int vida, int points) {
		super(x,y,vida);
		this.points=points;
	}
	
	abstract int GetWidth();
	abstract int GetHeight();
	
	/** Function which find the x position of the very right ship of the nSelf (belonging to the same row)
	 * @param nSelf Ship respect to with the comparison is made
	 * @param n Enemies' ships array
	 * @return x coordinate of the very right ship compared to the nSelf. If there is None returned, then returns none_right. 
	 */
	int RightShip(Ship nSelf, ArrayList<Ship> n) {
		int counter=Ship.none_right;
		for(Ship n2:n) {
			if(nSelf.y==n2.y && n2.x>nSelf.x) {
				if((n2.x-nSelf.x)<(counter-nSelf.x))
					counter=n2.x;
			}
		}
		return counter;
	}
	
	/** Function which find the x position of the very left ship of the nSelf (belonging to the same row)
	 * @param nSelf Ship respect to with the comparison is made
	 * @param n Enemies' ships array
	 * @return x coordinate of the very left ship compared to the nSelf. If there is None returned, then returns none_left. 
	 */
	int LeftShip(Ship nSelf, ArrayList<Ship> n) {
		int counter=Ship.none_left;
		for(Ship n2:n) {
			if(nSelf.y==n2.y && n2.x<nSelf.x) {
				if((nSelf.x-n2.x)<(nSelf.x-counter))
					counter=n2.x;
			}
		}
		return counter;
	}
	
	void move(ArrayList<Ship> n) {
		verticalMovement();
		if(x>Window.WIDTH - 50) { // in case the ship got out the screen by the right part (50 has the highest WIDTH value as origin)
			x=Window.WIDTH - 50;
			Ship.sense=-1; // change of movement sense (of EVERY ship)
			x+=Ship.sense*Ship.MODULE_v;
		}
		else if(x<0) {
			x=0;
			Ship.sense=1; // movement sense's change
			x+=Ship.sense*Ship.MODULE_v;
		}
		else { // if nothing happens, then proceed normally...
			x+=Ship.sense*Ship.MODULE_v;
			if(RightShip(this,n)!=none_right && RightShip(this,n)-this.x<Game.minimum_sep)
				x=RightShip(this,n)-Game.minimum_sep;
			else if(LeftShip(this,n)!=none_left && this.x-LeftShip(this,n)<Game.minimum_sep)
				x=LeftShip(this,n)+Game.minimum_sep;
		}		
	}
	
	void verticalMovement() {
		steps+=1; // increase every ship counter (sprite change)
		if(vertical_steps<=185) { // the walls will be at HEIGHT 450, while the basic enemies at 230.
								 // since the HEIGHT of the enemies will be of 30, there is a maximum of 450-230-30-5=185 vertical space to move vertically.
			if(steps%8==0) {
				this.y-=-1;
				vertical_steps+=1;
			}
		}
	}
	
	// TODO: implement the bullets' child classes
	
	void shoot(ArrayList<Bullet> b) {
		int random_int=(int)(Math.random()*(PROBABILITY+1));
		if(random_int==0)
			b.add(new NormalBullet(this.x+GetWidth()/2,this.y+GetHeight(),+Bullet.VEL_BULLET));
	}
	
	void shootMortally(ArrayList<Bullet> b) {
		int random_int=(int)(Math.random()*(6*PROBABILITY+1));
		if(random_int==0)
			b.add(new MortalBullet(this.x+GetWidth()/2,this.y+GetHeight(),+Bullet.VEL_BULLET));
	}
	
	void paint(Graphics g) {
		String s=getClass().getName();
		int width=GetWidth();
		int height=GetHeight();
		try {
			Image image=ImageIO.read(new File("sprites/"+s+"1_" + Integer.toString(getLife())+".png"));
			Image image2=ImageIO.read(new File("sprites/"+s+"2_" + Integer.toString(getLife())+".png"));
			if(steps%sprite_change==0){
				sprite_state*=(-1);
				steps=0;
			}
			if(sprite_state==1) {
				g.drawImage(image,x,y,width,height,null);
			}
			if(sprite_state==-1) {
				g.drawImage(image2,x,y,width,height,null);
			}
		} catch (IOException e) {}
	}
	
}