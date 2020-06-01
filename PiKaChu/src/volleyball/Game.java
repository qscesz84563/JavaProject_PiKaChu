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
	public JLabel player1 = new JLabel(new ImageIcon(getClass().getResource("/image/1P.gif")));
	public JLabel player2 = new JLabel(new ImageIcon(getClass().getResource("/image/2P.gif")));
	public JLabel ball = new JLabel(new ImageIcon(getClass().getResource("/image/ball.png")));
	public JLabel stick = new JLabel(new ImageIcon(getClass().getResource("/image/stick.jpg")));
	public ImageIcon player1_idle = new ImageIcon(getClass().getResource("/image/1P.gif"));
	public ImageIcon player2_idle = new ImageIcon(getClass().getResource("/image/2P.gif"));
	public ImageIcon dash_left = new ImageIcon(getClass().getResource("/image/dash_left.png"));
	public ImageIcon dash_right = new ImageIcon(getClass().getResource("/image/dash_right.png"));
	public int width, height;
	
	public Game(JFrame frame, int width, int height) {
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

		stick.setSize(20, 150);
		stick.setLocation(483, 500 -235-30+80);
		stick.setVisible(true);
		
		frame.add(player1);
		frame.add(player2);
		frame.add(ball);
		frame.add(stick);
		
	}
	
	public abstract void start();
	
	public JLabel getNet() {
		return stick;
	}

	public abstract KeyManager getKeyManager();
	public abstract Player getPlayer1P();
	public abstract Player getPlayer2P();
	public abstract int getBallX();
	public abstract int getBallY();
	
	
	
}

