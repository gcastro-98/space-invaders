import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

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
		// TODO: create & place the enemies' ships (store it in an array)
		// TODO: create the bullets' array
		// TODO: create & place the player
		// TODO: create & place the walls
		// TODO: reset some variables' values in case is the second time the videogame is played
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

	// TODO: finally, implement the following...
	void doMovements(){}
	void doShoots(){}
	void checkObjects(){}
	void paintScreen(){}
}
		
	