package volleyball;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
//import java.util.Timer;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.math.*;

public class Launcher extends JFrame implements ActionListener, KeyListener{

	JLabel background = new JLabel(new ImageIcon("src/image/background2.png"));
	public boolean  enter; 
	StartScreen start;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Launcher volleyball_frame = new Launcher();
		volleyball_frame.setVisible(true);
	}
	
	public Launcher() {
		setSize(1000, 540);
		setLocation(360, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		addKeyListener(this);
		setFocusable(true);
		requestFocusInWindow();
		
		start = new StartScreen(this,1000,500);
		start.check_type();	
		
//		Scanner scan = new Scanner(System.in);
//		String mode = scan.next();
//		
//		if (mode.toLowerCase().equals("2p")) {
//			Game2PMode game2P = new Game2PMode(this, 1000, 500);
//			game2P.start();
//		} else if (mode.toLowerCase().equals("internet1p")) {
//			GameServer gameServer = new GameServer(this, 1000, 500);
//			//GameClient gameClient = new GameClient(this, 1000, 500);
//			gameServer.start();
//			// gameClient.start();
//		} else if (mode.toLowerCase().equals("internet2p")) {
//			// GameServer gameServer = new GameServer(this, 1000, 500);
//			GameClient gameClient = new GameClient(this, 1000, 500);
//			// gameServer.start();
//			gameClient.start();
//		} else if(mode.toLowerCase().equals("computer")) {
//			// GameServer gameServer = new GameServer(this, 1000, 500);
//			GameComputerMode gameComputer = new GameComputerMode(this, 1000, 500);
//			// gameServer.start();
//			gameComputer.start();
//		}
//		
//		background.setSize(1000, 500);
//		background.setLocation(0, 0);
//		background.setVisible(true);
//		add(background);
//		// fuck 
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (start.choose==start.PLAYER1) {
				
				start.stop();

				background.setSize(1000, 500);
				background.setLocation(0, 0);
				background.setVisible(true);
				add(background);
			}
			else if (start.choose==start.PLAYER2) {
				
				start.stop();
				
				Game2PMode game2P = new Game2PMode(this, 1000, 500);
				game2P.start();

				background.setSize(1000, 500);
				background.setLocation(0, 0);
				background.setVisible(true);
				add(background);
			}
			
			else if (start.choose==start.PLAYER3) {
				
				
			}
			
			
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
