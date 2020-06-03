package volleyball;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import character.Ball;
import character.Pikachu1P;
import character.Pikachu2P;
import character.Player;
import character.Virtual1P;
import character.Virtual2P;
import control.KeyManager;
import java.io.InputStreamReader;

import org.json.*;

public class GameClient extends Game{
	private Player player1_class;
	private Player player2_class;
	private Ball ball_class;
	private KeyManager keyManager;
	
	private Socket clientSocket;
	private BufferedReader serverInput = null;
	private DataOutputStream serverOutput = null;
	
	private String ballControlFlag = "server", tempBuffer = "";
	private boolean isLagBallFlag = false;
	 
	public GameClient(JFrame frame, int width, int height) {
		super(frame, width, height);
		
		player1_class = new Virtual1P(frame, this, 70, 380-30, 120, 120);
		player2_class = new Pikachu2P(frame, this, 810, 380 - 30, 120, 120);
		ball_class = new Ball(frame, this, 100, 100, 80, 80);
		keyManager = new KeyManager();
		frame.addKeyListener(keyManager);
	}

	public void buildConnection(String serverIP, int serverPort) {
		try {
			// build connection
			System.out.println(serverIP);
			System.out.println(serverPort);
			clientSocket = new Socket(serverIP, serverPort);
			System.out.println("something");
			
			// set IOstream
			serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			serverOutput = new DataOutputStream(clientSocket.getOutputStream());
			
			// write test message
			serverOutput.writeBytes("TEST!!!\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
	
		public void start() {
		Timer timer = new Timer();
		
		TimerTask task_update = new TimerTask() { 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// Key manager & player update
				keyManager.update();
				if(keyManager.pause == false) {
					player2_class.update();
					
					String featNum_toServer = Dict2String_num(player2_class.getFeatNum()),
							featBool_toServer = Dict2String_bool(player2_class.getFeatBool()),
							featNum_fromServer = "", featBool_fromServer = "";
					
					Dictionary<String, Integer> tempNum = null, tempNumBall = null;
					Dictionary<String, Boolean> tempBool = null;
					
					try {
						// write features to server
						serverOutput.writeBytes(featNum_toServer + '\n');
						serverOutput.writeBytes(featBool_toServer + '\n'); 
						
						// read features from server
						// use buffer
						tempBuffer = serverInput.readLine();
						
						// deal with additional ball feature sent from server (occasionally happens)
						if (tempBuffer.indexOf("score") != -1) {
							// if there is "score" = normal feature 
							featNum_fromServer = tempBuffer;
						} else {
							// if no "score" = ball feature
							featNum_fromServer = serverInput.readLine();
							isLagBallFlag = true;
						}
						
						// read boolean feature 
						featBool_fromServer = serverInput.readLine();
					} catch (IOException e) { 
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
					// transfer feature from string to Dictionary
					tempNum = String2Dict_num(featNum_fromServer);
					tempBool = String2Dict_bool(featBool_fromServer);
					
					// deal with press restart on client side but with no response in server side
					if (tempBool.get("restart")) {
						ball_class.isPressRestart = true;
					}
					
					// set received feature to player1
					((Virtual1P)player1_class).setFeature(tempNum, tempBool);
					player1_class.update();
					
					
					// switch ball control
					// -> deal with ball couldn't act normally on client side
					if (ballControlFlag.equals("client")) {
						// send ball information
						String featNum_ball_toServer = Dict2String_num(ball_class.getFeatNum());
						
						try {
							serverOutput.writeBytes(featNum_ball_toServer + '\n');
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else if (ballControlFlag.equals("server")) { 
						// receive ball information
						String featNum_ball_fromServer = "";
						
						// deal with lag ball information
						if (isLagBallFlag) {
							featNum_ball_fromServer = tempBuffer;
							isLagBallFlag = false;
						} else {
							try {
								featNum_ball_fromServer = serverInput.readLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						tempNumBall = String2Dict_ball_num(featNum_ball_fromServer);
						ball_class.setData(tempNumBall);
					}
					
					// call ball_class update
					ball_class.update();
	
					// set ball control flag
					if ((ball_class.getFeatNum().get("x") > player1_class.getX() 
							&& ball_class.getFeatNum().get("x") <= player1_class.getX() + 120) 
							|| ball_class.getFeatNum().get("x") <= 483) {
						ballControlFlag =  "server";
					} else if ((ball_class.getFeatNum().get("x") > player2_class.getX()  
							&& ball_class.getFeatNum().get("x") <= player2_class.getX() + 120) 
							|| ball_class.getFeatNum().get("x") > 483) {
						ballControlFlag =  "client";
					}
					
					tempBuffer = "";
				}
			}
		};
		
		timer.schedule(task_update, 0, 50);
	}
	
	public String setBallControlFlag(int ballX, int player1X, int player2X) {
		if ((ballX > player1X && ballX <= player1X + 120) || ballX <= 483) {
			return "server";
		} else if ((ballX > player2X  && ballX <= player2X + 120) || ballX > 483) {
			return "client";
		}
		
		return "server";
	}
	
	public String Dict2String_num(Dictionary<String, Integer> target) {
		Hashtable<String, Integer> tempHashTable = (Hashtable<String, Integer>)target;
		JSONObject tempJSONO = new JSONObject(tempHashTable);
		return tempJSONO.toString();
	}
	
	public String Dict2String_bool(Dictionary<String, Boolean> target) {
		Hashtable<String, Boolean> tempHashTable = (Hashtable<String, Boolean>)target;
		JSONObject tempJSONO = new JSONObject(tempHashTable);
		return tempJSONO.toString();
	}
	
	public Dictionary<String, Integer> String2Dict_num (String target) {
		Dictionary<String, Integer> trim = new Hashtable<String, Integer>();
		trim.put("score"      , String2Int(target.substring((target.indexOf("score") +  "score".length() + 2)
				     		  			 , target.indexOf("hitCount") - 2)));
		trim.put("hitCount"   , String2Int(target.substring((target.indexOf("hitCount") +  "hitCount".length() + 2)
					     	  			 , target.indexOf("x") - 2)));
		trim.put("x"          , String2Int(target.substring((target.indexOf("x") +  "x".length() + 2)
				     	  				 , target.indexOf("width") - 2)));
		trim.put("width"      , String2Int(target.substring((target.indexOf("width") +  "width".length() + 2)
					     	  			 , target.indexOf("y") - 2)));
		trim.put("y"          , String2Int(target.substring((target.indexOf("y") +  "y".length() + 2)
					     	  			 , target.indexOf("moveSpeedY") - 2)));
		trim.put("moveSpeedY" , String2Int(target.substring((target.indexOf("moveSpeedY") +  "moveSpeedY".length() + 2)
					     	  			 , target.indexOf("height") - 2)));
		trim.put("height"     , String2Int(target.substring((target.indexOf("height") +  "height".length() + 2)
					     				 , target.indexOf("moveSpeedX") - 2)));
		trim.put("moveSpeedX" , String2Int(target.substring((target.indexOf("moveSpeedX") +  "moveSpeedX".length() + 2)
					     				 , target.length() - 1)));
		
		return trim;
	}
	
	public Dictionary<String, Boolean> String2Dict_bool(String target) {
		Dictionary<String, Boolean> trim = new Hashtable<String, Boolean>();
		trim.put("down_2P"   , String2Bool(target.substring((target.indexOf("down_2P") +  "down_2P".length() + 2)
						 				 , target.indexOf("jumping") - 2)));
		trim.put("jumping"   , String2Bool(target.substring((target.indexOf("jumping") +  "jumping".length() + 2)
						 				 , target.indexOf("down_1P") - 2)));
		trim.put("down_1P"   , String2Bool(target.substring((target.indexOf("down_1P") +  "down_1P".length() + 2)
						 				 , target.indexOf("restart") - 2)));
		trim.put("restart"   , String2Bool(target.substring((target.indexOf("restart") +  "restart".length() + 2)
						 				 , target.indexOf("right_1P") - 2)));
		
		trim.put("right_1P"  , String2Bool(target.substring((target.indexOf("right_1P") +  "right_1P".length() + 2)
						 				 , target.indexOf("right_2P") - 2)));
		trim.put("right_2P"  , String2Bool(target.substring((target.indexOf("right_2P") +  "right_2P".length() + 2)
						 				 , target.indexOf("goingDown") - 2)));
		trim.put("goingDown" , String2Bool(target.substring((target.indexOf("goingDown") +  "goingDown".length() + 2)
										 , target.indexOf("dashing_left") - 2)));
		
		trim.put("dashing_left"  , String2Bool(target.substring((target.indexOf("dashing_left") +  "dashing_left".length() + 2)
						 				 , target.indexOf("up_1P") - 2)));
		trim.put("up_1P"     , String2Bool(target.substring((target.indexOf("up_1P") +  "up_1P".length() + 2)
						   				 , target.indexOf("up_2P") - 2)));
		trim.put("up_2P"     , String2Bool(target.substring((target.indexOf("up_2P") +  "up_2P".length() + 2)
					   	 				 , target.indexOf("goingUp") - 2)));
		
		trim.put("goingUp"   , String2Bool(target.substring((target.indexOf("goingUp") +  "goingUp".length() + 2)
										 , target.indexOf("dashing_right") - 2)));
		trim.put("dashing_right"  , String2Bool(target.substring((target.indexOf("dashing_right") +  "dashing_right".length() + 2)
						 				 , target.indexOf("spike_2P") - 2)));
		trim.put("spike_2P"  , String2Bool(target.substring((target.indexOf("spike_2P") +  "spike_2P".length() + 2)
						 				 , target.indexOf("spike_1P") - 2)));
		
		trim.put("spike_1P"  , String2Bool(target.substring((target.indexOf("spike_1P") +  "spike_1P".length() + 2)
						 , target.indexOf("left_1P") - 2)));
		trim.put("left_1P"   , String2Bool(target.substring((target.indexOf("left_1P") +  "left_1P".length() + 2)
						 , target.indexOf("left_2P") - 2)));
		trim.put("left_2P"   , String2Bool(target.substring((target.indexOf("left_2P") +  "left_2P".length() + 2)
						 , target.length() - 1)));
		return trim;
	}
	
	public Dictionary<String, Integer> String2Dict_ball_num (String target) {
		Dictionary<String, Integer> trim = new Hashtable<String, Integer>();
		trim.put("x"          , String2Int(target.substring((target.indexOf("x") +  "x".length() + 2)
		  				 , target.indexOf("width") - 2)));
		trim.put("width"      , String2Int(target.substring((target.indexOf("width") +  "width".length() + 2)
			  			 , target.indexOf("y") - 2)));
		trim.put("y"          , String2Int(target.substring((target.indexOf("y") +  "y".length() + 2)
			  			 , target.indexOf("moveSpeedY") - 2)));
		trim.put("moveSpeedY" , String2Int(target.substring((target.indexOf("moveSpeedY") +  "moveSpeedY".length() + 2)
			  			 , target.indexOf("height") - 2)));
		trim.put("height"     , String2Int(target.substring((target.indexOf("height") +  "height".length() + 2)
						 , target.indexOf("moveSpeedX") - 2)));
		trim.put("moveSpeedX" , String2Int(target.substring((target.indexOf("moveSpeedX") +  "moveSpeedX".length() + 2)
						 , target.length() - 1)));
		return trim;
		
	}
	
	public int String2Int(String target) {
		int numeric = 0;
		
		if (target.indexOf('-') == -1) { 
			for (int a = 0; a < target.length(); ++a) 
				numeric += Character.getNumericValue(target.charAt(a)) * (Math.pow(10, target.length() - 1 - a));
			return numeric;
		} else {
			String trim = target.substring(1);
			for (int a = 0; a < trim.length(); ++a) 
				numeric += Character.getNumericValue(trim.charAt(a)) * (Math.pow(10, trim.length() - 1 - a));
			return -1 * numeric;
		}
	}
	
	public boolean String2Bool(String target) {
		if (target.equals("false"))
			return false;
		else
			return true;
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

