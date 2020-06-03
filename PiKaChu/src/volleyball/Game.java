package volleyball;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Dictionary;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import character.Player;
import control.KeyManager;

public abstract class Game{
	public JLabel background = new JLabel(new ImageIcon("src/image/background2.png"));
	public JLabel player1 = new JLabel(new ImageIcon(getClass().getResource("/image/1P.gif")));
	public JLabel player2 = new JLabel(new ImageIcon(getClass().getResource("/image/2P.gif")));
	public JLabel ball = new JLabel(new ImageIcon(getClass().getResource("/image/ball.png")));
	public JLabel stick = new JLabel(new ImageIcon(getClass().getResource("/image/stick.jpg")));
	public ImageIcon player1_idle = new ImageIcon(getClass().getResource("/image/1P.gif"));
	public ImageIcon player2_idle = new ImageIcon(getClass().getResource("/image/2P.gif"));
	public ImageIcon dash_left = new ImageIcon(getClass().getResource("/image/dash_left.png"));
	public ImageIcon dash_right = new ImageIcon(getClass().getResource("/image/dash_right.png"));
	public ImageIcon rball_left_slow = new ImageIcon(getClass().getResource("/image/left120.gif"));
	public ImageIcon rball_left_fast = new ImageIcon(getClass().getResource("/image/left40.gif"));
	public ImageIcon rball_right_slow = new ImageIcon(getClass().getResource("/image/right120.gif"));
	public ImageIcon rball_right_fast = new ImageIcon(getClass().getResource("/image/right40.gif"));
	public ImageIcon ball_idle = new ImageIcon(getClass().getResource("/image/ball.png"));
	
	public JLabel[] player1_score1 = new JLabel[]{
			new JLabel(new ImageIcon(getClass().getResource("/image/score/0.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/1.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/2.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/3.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/4.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/5.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/6.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/7.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/8.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/9.png")))};

	public JLabel[] player1_score2 = new JLabel[]{
			new JLabel(new ImageIcon(getClass().getResource("/image/score/0.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/1.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/2.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/3.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/4.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/5.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/6.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/7.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/8.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/9.png")))};
	
	public JLabel[] player2_score1 = new JLabel[]{
			new JLabel(new ImageIcon(getClass().getResource("/image/score/0.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/1.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/2.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/3.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/4.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/5.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/6.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/7.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/8.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/9.png")))};
	
	public JLabel[] player2_score2 = new JLabel[]{
			new JLabel(new ImageIcon(getClass().getResource("/image/score/0.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/1.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/2.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/3.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/4.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/5.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/6.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/7.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/8.png"))),
			new JLabel(new ImageIcon(getClass().getResource("/image/score/9.png")))};
	
	public int width, height;
	public JFrame frame;
	
	public Game(JFrame frame, int width, int height) {
		this.width = width;
		this.height = height;
		this.frame = frame;
		
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
		stick.setLocation(492, 500-235-25);
		//unmodified
		stick.setVisible(true);
		
		background.setSize(1000, 500);
		background.setLocation(0, 0);
		background.setVisible(true);
		
		frame.add(player1);
		frame.add(player2);
		frame.add(ball);
		frame.add(stick);
	
		for(int i = 0; i < 10; i++) {
			frame.add(player1_score2[i]);
			player1_score2[i].setVisible(false);
			player1_score2[i].setSize(100, 100);
			player1_score2[i].setLocation(20, 30);
		}
		
		for(int i = 0; i < 10; i++) {
			frame.add(player1_score1[i]);
			player1_score1[i].setVisible(true);
			player1_score1[i].setSize(100, 100);
			player1_score1[i].setLocation(90, 30);
		}
		
		for(int i = 0; i < 10; i++) {
			frame.add(player2_score2[i]);
			player2_score2[i].setVisible(false);
			player2_score2[i].setSize(100, 100);
			player2_score2[i].setLocation(730, 30);
		}
		
		for(int i = 0; i < 10; i++) {
			frame.add(player2_score1[i]);
			player2_score1[i].setVisible(true);
			player2_score1[i].setSize(100, 100);
			player2_score1[i].setLocation(800, 30);
		}
		
		frame.add(background);
	}
	
	public abstract void start();
	
	public void stop() {
		frame.remove(player1);
		frame.remove(player2);
		frame.remove(ball);
		frame.remove(stick);
		frame.remove(background);
	
		for(int i = 0; i < 10; i++) {
			frame.remove(player1_score1[i]);
			frame.remove(player1_score2[i]);
			frame.remove(player2_score1[i]);
			frame.remove(player2_score2[i]);
		}
		
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	public JLabel getNet() {
		return stick;
	}

	public abstract KeyManager getKeyManager();
	public abstract Player getPlayer1P();
	public abstract Player getPlayer2P();
	public abstract int getBallX();
	public abstract int getBallY();
	
	
	
}

