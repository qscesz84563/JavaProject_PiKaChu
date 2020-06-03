package volleyball;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import character.*;
import control.KeyManager;



public class GameComputerMode extends Game{
	private Pikachu1P player1_class;
	private PikachuComputer computer_class;
	private Ball ball_class;
	private KeyManager keyManager;
	
	public GameComputerMode(JFrame frame, int width, int height) {
		super(frame, width, height);
		
		player1_class = new Pikachu1P(frame, this, 70, 380-30, 120, 120);
		computer_class = new PikachuComputer(frame, this, 810, 380-30, 120, 120);
		
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
				if(keyManager.pause == false) {
					player1_class.update();
					computer_class.update();
					ball_class.update();
				}
			}
		};
		
		timer.schedule(task_update, 0, 50);
	}
	
	
	public KeyManager getKeyManger() {
		return keyManager;
	}
		
	public Player getPlayer1P() {
		return player1_class;
	}
	
	public Player getPlayer2P() {
		return computer_class;
	}


	public KeyManager getKeyManager() {
		return keyManager;
	}

	
	public int getBallX() {
		return ball_class.getX();
	}

	public int getBallY() {
		return ball_class.getY();
	}
}
