package volleyball;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import character.Player;
import character.Virtual2P;
import control.KeyManager;

public class GameServer extends Game{
	private Player player1_class;
	private Player player2_class;
	private Ball ball_class;
	private KeyManager keyManager;
	
	public GameServer(JFrame frame, int width, int height) {
		super(frame, width, height);
		
		player1_class = new Pikachu1P(frame, this, 70, 380-30, 120, 120);
		player2_class = new Pikachu2P(frame, this, 810, 380-30, 120, 120);
		ball_class = new Ball(frame, this, 100, 100, 80, 80);
		keyManager = new KeyManager();
	}
	
	public void start() {
		Timer timer = new Timer();
		try {
			ServerSocket serverSocket = new ServerSocket(60000);
			Scanner s = new Scanner(System.in);
			System.out.println("server started....");
			
			while (true) {
				Socket clientSocket = serverSocket.accept();
				s.next();
				
				ObjectInputStream clientInput = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream clientOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				
				System.out.println(clientInput.readLine());
			}
			// Dictionary<String, Integer> InFeatNum = (Hashtable<String, Integer>)clientInput.readObject();
			// Dictionary<String, Boolean> InFeatBool = (Hashtable<String, Boolean>)clientInput.readObject();
			
			// ClientOutput.writeObject(obj);  <- send 1P information to client (feature_num)
			// ClientOutput.writeObject(obj);  <- send 1P information to client (feature_bool)
			
			// s.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
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
	
	
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
		
	public Player getPlayer1P() {
		return player1_class;
	}
	
	public Player getPlayer2P() {
		return player2_class;
	}

}
