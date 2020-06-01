package volleyball;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartScreen implements KeyListener{
	
	public JLabel bigPikachu = new JLabel(new ImageIcon("src/image/begin_pikachu.gif"));
	public JLabel blue = new JLabel(new ImageIcon("src/image/blue.png"));
	public JLabel player1 = new JLabel(new ImageIcon("src/image/1player.png"));
	public JLabel player2 = new JLabel(new ImageIcon("src/image/2player.png"));
	public JLabel player3 = new JLabel(new ImageIcon("src/image/remote player.png"));
	public JLabel player1_2 = new JLabel(new ImageIcon("src/image/1player-2.png"));
	public JLabel player2_2 = new JLabel(new ImageIcon("src/image/2player-2.png"));
	public JLabel player3_2 = new JLabel(new ImageIcon("src/image/remote player-2.png"));
	public int width, height;
	public static final int PLAYER1 = 1; 
	public static final int PLAYER2 = 2;
	public static final int PLAYER3 = 3;
	public int choose=PLAYER1;
//	private int key;
	public Timer timer;
	
	private boolean keys[];
	public boolean up,  down, right, left, enter; 
	public JFrame frame;
	public StartScreen(JFrame frame, int width, int height) {
		this.width = width;
		this.height = height;
		this.frame = frame;
		keys = new boolean[256];
		frame.addKeyListener(this);
		
		player1.setSize(441, 59);
		player1.setLocation(470, 200);
		player1_2.setSize(509, 94);
		player1_2.setLocation(440, 185);
		
		player2.setSize(441, 59);
		player2.setLocation(470, 280);
		player2_2.setSize(509, 94);
		player2_2.setLocation(440, 265);

		player3.setSize(550, 59);
		player3.setLocation(470-55, 360);
		player3_2.setSize(635, 101);
		player3_2.setLocation(470-95, 345);
		
		frame.add(player1);
		frame.add(player1_2);
		frame.add(player2);
		frame.add(player2_2);
		frame.add(player3);
		frame.add(player3_2);
		
		bigPikachu.setSize(450, 410);
		bigPikachu.setLocation(40, 50);
		bigPikachu.setVisible(true);
		frame.add(bigPikachu);
		
		blue.setSize(1000, 500);
		blue.setLocation(0, 0);
		blue.setVisible(true);
		frame.add(blue);
		
		
	}
	
	public void stop() {
		frame.removeKeyListener(this);
		frame.remove(player1);
		frame.remove(player1_2);
		frame.remove(player2);
		frame.remove(player2_2);
		frame.remove(player3);
		frame.remove(player3_2);
		frame.remove(bigPikachu);
		frame.remove(blue);
		timer.cancel();
		
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	
	public void choose_type() {
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		enter = keys[KeyEvent.VK_ENTER];
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void check_type() {
		timer = new Timer();
		TimerTask task_update = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				choose_type();
				if (choose==PLAYER1) {
					if (up==true) {choose = PLAYER3;}
					else if (down==true) {choose = PLAYER2;}
				}
				else if (choose==PLAYER2) {
					if (up==true) {choose = PLAYER1;}
					else if (down==true) {choose = PLAYER3;}
				}
				else if (choose==PLAYER3) {
					if (up==true) {choose = PLAYER2;}
					else if (down==true) {choose = PLAYER1;}
				}
				
				switch(choose) {
				
					case PLAYER1: 
						player1.setVisible(false);
						player1_2.setVisible(true);
						player2.setVisible(true);
						player2_2.setVisible(false);
						player3.setVisible(true);
						player3_2.setVisible(false);
						break;
			        case PLAYER2:
			    		player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(false);
			    		player2_2.setVisible(true);
			    		player3.setVisible(true);
			    		player3_2.setVisible(false);
						break;
	
			        case PLAYER3:
			    		player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(true);
			    		player2_2.setVisible(false);
			    		player3.setVisible(false);
			    		player3_2.setVisible(true);
						break;
	
				}
			}
		};
		timer.schedule(task_update, 0, 140);
	}

}
