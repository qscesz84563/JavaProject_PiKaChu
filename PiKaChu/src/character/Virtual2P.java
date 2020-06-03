package character;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JFrame;

import volleyball.Game;

public class Virtual2P extends Player{
	private Game game;

	protected Dictionary<String, Integer> feature_num = new Hashtable<String, Integer>();
	protected Dictionary<String, Boolean> feature_bool = new Hashtable<String, Boolean>();
	
	public Virtual2P(JFrame frame, Game game, int x, int y, int width, int height) {
		super(frame);
		this.game = game;

		feature_num.put("x", x);
		feature_num.put("y", y);
		feature_num.put("width", width);
		feature_num.put("height", height);
		feature_num.put("hitCount", 0);
		feature_num.put("moveSpeedX", 5);
		feature_num.put("moveSpeedY", 5);
		feature_num.put("score", 0);
		
		feature_bool.put("jumping", false);
		feature_bool.put("goingUp", false);
		feature_bool.put("goingDown", false);
		
		feature_bool.put("up_1P", false);
		feature_bool.put("up_2P", false);
		feature_bool.put("down_1P", false);
		feature_bool.put("down_2P", false);
		feature_bool.put("right_1P", false);
		feature_bool.put("right_2P", false);
		feature_bool.put("left_1P", false);
		feature_bool.put("left_2P", false);
		feature_bool.put("spike_1P", false);
		feature_bool.put("spike_2P", false);
		feature_bool.put("restart", false);
	}
	
	public void update() {
		// up_2P
		if(feature_bool.get("up_2P") & !feature_bool.get("jumping") & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")) {
			feature_bool.put("jumping", true);
			feature_bool.put("goingUp", true);
			feature_bool.put("goingDown", false);
		}
		
		// left_2P
		if(feature_bool.get("left_2P") & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")) {
			if(game.getKeyManager().dash_2P & !feature_bool.get("jumping")) {
				game.player2.setIcon(game.dash_left);
				game.player2.setSize(144, 100);
				feature_num.put("width", 144);
				feature_num.put("height", 100);
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
				feature_bool.put("dashing_left", true);
				feature_num.put("moveSpeedX", -10);
				feature_num.put("moveSpeedY", 2);
			}
			else {
				feature_num.put("moveSpeedX", -7);
				feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
				
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
		}
		
		// right_2P
		if(feature_bool.get("right_2P") & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")){
			if(game.getKeyManager().dash_2P & !feature_bool.get("jumping")) {
				game.player2.setIcon(game.dash_right);
				game.player2.setSize(144, 100);
				feature_num.put("width", 144);
				feature_num.put("height", 100);
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
				feature_bool.put("dashing_right", true);
				feature_num.put("moveSpeedX", 10);
				feature_num.put("moveSpeedY", 2);
			}
			else {
				feature_num.put("moveSpeedX", 7);
				feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
				
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
		}
		
		// jumping
		if(feature_bool.get("jumping")) {
			if(feature_bool.get("goingUp")){
				feature_num.put("moveSpeedY", -15);
				if(feature_num.get("y") <= 200) {
					feature_bool.put("goingUp", false);
					feature_bool.put("goingDown", true);
				}
			} else if(feature_bool.get("goingDown")){
				feature_num.put("moveSpeedY", 15);
				feature_bool.put("goingDown", false);
			}
			feature_num.put("y", feature_num.get("y") + feature_num.get("moveSpeedY"));
			feature_num.put("moveSpeedY", feature_num.get("moveSpeedY") + 1);
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			
			if(feature_num.get("y") + feature_num.get("height") >= game.height) {
				feature_bool.put("jumping", false);
				feature_bool.put("goingDown", false);
			}
		}
		else if(feature_bool.get("dashing_left")) {
			game.player2.setIcon(game.dash_left);
			game.player2.setSize(144, 100);
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			feature_num.put("y", feature_num.get("y") + feature_num.get("moveSpeedY"));
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		else if(feature_bool.get("dashing_right")) {
			game.player2.setIcon(game.dash_right);
			game.player2.setSize(144, 100);
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			feature_num.put("y", feature_num.get("y") + feature_num.get("moveSpeedY"));
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		
		//check out of border
		if(feature_num.get("x") > game.width - feature_num.get("width")) {
			feature_num.put("x", game.width - feature_num.get("width"));
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		
		if(feature_num.get("y") < 0) {
			feature_num.put("y", 0);
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		
		if(feature_num.get("y") > game.height - feature_num.get("height") - 30) {
			feature_num.put("width", 120);
			feature_num.put("height", 120);
			feature_num.put("y", game.height - feature_num.get("height") - 30);
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			game.player2.setIcon(game.player2_idle);
			game.player2.setSize(120, 120);
			feature_bool.put("dashing_left", false);
			feature_bool.put("dashing_right", false);
			feature_bool.put("jumping", false);
			feature_num.put("moveSpeedX", 0);
			feature_num.put("moveSpeedY", 0);
		}
		
		//check touch net 
		if(feature_num.get("x") < game.getNet().getLocation().x + game.getNet().getSize().width) {
			feature_num.put("x", game.getNet().getLocation().x + game.getNet().getSize().width);
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y") );
		}
	}
	
	public void setFeature(Dictionary<String, Integer> feature_num, Dictionary<String, Boolean> feature_bool) {
		this.feature_num = feature_num;
		this.feature_bool = feature_bool;
	}
	
	public Dictionary<String, Integer> getFeatNum() {
		return feature_num;
	}
	
	public Dictionary<String, Boolean> getFeatBool() {
		return feature_bool;
	}
	
	public void setHitCount(int hitCount) {
		this.feature_num.put("hitCount", hitCount);
	}
	
	public void setScore(int score) {
		this.feature_num.put("score", score);
	}
	
	public void setLocation(int x, int y) {
		this.feature_num.put("x", x);
		this.feature_num.put("y", y);
		game.player2.setLocation(x, y);
	}
	
	
	public int getHitCount() {
		return feature_num.get("hitCount");
	}


	public int getX() {
		return feature_num.get("x");
	}


	public int getY() {
		return feature_num.get("y");
	}


	public int getWidth() {
		return feature_num.get("width");
	}


	public int getHeight() {
		return feature_num.get("height");
	}
	
	public int getScore() {
		return feature_num.get("score");
	}
	
}
