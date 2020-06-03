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
	public JLabel player1 = new JLabel(new ImageIcon("src/image/1p.png"));
	public JLabel player2 = new JLabel(new ImageIcon("src/image/2p.png"));
	public JLabel player3 = new JLabel(new ImageIcon("src/image/remote.png"));
	public JLabel player1_2 = new JLabel(new ImageIcon("src/image/1p-2.png"));
	public JLabel player2_2 = new JLabel(new ImageIcon("src/image/2p-2.png"));
	public JLabel player3_2 = new JLabel(new ImageIcon("src/image/remote-2.png"));
//	public JLabel circle1 = new JLabel(new ImageIcon("src/image/cloud.png"));
//	public JLabel circle2 = new JLabel(new ImageIcon("src/image/cloud.png"));
//	public JLabel circle3 = new JLabel(new ImageIcon("src/image/cloud.png"));
	public JLabel title = new JLabel(new ImageIcon("src/image/title.png"));
	public JLabel server = new JLabel(new ImageIcon("src/image/server.png"));
	public JLabel client = new JLabel(new ImageIcon("src/image/client.png"));
	public JLabel server_2 = new JLabel(new ImageIcon("src/image/server-2.png"));
	public JLabel client_2 = new JLabel(new ImageIcon("src/image/client-2.png"));

	public int width, height;
	public static final int PLAYER1 = 1; 
	public static final int PLAYER2 = 2;
	public static final int PLAYER3 = 3;
	public static final int SERVER = 4;
	public static final int CLIENT = 5;
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
		
//		circle1.setSize(246, 200);
//		circle1.setLocation(295, 235);
//		circle2.setSize(246, 200);
//		circle2.setLocation(295+170, 235);
//		circle3.setSize(246, 200);
//		circle3.setLocation(295+340+30, 235);
		
		player1.setSize(150, 105);
		player1.setLocation(360, 280);
		player1_2.setSize(150, 105);
		player1_2.setLocation(360, 280);
		
		player2.setSize(150, 105);
		player2.setLocation(530, 280);
		player2_2.setSize(150, 105);
		player2_2.setLocation(530, 280);

		player3.setSize(236, 105);
		player3.setLocation(700, 280);
		player3_2.setSize(236, 105);
		player3_2.setLocation(700, 280);
		
		server.setSize(200,100);
		server.setLocation(700, 260);
		client.setSize(200,100);
		client.setLocation(700, 300);
		server_2.setSize(200,100);
		server_2.setLocation(700, 260);
		client_2.setSize(200,100);
		client_2.setLocation(700, 300);
		
//		frame.add(circle1);
//		frame.add(circle2);
//		frame.add(circle3);
		frame.add(player1);
		frame.add(player1_2);
		frame.add(player2);
		frame.add(player2_2);
		frame.add(player3);
		frame.add(player3_2);
		frame.add(server_2);
		frame.add(client_2);
		frame.add(server);
		frame.add(client);
		
		
		title.setSize(737, 252);
		title.setLocation((1000-737)/2, 0);
		title.setVisible(true);
		frame.add(title);
		
		bigPikachu.setSize(300, 300);
		bigPikachu.setLocation(50, 150);
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
		frame.remove(server);
		frame.remove(client);
		frame.remove(server_2);
		frame.remove(client_2);

		//		frame.remove(circle1);
//		frame.remove(circle2);
//		frame.remove(circle3);
		frame.remove(title);
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
					if (left==true) {choose = PLAYER3;}
					else if (right==true) {choose = PLAYER2;}
				}
				else if (choose==PLAYER2) {
					if (left==true) {choose = PLAYER1;}
					else if (right==true) {choose = PLAYER3;}
				}
				else if (choose==PLAYER3) {
					if (left==true) {choose = PLAYER2;}
					else if (enter==true) {choose = SERVER;}
				}
				else if (choose==SERVER) {
					if (left==true) {choose = PLAYER2;}
					else if (down==true) {choose = CLIENT;}
				}
				
				else if (choose==CLIENT) {
					if (up==true) {choose = SERVER;}
					else if (left==true) {choose = PLAYER2;}
				}
				
				switch(choose) {
				
					case PLAYER1: 
						player1.setVisible(false);
						player1_2.setVisible(true);
						player2.setVisible(true);
						player2_2.setVisible(false);
						player3.setVisible(true);
						player3_2.setVisible(false);
//						circle1.setVisible(true);
//						circle2.setVisible(false);
//						circle3.setVisible(false);
						server.setVisible(false);
						client.setVisible(false);
						server_2.setVisible(false);
						client_2.setVisible(false);
						break;
			        case PLAYER2:
			    		player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(false);
			    		player2_2.setVisible(true);
			    		player3.setVisible(true);
			    		player3_2.setVisible(false);
//						circle1.setVisible(false);
//						circle2.setVisible(true);
//						circle3.setVisible(false);
						server.setVisible(false);
						client.setVisible(false);
						server_2.setVisible(false);
						client_2.setVisible(false);
						break;
	
			        case PLAYER3:
			    		player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(true);
			    		player2_2.setVisible(false);
			    		player3.setVisible(false);
			    		player3_2.setVisible(true);
//						circle1.setVisible(false);
//						circle2.setVisible(false);
//						circle3.setVisible(true);
						server.setVisible(false);
						client.setVisible(false);
						server_2.setVisible(false);
						client_2.setVisible(false);
						break;
			        case SERVER:
			        	player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(true);
			    		player2_2.setVisible(false);
			    		player3.setVisible(false);
			    		player3_2.setVisible(false);
//						circle1.setVisible(false);
//						circle2.setVisible(false);
//						circle3.setVisible(false);
						server.setVisible(false);
						client.setVisible(false);
						server_2.setVisible(true);
						client_2.setVisible(false);
						break;
			        case CLIENT:
			        	player1.setVisible(true);
			    		player1_2.setVisible(false);
			    		player2.setVisible(true);
			    		player2_2.setVisible(false);
			    		player3.setVisible(false);
			    		player3_2.setVisible(false);
//						circle1.setVisible(false);
//						circle2.setVisible(false);
//						circle3.setVisible(false);
						server.setVisible(false);
						client.setVisible(false);
						server_2.setVisible(false);
						client_2.setVisible(true);
						break;
	
				}
			}
		};
		timer.schedule(task_update, 0, 140);
	}

}
