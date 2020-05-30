package character;

import java.awt.Color;
import java.awt.Graphics;

import volleyball.Game2PMode;
import javax.swing.*;

public class Pikachu1P {
	
	private JFrame frame;
	private Game2PMode game;
	private int x, y, width, height, hitCount = 0;
	private int moveSpeedX = 5, moveSpeedY = 5;
	private boolean jumping = false, goingUp = false, goingDown = false;
	
	
	public Pikachu1P(JFrame frame, Game2PMode game,int x, int y, int width, int height) {
		this.frame = frame;
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		if(game.getKeyManger().up_1P & !jumping) {
			jumping = true;
			goingUp = true;
			goingDown = false;
			moveSpeedY = -30;
		}
//		if(game.getKeyManger().down_1P & !jumping) {
//			moveSpeedY = 5;
//			y += moveSpeedY;
//		}
		if(game.getKeyManger().left_1P) {
			moveSpeedX = -7;
			x += moveSpeedX;
			game.player1.setLocation(x, y);
		}
		if(game.getKeyManger().right_1P){
			moveSpeedX = 7;
			x += moveSpeedX;
			game.player1.setLocation(x, y);
		}
		if(jumping) {
			if(goingUp){
				moveSpeedY++;
				if(y <= 150) {
					goingUp = false;
					goingDown = true;
				}
			}else if(goingDown){
				moveSpeedY = 10;
				goingDown = false;
			}
			y += moveSpeedY;
			moveSpeedY++;
			game.player1.setLocation(x, y);
			if(y + height >= game.height) {
				jumping = false;
				goingDown = false;
			}
		}
		
		//check out of border
		if(x < 0) {
			x = 0;
			game.player1.setLocation(x, y);
		}
			
		if(y < 0) {
			y = 0;
			game.player1.setLocation(x, y);
		}
			
		if(y > game.height - height -30) {
			y = game.height - height -30;
			game.player1.setLocation(x, y);
		}
			
		//check touch net 
		if(x + width > game.getNet().getLocation().x) {
			x = game.getNet().getLocation().x - width;
			game.player1.setLocation(x, y);
		}
			
	}
	
	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getX() {
		return x;
	}


	public int getY() {
		return y;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}

}
