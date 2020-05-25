package character;

import java.awt.Color;
import java.awt.Graphics;

import windows.Game2PMode;

public class Ball {
	
	private Game2PMode game;
	private int x, y, width, height;
	private int moveSpeedX = 0, moveSpeedY = 5;
	
	public Ball(Game2PMode game, int x, int y, int width, int height) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {		
		if(game.getKeyManger().restart) {
			x = y = 0;
			moveSpeedX = 0;
			moveSpeedY = 5;
		}
		//left side of net
		if((x + width > game.getNet().x) & (x + width <= game.getNet().x + game.getNet().width) & moveSpeedX > 0) {
			if(y > game.getNet().y - height) {
				x = game.getNet().x - width;
				moveSpeedX = -5;
			}
			else if (y == game.getNet().y - height){
				y = game.getNet().y - height;
				moveSpeedY = -5;
			}
		}
		//right side of net
		else if((x < game.getNet().x + game.getNet().width) & (x >= game.getNet().x) & moveSpeedX < 0) {
			if(y > game.getNet().y - height) {
				x = game.getNet().x + game.getNet().width;
				moveSpeedX = 5;
			}
			else if (y == game.getNet().y - height){
				y = game.getNet().y - height;
				moveSpeedY = -5;
			}
		}
		//check out of border
		if(x < 0) {
			moveSpeedX = 5;
		}
		else if(x + width > game.width) {
			moveSpeedX = -5;
		}
		else if(y < 0) {
			moveSpeedY = 5;
		}
		else if(y + width > game.getLanding().y) {
			moveSpeedY = -10;
		}
		
		check_collision_with_player1();
		check_collision_with_player2(); 
		
		x += moveSpeedX;
		y += moveSpeedY;
		moveSpeedY += 1;
	}
	
	public void check_collision_with_player1() {
		int px = game.getPlayer1P().getX();
		int py = game.getPlayer1P().getY();
		int pw = game.getPlayer1P().getWidth();
		int ph = game.getPlayer1P().getHeight();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < py + ph)) {
			if(game.getKeyManger().left_1P) {
				moveSpeedX = -15;
				moveSpeedY = -20;
			}else if(game.getKeyManger().right_1P) {
				moveSpeedX = 15;
				moveSpeedY = -20;
			}else {
				moveSpeedX = 0;
				moveSpeedY = -20;
			}
			if(game.getKeyManger().up_1P & game.getKeyManger().spike_1P) {
				moveSpeedX = 30;
				moveSpeedY = 0;
			}
		}
	}
	
	public void check_collision_with_player2() {
		int px = game.getPlayer2P().getX();
		int py = game.getPlayer2P().getY();
		int pw = game.getPlayer2P().getWidth();
		int ph = game.getPlayer2P().getHeight();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < py + ph)) {
			if(game.getKeyManger().left_2P) {
				moveSpeedX = -15;
				moveSpeedY = -20;
			}else if(game.getKeyManger().right_2P) {
				moveSpeedX = 15;
				moveSpeedY = -20;
			}else {
				moveSpeedX = 0;
				moveSpeedY = -20;
			}
			if(game.getKeyManger().up_2P & game.getKeyManger().spike_2P) {
				moveSpeedX = -30;
				moveSpeedY = 0;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
	}
}
