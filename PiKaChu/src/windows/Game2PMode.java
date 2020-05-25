package windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import control.KeyManager;

public class Game2PMode implements Runnable{
	
	private Display window;
	public int width, height;
	private String title;
	
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private boolean running = false;
	
	private Pikachu1P player1;
	private Pikachu2P player2;
	private Ball ball;
	
	private Rectangle net, landing;
	
	private KeyManager keyManager;
	
	public Game2PMode(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		player1 = new Pikachu1P(this, 0, 300, 80, 100);
		player2 = new Pikachu2P(this, 950, 300, 80, 100);
		ball = new Ball(this, 0, 0, 25, 25);
		net = new Rectangle(475, 400 - 150, 50, 150);
		landing = new Rectangle(0, 400, width, 10);
		keyManager = new KeyManager();
	}

	public void init() {
		window = new Display(title, width, height);
		window.getWindow().addKeyListener(keyManager);
	}
	
	public void update() {
		keyManager.update();
		player1.update();
		player2.update();
		ball.update();
	}
	
	public void paint() {
		bs = window.getCanvas().getBufferStrategy();
		if(bs == null) {
			window.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clean screen
		g.clearRect(0, 0, width, height);
		//draw
		player1.paint(g);
		player2.paint(g);
		ball.paint(g);
		g.setColor(Color.black);
		g.fillRect(landing.x, landing.y, landing.width, landing.height);
		g.fillRect(net.x, net.y, net.width, net.height);
		//end
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		//process that let the move speed is same at different cpu.
		int fps = 45;
		double timePerUpdate = 1000000000 / fps;
		double delta = 0;
		long now = 0;
		long lastTime = System.nanoTime();
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerUpdate;
			lastTime = now;
			
			if(delta >= 1) {
				update();
				paint();
				delta--;
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public KeyManager getKeyManger() {
		return keyManager;
	}
	
	public Rectangle getNet() {
		return net;
	}
	
	public Rectangle getLanding() {
		return landing;
	}
	
	public Pikachu1P getPlayer1P() {
		return player1;
	}
	
	public Pikachu2P getPlayer2P() {
		return player2;
	}

}
