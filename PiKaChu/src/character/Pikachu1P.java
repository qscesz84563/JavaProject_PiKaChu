package character;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Dictionary;
import java.util.Hashtable;

import volleyball.Game;
import volleyball.Game2PMode;
import javax.swing.*;

public class Pikachu1P extends Player{
	
	private Game game;
	
	public Dictionary<String, Integer> feature_num = new Hashtable<String, Integer>();
	public Dictionary<String, Boolean> feature_bool = new Hashtable<String, Boolean>();
	
	public Pikachu1P(JFrame frame, Game game,int x, int y, int width, int height) {
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
		feature_bool.put("dashing_left", false);
		feature_bool.put("dashing_right", false);

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
		//up_1P, can't jump when dashing
		if(game.getKeyManager().up_1P & !feature_bool.get("jumping") & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")) {
			feature_bool.put("jumping", true);
			feature_bool.put("goingUp", true);
			feature_bool.put("goingDown", false);
		}

		//left_1P, can't move when dashing
		if(game.getKeyManager().left_1P & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")) {
			if(game.getKeyManager().dash_1P & !feature_bool.get("jumping")) {
				game.player1.setIcon(game.dash_left);
				game.player1.setSize(144, 100);
				feature_num.put("width", 144);
				feature_num.put("height", 100);
				game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
				feature_bool.put("dashing_left", true);
				feature_num.put("moveSpeedX", -10);
				feature_num.put("moveSpeedY", 2);
			}
			else {
				feature_num.put("moveSpeedX", -7);
				feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
				
				game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
		}

		// right_1P, can't move when dashing
		if(game.getKeyManager().right_1P & !feature_bool.get("dashing_left") & !feature_bool.get("dashing_right")){
			if(game.getKeyManager().dash_1P & !feature_bool.get("jumping")) {
				game.player1.setIcon(game.dash_right);
				game.player1.setSize(144, 100);
				feature_num.put("width", 144);
				feature_num.put("height", 100);
				game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
				feature_bool.put("dashing_right", true);
				feature_num.put("moveSpeedX", 10);
				feature_num.put("moveSpeedY", 2);
			}
			else {
				feature_num.put("moveSpeedX", 7);
				feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
				
				game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
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
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
			
			if(feature_num.get("y") + feature_num.get("height") >= game.height) {
				feature_bool.put("jumping", false);
				feature_bool.put("goingDown", false);
			}
		}

		//dashing left
		else if(feature_bool.get("dashing_left")) {
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			feature_num.put("y", feature_num.get("y") + feature_num.get("moveSpeedY"));
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		else if(feature_bool.get("dashing_right")) {
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			feature_num.put("y", feature_num.get("y") + feature_num.get("moveSpeedY"));
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		
		//check out of border
		if(feature_num.get("x") < 0) {
			feature_num.put("x", 0);
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
			
		if(feature_num.get("y") < 0) {
			feature_num.put("y", 0);
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
			
		if(feature_num.get("y") > game.height - feature_num.get("height") -30) {
			feature_num.put("width", 120);
			feature_num.put("height", 120);
			feature_num.put("y", game.height - feature_num.get("height") - 30);
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
			game.player1.setIcon(game.player1_idle);
			game.player1.setSize(120, 120);
			feature_bool.put("dashing_left", false);
			feature_bool.put("dashing_right", false);
			feature_bool.put("jumping", false);
			feature_num.put("moveSpeedX", 0);
			feature_num.put("moveSpeedY", 0);
		}
			
		//check touch net 
		if(feature_num.get("x") + feature_num.get("width") > game.getNet().getLocation().x) {
			feature_num.put("x", game.getNet().getLocation().x - feature_num.get("width"));
			game.player1.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		
		score_update();
		
		updateKeyManager();
	}
	
	public void updateKeyManager() {
		feature_bool.put("up_1P",    game.getKeyManager().up_1P);
		feature_bool.put("up_2P",    game.getKeyManager().up_2P);
		feature_bool.put("down_1P",  game.getKeyManager().down_1P);
		feature_bool.put("down_2P",  game.getKeyManager().down_2P);
		feature_bool.put("right_1P", game.getKeyManager().right_1P);
		feature_bool.put("right_2P", game.getKeyManager().right_2P);
		feature_bool.put("left_1P",  game.getKeyManager().left_1P);
		feature_bool.put("left_2P",  game.getKeyManager().left_2P);
		feature_bool.put("spike_1P", game.getKeyManager().spike_1P);
		feature_bool.put("spike_2P", game.getKeyManager().spike_2P);
		feature_bool.put("restart",  game.getKeyManager().restart);
	}
	
	public Dictionary<String, Integer> getFeatNum() {
		return feature_num;
	}
	
	public Dictionary<String, Boolean> getFeatBool() {
		return feature_bool;
	}
	
	public void setHitCount(int hitCount) {
		if(hitCount > 5) hitCount = 5;
		this.feature_num.put("hitCount", hitCount);
	}
	
	public void setScore(int score) {
		this.feature_num.put("score", score);
	}
	
	public void setLocation(int x, int y) {
		this.feature_num.put("x", x);
		this.feature_num.put("y", y);
		game.player1.setLocation(x, y);
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
	
	public void score_update() {
		if(feature_num.get("score") == 0) {
			for(int i = 0; i < 10; i++) {
				game.player1_score1[i].setVisible(false);
				game.player1_score2[i].setVisible(false);
			}
		}
		if(feature_num.get("score") > 0) {
			for(int  j = 0; j <= 9; j++) {
				if(feature_num.get("score") % 10 == j) {
					game.player1_score1[j].setVisible(true);
			        for(int i=0; i<10; i++){
			        	if(i!=j) 
			        		game.player1_score1[i].setVisible(false);
			        }
				}
			}
		}
		if(feature_num.get("score") >= 10) {
			for(int j = 0; j <= 90; j+=10) {
				if(feature_num.get("score") % 100 >= j && feature_num.get("score") % 100 < (j+10)) {
					game.player1_score2[j/10].setVisible(true);
			        for(int i=0; i<10; i++){
			        	if(i != (j/10)) 
			        		game.player1_score2[i].setVisible(false);
			        }
			    }
			}
		}
	}
	
}
