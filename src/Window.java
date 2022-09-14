import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Window extends Frame implements KeyListener{
	
	/**
	 * Main class of the project. Executing it implies starting the game. 
	 **/
	private static final long serialVersionUID = 1L;

	// we want to detect & process keyboard actions (for the player movement)
	private boolean LEFT_isPressed;
	private boolean RIGHT_isPressed;
	static boolean ENTER_isPressed;
	static boolean ESCAPE_isPressed;
	
	Game  j;
	static int WIDTH=800, HEIGHT=600;
	Graphics g;
	Image im;
	public static void main(String[] args) 	{ // main function: just initializing it, everything starts
		new Window();
	}
	Window() {
		super("Space Invaders - Gerard Castro");
		this.setLocation(WIDTH/2, HEIGHT/20);
		
		LEFT_isPressed=false;
		RIGHT_isPressed=false;
		ENTER_isPressed=false;
		ESCAPE_isPressed=false;
		
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		addKeyListener(this); // so that keyboard can be read
		
		im=createImage(WIDTH,HEIGHT);
		g=im.getGraphics();
		
		j=new Game(this);
		waitingBackground("Welcome!", WIDTH/3+100);
		while(true) {
			PLAY:{
			j.run(); // method to start the animation
			// the GameOver method should exit the loop
			if(Game.OUTCOME==0)
				waitingBackground("\n GAME OVER: you loose!\n"+"\n Your punctuation has been " + Integer.toString(Game.PUNCTUATION)+", while you played during " + Float.toString(Game.TIME)+" seconds.", 25);
			if(Game.OUTCOME==1)
				waitingBackground("\n GAME OVER: you won! Your punctuation has been " + Integer.toString(Game.PUNCTUATION)+", while you played during " + Float.toString(Game.TIME)+" seconds.", 25);
			break PLAY;}
		}
	}
	
	public void waitingBackground(String s, int X0) { // X0 initial horizontal position where the sentence appears
		ESCAPE_isPressed=false;
		try { // we load the waiting background
			g.drawImage(ImageIO.read(new File("sprites/background_waiting.jpg")),0,0,Window.WIDTH,Window.HEIGHT,null);
			} catch (IOException e) {}
		this.repaint();
		g.setColor(Color.WHITE);
		g.drawString(s,X0,40+HEIGHT/2);
		g.drawString("Press SPACE to start playing and ESCAPE to exit!",WIDTH/3-25,40+HEIGHT/2+15);
		this.repaint();
		while(!ENTER_isPressed) {
			if(ESCAPE_isPressed)
				System.exit(0);
			try { Thread.sleep(Game.INTERVAL);} catch (InterruptedException e) {}
		}
	}
	
	public void paint(Graphics g) { // function for the game to be used to paint the frame (after the UPDATE)
		g.drawImage(im,0,0,null);
	}
	
	public void paintBackground() {
		try {
			Image background=ImageIO.read(new File("sprites/background.png"));
			g.drawImage(background,0,0,Window.WIDTH,Window.HEIGHT,null);
			} catch (IOException e) {}
	}
	
	public void paintData() {
		Game.TIME+=(float)(Game.INTERVAL)/1000.;
		g.setColor(Color.WHITE);
		g.drawString("Punctuation: "+(Integer.toString(Game.PUNCTUATION)),3,50);
		g.drawString("Time: "+(Float.toString(Game.TIME)),WIDTH-78,50);
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public boolean isLeftPressed() {
		return LEFT_isPressed;
	}
	
	public boolean isRightPressed() {
		return RIGHT_isPressed;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) // right arrow
			RIGHT_isPressed=true;
		else if (e.getKeyCode()==KeyEvent.VK_LEFT) // left arrow
			LEFT_isPressed=true;
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) // right arrow
			RIGHT_isPressed=false;
		else if (e.getKeyCode()==KeyEvent.VK_LEFT) // left arrow
			LEFT_isPressed=false;
		else if (e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_SPACE) // up arrow or SPACE
			j.player.shoot(j.b_player);
		else if (e.getKeyCode()==KeyEvent.VK_ENTER)
			ENTER_isPressed=true;
		else if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
			ESCAPE_isPressed=true;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}