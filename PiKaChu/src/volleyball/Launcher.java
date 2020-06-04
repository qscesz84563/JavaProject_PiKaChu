package volleyball;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.InputContext;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
//import java.util.Timer;

import org.json.JSONObject;

import java.io.BufferedReader;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.math.*;
import java.net.Socket;

public class Launcher extends JFrame implements ActionListener, KeyListener{

	public boolean  enter; 
	StartScreen start;
	Game2PMode game2P;
	GameComputerMode gameComputer;
	GameServer gameServer;
	GameClient gameClient;
	AePlayWave apw;
	public static final int START = 0;
	public static final int NOTSTART = 1;
	public int check=START;
	public JTextField t1, t2;
	public JButton server_enter = new JButton("Enter");
	public JButton client_enter = new JButton("Enter");
	public int client_check = 0;
	public JFrame server_frame, client_frame;
	public Timer timer_win;
	public TimerTask task_win;
	public JLabel winner_1p = new JLabel(new ImageIcon("src/image/1pwin.png"));
	public JLabel winner_2p = new JLabel(new ImageIcon("src/image/2pwin.png"));
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("in!");
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

		winner_1p.setSize(282, 104);
		winner_1p.setLocation((1000-282)/2, (500-104)/2);
		winner_1p.setVisible(false);
		
		winner_2p.setSize(282, 104);
		winner_2p.setLocation((1000-282)/2, (500-104)/2);
		winner_2p.setVisible(false);
		
		add(winner_1p);
		add(winner_2p);
		
