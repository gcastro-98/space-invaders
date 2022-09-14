import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {
	static float TIME=0;
	static int PUNCTUATION=0;
	static int OUTCOME=-1; //0 will mean the player has lost, and 1 that the player has won
	static int INTERVAL=30; // time interval (in ms) that passes between frames
	static int N_ENEMIES=9; // enemies per row
	static int N_length=N_ENEMIES*3; // enemies in total
	static int minimum_sep=Window.WIDTH/((N_ENEMIES)+1); // minimum separation between ships
	static Clip background_music;
	int N_MAX_BULLETS=100; // maximum capacity for the bullets to be at screen
	int N_WALLS=4;
	Window f;
	ArrayList<Ship> n; // list of enemies' ships
	ArrayList<Bullet> b; // list of enemies' ships bullets
	ArrayList<Bullet> b_player; // list (of 1 element at most) of player's bullets
	ArrayList<Wall> w; // list of contention walls
	Player player; // player
	
	Game(Window f) {
		this.f=f;		
	}
	
	void run() {
		initialization();
		while(Window.ENTER_isPressed) {
			doMovements();
			doShoots();
			checkObjects(); // check: whether a bullet is out of the screen, any ship has to be suppressed or the player has won
			paintScreen();
			try { Thread.sleep(INTERVAL);} catch (InterruptedException e) {}
		}
	}
	
	void initialization() {
		// background music starts
		backgroundMusic();		
		// the enemies' ships are created
		n = new ArrayList<Ship>(N_length);
		enemyShipsPositioning();
		// the bullets' list is created
		b = new ArrayList<Bullet>(N_MAX_BULLETS);
		b_player = new ArrayList<Bullet>(1);
		//es crea el jugador
		player = new Player(Window.WIDTH/2, Window.HEIGHT - 30);
		//es creen els murs de contenció
		w = new ArrayList<Wall>(N_WALLS);
		wallsPositioning();
		// also, some variables' values are reset (in case the video game is played by second time)
		Ship.sense=1; // movement sense changed
		Ship.MODULE_v=3; // velocity module set
		Ship.PROBABILITY=600;
	}
	
	void backgroundMusic() {
		try {
			background_music=AudioSystem.getClip();
			background_music.open(AudioSystem.getAudioInputStream(new File("sound/music.wav")));
			background_music.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {}
	}

	static void GameOver() {
		// first we stop the music
		background_music.stop();
		background_music.close();
		reproduceSound("game_over", 0); // Game Over sound
		Window.ENTER_isPressed=false; // set to false again, condition from which the run loop stops
	}
	
	static void reproduceSound(String s, int l) { // l represents the number of times the sound must be reproduced
		try {
			Clip so = AudioSystem.getClip();
			so.open(AudioSystem.getAudioInputStream(new File("sound/"+s+".wav")));
			so.loop(l);
			so.addLineListener(new LineListener() { // when the sound has stopped, shut down the clip
				public void update(LineEvent myLineEvent) {
				  if (myLineEvent.getType() == LineEvent.Type.STOP)
					so.close();
				}
			  });	
		} catch (Exception e) {}
	}

	void enemyShipsPositioning() {
		for(int i=0;i<N_length;i++) {
			if(i<N_ENEMIES)
				n.add(new HardEnemy(20+(i%(N_ENEMIES))*minimum_sep,70));
			if(i>=N_ENEMIES && i<2*N_ENEMIES)
				n.add(new MediumEnemy(15+(i%(N_ENEMIES))*minimum_sep,150));
			if(i>=2*N_ENEMIES)
				n.add(new BasicEnemy(10+(i%(N_ENEMIES))*minimum_sep,230));
		}
	}
	
	void wallsPositioning() {
		for(int i=0;i<N_WALLS;i++) {
			w.add(new Wall((Window.WIDTH/N_WALLS)*i+(Window.WIDTH/N_WALLS)/2,Window.HEIGHT-150));
		}
	}
	
	void doMovements() {
		// enemies' ships
		for(Ship n2:n) 
			n2.move(n);
		// bullets of enemies' ships
		for(Bullet b2:b)
			b2.move();
		// player's bullets
		for(Bullet b2:b_player)
			b2.move();
		// the player (implemented like this, otherwise the movement is not optimal)
		if(f.isLeftPressed())
			player.moveLeft();
		if(f.isRightPressed())
			player.moveRight();
	}
	
	void doShoots() {
		for(Ship n2:n) {
			n2.shoot(b);
			n2.shootMortally(b);
		}
	}
	
	void checkObjects() {
		checkBullets();
		detectCollisions();
		if(n.isEmpty()) {
			Game.OUTCOME=1;
			Game.GameOver();
		}
	}
	
	void checkBullets() {
		// check whether a bullet must be deleted from the enemies' array (w/ iterator, otherwise it does not work properly)
		Iterator<Bullet> iter = b.iterator();
		while(iter.hasNext()){
			Bullet b2=iter.next();
			if(b2.y<=0 || b2.y>=Window.HEIGHT)
				iter.remove();
			}
		
		// them for player's bullets...
		Iterator<Bullet> iter2 = b_player.iterator();
		while(iter2.hasNext()){
			Bullet b2=iter2.next();
			if(b2.y<=0 || b2.y>=Window.HEIGHT) {
				iter2.remove();
				player.addMissedShoot();
			}
		}
	}
	
	void detectCollisions() {
		WallsCollisions();
		ShipsCollisions();
		PlayerCollisions();
	}

	void paintScreen() {
		f.paintBackground();
		paintObjects();
		f.paintData();
		f.repaint();
	}
	
	void paintObjects() {
		// enemies' ships
		for(Ship n2:n) 
			n2.paint(f.g);
		// bullets
		for(Bullet b2:b)
			b2.paint(f.g);
		for(Bullet b2:b_player)
			b2.paint(f.g);
		// player
		player.paint(f.g);
		// contention walls
		for(Wall w2:w)
			w2.paint(f.g);
	}
	
	void ShipsCollisions(){
		Iterator<Ship> iterNau = n.iterator();
		while(iterNau.hasNext()){
			Ship n2=iterNau.next();
			Iterator<Bullet> iter = b_player.iterator();
			while(iter.hasNext()){ 
				Bullet b2=iter.next();
				if(n2.checkElementBullet(b2)) {
					n2.setLife(n2.getLife()-1); 
					iter.remove(); 
					player.resetMissedShoots(); // reset the missed shoots counter
					if(n2.getLife()==0) {
						iterNau.remove(); // if life reaches to 0, then the ship is removed
						PUNCTUATION+=n2.points;
						Game.reproduceSound("enemy_death", 0);
						increaseDifficulty(); // for each enemies' ship that dies, the shooting probability & velocity increases
					}
				}
			}
		}
	}
	
	void PlayerCollisions() {
		Player n2=player;
		Iterator<Bullet> iter = b.iterator();
		while(iter.hasNext()){
			Bullet b2=iter.next();
			if(n2.checkElementBullet(b2)) {
				iter.remove();
				player.subtractLife(b2.damage);
			}
		}
	}
	
	void WallsCollisions(){
		// check whether the walls' collisions are due to enemies' bullets or player's
		Iterator<Wall> iterWall = w.iterator();
		while(iterWall.hasNext()){
			Wall w2=iterWall.next();
			
			// first loop (player's bullet)
			Iterator<Bullet> iter = b_player.iterator();
			while(iter.hasNext()){ 
				Bullet b2=iter.next();
				if(w2.checkElementBullet(b2)) {
					w2.setLife(w2.getLife()-1); 
					iter.remove();
					if(w2.getLife()==0) {
						iterWall.remove();
						Game.reproduceSound("wall_death",0);
						}
				}
			}
			// second loop (enemies' bullets)
			Iterator<Bullet> iter2 = b.iterator();
			while(iter2.hasNext()){
				Bullet b2=iter2.next();
				if(w2.checkElementBullet(b2)) {
					w2.setLife(w2.getLife()-b2.damage);
					iter2.remove(); 
					if(w2.getLife()<=0) {
						iterWall.remove(); 
						Game.reproduceSound("wall_death",0);
						}
				}
			}
		}	
	}
	
	void increaseDifficulty() {
		Ship.PROBABILITY=Ship.PROBABILITY-15;
		if(n.size() % (2*Game.N_ENEMIES/3)==0)
			Ship.MODULE_v=Ship.MODULE_v+2;
	}
}
		
	