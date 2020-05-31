package character;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Dictionary;
import java.util.Hashtable;

import volleyball.*;
import javax.swing.*;

public class Pikachu2P extends Player{
	
	private Game game;

	protected Dictionary<String, Integer> feature_num = new Hashtable<String, Integer>();
	protected Dictionary<String, Boolean> feature_bool = new Hashtable<String, Boolean>();
	
	public Pikachu2P(JFrame frame, Game game, int x, int y, int width, int height) {
		super(frame);
		this.game = game;

		feature_num.put("x", x);
		feature_num.put("y", y);
		feature_num.put("width", width);
		feature_num.put("height", height);
		feature_num.put("hitCount", 0);
		feature_num.put("moveSpeedX", 5);
		feature_num.put("moveSpeedY", 5);
		
		feature_bool.put("jumping", false);
		feature_bool.put("goingUp", false);
		feature_bool.put("goingDown", false);
	}
	public void update() {
		if(game.getKeyManager().up_2P & !feature_bool.get("jumping")) {
			feature_bool.put("jumping", true);
			feature_bool.put("goingUp", true);
			feature_bool.put("goingDown", false);
		}
		if(game.getKeyManager().left_2P) {
			feature_num.put("moveSpeedX", -7);
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
		if(game.getKeyManager().right_2P){
			feature_num.put("moveSpeedX", 7);
			feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
			
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
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
			feature_num.put("y", game.height -feature_num.get("height") - 30);
			game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
		}
			
		//check touch net 
		if(feature_num.get("x") < game.getNet().getLocation().x + game.getNet().getSize().width) {
			feature_num.put("x", game.getNet().getLocation().x + game.getNet().getSize().width);
			game.player2.setLocation(game.getNet().getX() + game.getNet().getWidth(), feature_num.get("y") );
		}
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
}
