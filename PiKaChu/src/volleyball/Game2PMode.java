package volleyball;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import control.KeyManager;



public class Game2PMode extends Game{
	private Pikachu1P player1_class;
	private Pikachu2P player2_class;
	private Ball ball_class;
	private KeyManager keyManager;
	
	public Game2PMode(JFrame frame, int width, int height) {
		super(frame, width, height);
		
		player1_class = new Pikachu1P(frame, this, 70, 380-30, 120, 120);
		player2_class = new Pikachu2P(frame, this, 810, 380-30, 120, 120);
		ball_class = new Ball(frame, this, 100, 100, 80, 80);
		keyManager = new KeyManager();
		
		frame.addKeyListener(keyManager);	
	}
	
	public void start() {
		Timer timer = new Timer();
		
		TimerTask task_update = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				keyManager.update();
				player1_class.update();
				player2_class.update();
				ball_class.update();
			}
		};
		
		timer.schedule(task_update, 0, 50);
	}
	
	
	public KeyManager getKeyManger() {
		return keyManager;
	}
		
	public Pikachu1P getPlayer1P() {
		return player1_class;
	}
	
	public Pikachu2P getPlayer2P() {
		return player2_class;
	}
	
	
}
