package character;

import java.awt.Color;
import java.awt.Graphics;

import windows.Game2PMode;

public class Pikachu2P {
	
	private Game2PMode game;
	private int x, y, width, height;
	private static int moveSpeedX = 5, moveSpeedY = 5;
	private boolean jumping = false, goingUp = false, goingDown = false;

	
	public Pikachu2P(Game2PMode game, int x, int y, int width, int height) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		if(game.getKeyManger().up_2P & !jumping) {
			jumping = true;
			goingUp = true;
		}
//		if(game.getKeyManger().down_2P & !jumping) {
//			moveSpeedY = 5;
//			y += moveSpeedY;
//		}
		if(game.getKeyManger().left_2P) {
			moveSpeedX = -7;
			x += moveSpeedX;
		}
		if(game.getKeyManger().right_2P){
			moveSpeedX = 7;
			x += moveSpeedX;
		}
		if(jumping) {
			if(goingUp){
				moveSpeedY = -7;
				if(y <= 150) {
					goingUp = false;
					goingDown = true;
				}
			}else if(goingDown){
				moveSpeedY = 7;
			}
			y += moveSpeedY;
			if(y + height >= game.getLanding().y) {
				jumping = false;
				goingDown = false;
			}
		}
		
		//check out of border
		if(x > game.width - width)
			x = game.width - width;
		if(y < 0)
			y = 0;
		if(y > game.getLanding().y - height)
			y = game.getLanding().y - height;
		//check touch net
		if(x < game.getNet().x + game.getNet().width)
			x = game.getNet().x + game.getNet().width;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
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


	public static int getMoveSpeedX() {
		return moveSpeedX;
	}
}
