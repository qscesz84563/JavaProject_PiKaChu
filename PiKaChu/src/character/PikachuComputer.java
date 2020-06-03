package character;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JFrame;

import volleyball.Game;



public class PikachuComputer extends Player{		
		private Game game;

		public Dictionary<String, Integer> feature_num = new Hashtable<String, Integer>();
		public Dictionary<String, Boolean> feature_bool = new Hashtable<String, Boolean>();
		
		private  int previouBallX = 0;
		private  int previouBallY = 0;
		private  int speedBallX = 0;
		private  int speedBallY = 0;
		
		public PikachuComputer(JFrame frame, Game game, int x, int y, int width, int height) {
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
		}
		public void update() {
			speedBallX = game.getBallX() - previouBallX;
			speedBallY = game.getBallY() - previouBallY;
			
			if(game.getBallX() < 500) {
				game.getKeyManager().spike_com = false;
				if(feature_num.get("x") < 750) {
					game.getKeyManager().right_com=true;
					game.getKeyManager().left_com=false;
				}else if(feature_num.get("x") > 780) {
					game.getKeyManager().left_com=true;
					game.getKeyManager().right_com=false;
				}else {
					game.getKeyManager().left_com=false;
					game.getKeyManager().right_com=false;
				}
			}			
			else {
				if(feature_num.get("x") < game.getBallX() ) {
					game.getKeyManager().right_com=true;
					game.getKeyManager().left_com=false;
				}else if(feature_num.get("x") > game.getBallX() + 80) {
					game.getKeyManager().left_com=true;
					game.getKeyManager().right_com=false;
				}else {
					game.getKeyManager().up_com=true;
					game.getKeyManager().left_com=false;
					game.getKeyManager().right_com=false;
				}
				
				if( Math.abs(feature_num.get("x")-game.getBallX()) < 80 ) {
					double random = Math.random() ;
					if(random < 0.5) {
						game.getKeyManager().spike_com=true;	
					}else{	
						game.getKeyManager().spike_com=false;	
						game.getKeyManager().left_com=true;
					}
				}
				
			}


			 previouBallX = game.getBallX();
			 previouBallY = game.getBallY();			
			
			if(game.getKeyManager().up_com & !feature_bool.get("jumping")) {
				feature_bool.put("jumping", true);
				feature_bool.put("goingUp", true);
				feature_bool.put("goingDown", false);
			}
			if(game.getKeyManager().left_com) {
				feature_num.put("moveSpeedX", -7);
				feature_num.put("x", feature_num.get("x") + feature_num.get("moveSpeedX"));
				
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
			if(game.getKeyManager().right_com){
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
					game.getKeyManager().up_com = false;
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
				feature_num.put("y", game.height - feature_num.get("height") - 30);
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
				
			//check touch net 
			if(feature_num.get("x") < game.getNet().getLocation().x + game.getNet().getSize().width) {
				feature_num.put("x", game.getNet().getLocation().x + game.getNet().getSize().width);
				game.player2.setLocation(feature_num.get("x"), feature_num.get("y"));
			}
			
			score_update();
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
		@Override
		public Dictionary<String, Integer> getFeatNum() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Dictionary<String, Boolean> getFeatBool() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public int getScore() {
			return feature_num.get("score");
		}
		
		public void score_update() {
			if(feature_num.get("score") == 0) {
				for(int i = 0; i < 10; i++) {
					game.player2_score1[i].setVisible(false);
					game.player2_score2[i].setVisible(false);
				}
			}
			if(feature_num.get("score") > 0) {
				for(int  j = 0; j <= 9; j++) {
					if(feature_num.get("score") % 10 == j) {
						game.player2_score1[j].setVisible(true);
				        for(int i=0; i<10; i++){
				        	if(i!=j) 
				        		game.player2_score1[i].setVisible(false);
				        }
					}
				}
			}
			if(feature_num.get("score") >= 10) {
				for(int j = 0; j <= 90; j+=10) {
					if(feature_num.get("score") % 100 >= j && feature_num.get("score") % 100 < (j+10)) {
						game.player2_score2[j/10].setVisible(true);
				        for(int i=0; i<10; i++){
				        	if(i != (j/10)) 
				        		game.player2_score2[i].setVisible(false);
				        }
				    }
				}
			}
		}
	}
