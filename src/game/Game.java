package game;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import input.KeyManager;
import input.MouseManager;
import Display.Display;
import Graphics.Assets;

import Sound.Sound;

import State.GameState; 
import State.MainMenu;

import State.State;


public class Game implements Runnable {
	
	//
	private Display display;
	public int width, height;
	public String title;
	private BufferStrategy bs;
	private Graphics g;
	
	//
	private boolean running = false;
	private Thread thread;

	
	//Score and Lives
    private int score;
    private int live;
    private boolean frighten; 
    
   
	//State

	private State gameState;
	private State menuState;
	
	
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	
	//Handler
	private HandleClass handler;
	
	//Sound
	private Sound sound;
	
	

	
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title; 
		keyManager = new KeyManager();
		mouseManager=new MouseManager();
		//score=0;
		//live=3;
		//
		
	}
	public void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
       
		Assets.init();
		handler = new HandleClass(this);
		
		//setGameState(new GameState(handler));

		gameState= new GameState(handler);
		
		menuState= new MainMenu(handler);


		State.setState(menuState);

	
//		score , lives and frighten
		
		score=0;
		live=3;
		frighten= false; 
		
		
		if(State.getState()== menuState) {
		sound=new Sound("/Sound/pacman_beginning.wav");
		sound.play();
		}
		if(State.getState()== gameState) {
			sound=new Sound("/Sound/pacman_chomp.wav");
			sound.play();	
		}
		

		
	}
	
	
	public void render() {
		bs= display.getCanvas().getBufferStrategy();
		if(bs==null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g=bs.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		
		if(State.getState()!=null) {
			State.getState().render(g);
		}
		
	
		
		bs.show();
		g.dispose();
		
	}
	@Override
	public void run() {
		init();

		
		int fps = 60; 
		double timePerTick= 1000000000/ fps; 
		double delta=0; 
		long now; 
		long lastTime= System.nanoTime(); 
		long timer =0; 
		int ticks=0;
		
		while(running) {
			tick();
			render();

			now= System.nanoTime();
			delta+= (now- lastTime)/ timePerTick;
			timer+= now - lastTime;
			lastTime= now;
			if(delta>=1){
				tick();
				render();
				ticks++; 
				delta--; 
			}
			if (timer> 1000000000) {
				System.out.println("Ticks and Frame : " + ticks);
				ticks=0; 
				timer=0; 
			}
		}
		stop();
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	///Getters Setters for SCORE, LIVES and FRIGHTEN
	 public int getScore() {
	    return score;
	 }
	 public void setScore(int score) {
	    this.score = this.score + score;
	 }
	 public void setLives(int live) {
		 this.live = this.live - live;
	 }
	 public int getLives() {
		 return live;
	 }
	 public boolean getFrighten() {
			return frighten;
		}
		public void setFrighten(boolean frighten) {
			this.frighten = frighten;
		}
		private void tick() {
			keyManager.tick();
			if(State.getState()!=null) {
				State.getState().tick();
			}
			
		}
	    
	
	public Canvas getCanvas(){
        return display.getCanvas();
    }
	public KeyManager getKeyManager() {
		return keyManager;
	}
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	public Graphics getG() {
		// TODO Auto-generated method stub
		return g;
	}
	public State getMenuState() {
		// TODO Auto-generated method stub
		return this.menuState;
	}
	
	public State getGameState() {
		return gameState;
	}

	
	
	public void reinit() {
		
		//handler = new HandleClass(this);
//		
//		//setGameState(new GameState(handler));
//
		gameState= new GameState(handler);
//		
		menuState= new MainMenu(handler);
		
//
		State.setState(menuState);
//
//	
////		score , lives and frighten
//		
		score=0;
		live=3;
		frighten= false; 
//		
//		
		if(State.getState()== menuState) {
		sound=new Sound("/Sound/pacman_beginning.wav");
		sound.play();
		}

	}
}
