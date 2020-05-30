package volleyball;

import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import control.KeyManager;



public class Game2PMode {
	public JLabel player1 = new JLabel(new ImageIcon(getClass().getResource("/image/1P.gif")));
	public JLabel player2 = new JLabel(new ImageIcon(getClass().getResource("/image/2P.gif")));
	public JLabel ball = new JLabel(new ImageIcon(getClass().getResource("/image/ball.png")));
	public JLabel stick = new JLabel(new ImageIcon(getClass().getResource("/image/stick.jpg")));
	public int width, height;
	
	private Pikachu1P player1_class;
	private Pikachu2P player2_class;
	private Ball ball_class;
	private KeyManager keyManager;
	
	public Game2PMode(JFrame frame, int width, int height) {
		this.width = width;
		this.height = height;
		
		player1.setSize(120, 120);
		player1.setLocation(50, 380-30);
		player1.setVisible(true);
		
		player2.setSize(120, 120);
		player2.setLocation(810, 380-30);
		player2.setVisible(true);
		
		ball.setSize(80, 80);
		ball.setLocation(100, 100);
		ball.setVisible(true);

		stick.setSize(17, 235);
		stick.setLocation(483, 500 -235-30);
		stick.setVisible(true);
		
		frame.add(player1);
		frame.add(player2);
		frame.add(ball);
		frame.add(stick);
		
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
	
	public JLabel getNet() {
		return stick;
	}
}