		start = new StartScreen(this,1000,500);
		start.check_type();	
		apw = new AePlayWave("src/music/bgm.wav");
		apw.start();
		
		
//		Scanner scan = new Scanner(System.in);
//		String mode = scan.next();
//		
//		if (mode.toLowerCase().equals("2p")) {
//			// 2P mode
//			Game2PMode game2P = new Game2PMode(this, 1000, 500);
//			game2P.start();
//			
//		} else if (mode.toLowerCase().equals("internet1p")) {
//			// Internet Server
//			GameServer gameServer = new GameServer(this, 1000, 500); 
//			gameServer.builtConnection();
//			gameServer.start();
//			
//		} else if (mode.toLowerCase().equals("internet2p")) {
//			 // Client
//			 // get IP & port
//			 String IP = scan.next();
//			 int port = scan.nextInt();
//			 scan.close();
//			
//			 GameClient gameClient = new GameClient(this, 1000, 500);
//			 gameClient.buildConnection(IP, port);
//			 gameClient.start();
//			
//		} else if(mode.toLowerCase().equals("computer")) { 
//			// computer mode
//			GameComputerMode gameComputer = new GameComputerMode(this, 1000, 500);
//			gameComputer.start();
//		}
		
		
		client_enter.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(client_check == 1) {
					start.stop();
					newGameClient();
					String IP = t1.getText();
					String port = t2.getText();
					gameClient.buildConnection(IP, Integer.parseInt(port));
					gameClient.start();
					
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check = NOTSTART;
					client_check = 0;
					client_frame.setVisible(false);
					timer_win.schedule(task_win, 0, 1000);
				}
			}
		});
		
		server_enter.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				server_frame.setVisible(false);
			}
		});
		
		timer_win = new Timer();
		task_win = new TimerTask() {
			@Override
			public void run() {				
				if (check == NOTSTART) {
					if (start.choose==StartScreen.PLAYER1) {
						if(gameComputer.player1_class.feature_num.get("score") >= 3) {
							gameComputer.timer.cancel();
							winner_1p.setVisible(true);
						}
						else if(gameComputer.computer_class.feature_num.get("score") >= 3) {
							gameComputer.timer.cancel();
							winner_2p.setVisible(true);
						}
					}
					else if (start.choose==StartScreen.PLAYER2) {
						if(game2P.player1_class.feature_num.get("score") >= 3) {
							game2P.timer.cancel();
							winner_1p.setVisible(true);
						}
						else if(game2P.player2_class.feature_num.get("score") >= 3) {
							game2P.timer.cancel();
							winner_2p.setVisible(true);
						}
					}
					
					
					else if (start.choose==StartScreen.SERVER) {
						if(gameServer.player1_class.getFeatNum().get("score") >= 3) {
							gameServer.timer.cancel();
							winner_1p.setVisible(true);
						}
						else if(gameServer.player2_class.getFeatNum().get("score") >= 3) {
							gameServer.timer.cancel();
							winner_2p.setVisible(true);
						}
					}
					else if (start.choose==StartScreen.CLIENT) {
						if(gameClient.player1_class.getFeatNum().get("score") >= 3) {
							gameClient.timer.cancel();
							winner_1p.setVisible(true);
						}
						else if(gameClient.player2_class.getFeatNum().get("score") >= 3) {
							gameClient.timer.cancel();
							winner_2p.setVisible(true);
						}
					}
				}
			}
		};
		
	}
	
	public void newGameClient() {
		gameClient = new GameClient(this, 1000, 500);
	}
	
	public void server() {
		server_frame = new JFrame("Server's window");  
		JTextField t1;  
		 
		server_enter.setSize(80,30);
		server_enter.setLocation(100,80);
		server_enter.setVisible(true);
		 
		server_frame.add(server_enter);
		
		server_frame.setLocation(700, 250);  
		server_frame.setSize(300,150);  
		server_frame.setLayout(null);  
		server_frame.setVisible(true);  
	}
	
	public void client() {
		client_frame = new JFrame("Client's window");  
		
		JLabel Label1 = new JLabel("IP:");
		JLabel Label2 = new JLabel("PORT:");
		
//		t1 = new JTextField("Please type the IP");  
//		t2 = new JTextField("Please type the PORT");  
		
		t1 = new JTextField();  
		t2 = new JTextField();  
		
		Label1.setBounds(32,10, 40, 30);
		Label2.setBounds(10,40, 40, 30);
		
		t1.setBounds(60,10, 200,30); 
		t2.setBounds(60,40, 200,30); 
		client_enter.setSize(80,30);
		client_enter.setLocation(100,80);
		
		client_frame.add(t1); 
		client_frame.add(t2); 
		client_frame.add(client_enter);
		client_frame.add(Label1);
		client_frame.add(Label2);
		
		client_frame.setLocation(700, 250);  
		client_frame.setSize(300,150); 
		client_frame.setLayout(null);  
		client_frame.setVisible(true);  
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (start.choose==StartScreen.PLAYER1) {
				if (check == START) {
					apw.stop();
					start.stop();
					System.out.println("in");
					gameComputer = new GameComputerMode(this, 1000, 500);
					gameComputer.start();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check = NOTSTART;
					timer_win.schedule(task_win, 0, 1000);
				}
				
			}
			else if (start.choose==StartScreen.PLAYER2) {
				if (check == START) {
					apw.stop();
					start.stop();	
					game2P = new Game2PMode(this, 1000, 500);
					game2P.start();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check = NOTSTART;
					timer_win.schedule(task_win, 0, 1000);
				}
				
			}
			
			else if (start.choose==StartScreen.SERVER) {
				if (check == START) {
					apw.stop();	
					server();
					start.stop();
					
					gameServer = new GameServer(this, 1000, 500); 
					gameServer.builtConnection();
					gameServer.start();
					
					server_frame.setVisible(false);
					
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check = NOTSTART;
					timer_win.schedule(task_win, 0, 1000);
				}
			}
			else if (start.choose==StartScreen.CLIENT) {
				if (check == START) {
					client();
					client_check = 1;
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (check == NOTSTART) {
				if (start.choose==StartScreen.PLAYER1) {
					gameComputer.stop();
					start = new StartScreen(this,1000,500);
					start.check_type();	
					apw.stop();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check=START;
					winner_1p.setVisible(false);
					winner_2p.setVisible(false);
				}
				else if (start.choose==StartScreen.PLAYER2) {
					game2P.stop();
					start = new StartScreen(this,1000,500);
					start.check_type();	
					apw.stop();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check=START;
					winner_1p.setVisible(false);
					winner_2p.setVisible(false);
				}
				else if (start.choose==StartScreen.SERVER) {
					gameServer.stop();
					start = new StartScreen(this,1000,500);
					start.check_type();
					apw.stop();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check=START;
					winner_1p.setVisible(false);
					winner_2p.setVisible(false);
				}
				else if (start.choose==StartScreen.CLIENT) {
					gameClient.stop();
					start = new StartScreen(this,1000,500);
					start.check_type();
					apw.stop();
					apw = new AePlayWave("src/music/bgm.wav");
					apw.start();
					check=START;
					winner_1p.setVisible(false);
					winner_2p.setVisible(false);
				}
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
