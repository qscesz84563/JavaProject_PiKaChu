package volleyball;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import character.Player;
import control.KeyManager;



public class Game2PMode extends Game{
	public Pikachu1P player1_class;
	public Pikachu2P player2_class;
	public Ball ball_class;
	public KeyManager keyManager;
	public int winner_time_count = 0, winner_pause = 0;
	public Timer timer;
	
	public Game2PMode(JFrame frame, int width, int height) {
		super(frame, width, height);
		
		player1_class = new Pikachu1P(frame, this, 70, 380-30, 120, 120);
		player2_class = new Pikachu2P(frame, this, 810, 380-30, 120, 120);
		ball_class = new Ball(frame, this, 100, 100, 80, 80);
		keyManager = new KeyManager();

		
		frame.addKeyListener(keyManager);	
	}
	
	public void start() {
		timer = new Timer();
		JLabel win1p = this.win1p;
		JLabel win2p = this.win2p;
		TimerTask task_update = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				keyManager.update();
				if(keyManager.pause == false) {
					if(winner_pause == 0) {
						player1_class.update();
						player2_class.update();
						ball_class.update();
					}
				}
				
				if(ball_class.winner_check != 0) {
					if(ball_class.winner_check == 1)
						win1p.setVisible(false);
					else
						win2p.setVisible(false);
					winner_time_count++;
					winner_pause = 1;
					if(winner_time_count == 10) {
						if(ball_class.winner_check == 1)
							win1p.setVisible(false);
						else
							win2p.setVisible(false);						
						winner_pause = 0;
						winner_time_count = 0;
						ball_class.winner_check = 0;
					}
				}
			}
		};
		
		timer.schedule(task_update, 0, 50);
	}
	

	
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
		
	public Player getPlayer1P() {
		return player1_class;
	}
	
	public Player getPlayer2P() {
		return player2_class;
	}

	@Override
	public int getBallX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBallY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
