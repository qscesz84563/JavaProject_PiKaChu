package character;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import volleyball.Game2PMode;
import javax.swing.*;

public class Ball {
	private JFrame frame;
	private Game2PMode game;
	private int x, y, width, height;
	private int moveSpeedX = 0, moveSpeedY = 5;
	private boolean spiking = false;
	private final int vHitRight = 15, vHitLeft = -15, vHitUp = -20,
					  vSpikeRight = 30, vSpikeLeft = -30, vMaxRight = 100, vMaxLeft = -100;
	
	public Ball(JFrame frame, Game2PMode game, int x, int y, int width, int height) {
		this.frame = frame;
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
			game.getPlayer1P().setHitCount(0);
			game.getPlayer2P().setHitCount(0);
			spiking = false;
			
		}
		//left side of net
		if((x + width > game.getNet().getLocation().x) & (x + width <= game.getNet().getLocation().x + game.getNet().getSize().width) & moveSpeedX > 0) {
			if(y > game.getNet().getLocation().y - height) {
				x = game.getNet().getLocation().x - width;
				moveSpeedX = -5;
				game.ball.setLocation(x, y);
			}
			//top of net, a lil bit rebounce
			else if (y == game.getNet().getLocation().y - height){
				y = game.getNet().getLocation().y - height;
				moveSpeedY = -5;
				game.ball.setLocation(x, y);
			}
		}
		//right side of net 
		else if((x < game.getNet().getLocation().x + game.getNet().getSize().width) & (x >= game.getNet().getLocation().x) & moveSpeedX < 0) {
			if(y > game.getNet().getLocation().y - height) {
				x = game.getNet().getLocation().x + game.getNet().getSize().width;
				moveSpeedX = 5;
				game.ball.setLocation(x, y);
			}
			//top of net, a lil bit rebounce
			else if (y == game.getNet().getLocation().y - height){
				y = game.getNet().getLocation().y - height;
				moveSpeedY = -5;
				game.ball.setLocation(x, y);
			}
		}
		//check out of border
		if(x < 0 | (x - 100 < 0 & spiking & moveSpeedX < 0)) {
			x = 0;
			game.ball.setLocation(x, y);
			moveSpeedX = 5;
			spiking = false;
		}
		else if(x + width > game.width | (x + 100 > game.width & spiking & moveSpeedX > 0)) {
			x = game.width - width;
			game.ball.setLocation(x, y);
			moveSpeedX = -5;
			spiking = false;
		}
		else if(y < 0) {
			y = 0;
			game.ball.setLocation(x, y);
			moveSpeedY = 5;
		}
		else if(y + height + 30 > 500) {
			y = 500 - height -30;
			game.ball.setLocation(x, y);
			moveSpeedY = -3;
		}
		
		check_collision_with_player1();
		check_collision_with_player2(); 
		
//		if(x + 100 > game.width & spiking & moveSpeedX > 0) {
//			x = game.width - width;
//			moveSpeedX = -5;
//			spiking = false;
//		}
//		else if(x - 100 < 0 & spiking & moveSpeedX < 0) {
//			x = 0;
//			moveSpeedX = 5;
//			spiking = false;
//		}
		
		x += moveSpeedX;
		y += moveSpeedY;
		game.ball.setLocation(x, y);
		moveSpeedY += 1;
	}
	
	public void check_collision_with_player1() {
		int px = game.getPlayer1P().getX();
		int py = game.getPlayer1P().getY();
		int pw = game.getPlayer1P().getWidth();
//		int ph = game.getPlayer1P().getHeight();
		int hc = game.getPlayer1P().getHitCount();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < 500 - height)) {
			game.getPlayer1P().setHitCount(hc + 1);
			spiking = false;
			if(game.getKeyManger().left_1P) {
				moveSpeedX = vHitLeft;
				moveSpeedY = vHitUp;
			}else if(game.getKeyManger().right_1P) {
				moveSpeedX = vHitRight;
				moveSpeedY = vHitUp;
			}else {
				moveSpeedX = 0;
				moveSpeedY = vHitUp;
			}
			if(game.getKeyManger().up_1P & game.getKeyManger().spike_1P) {
				if(game.getPlayer1P().getHitCount() > 5) {
					moveSpeedX = vMaxRight;
					moveSpeedY = 10;
					spiking = true;
				}
				else {
					moveSpeedX = vSpikeRight;
					moveSpeedY = 0;
				}
				game.getPlayer1P().setHitCount(0);
			}
		}
	}
	
	public void check_collision_with_player2() {
		int px = game.getPlayer2P().getX();
		int py = game.getPlayer2P().getY();
		int pw = game.getPlayer2P().getWidth();
//		int ph = game.getPlayer2P().getHeight();
		int hc = game.getPlayer2P().getHitCount();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < 500 - height)) {
			game.getPlayer2P().setHitCount(hc + 1);
			spiking = false;
			if(game.getKeyManger().left_2P) {
				moveSpeedX = vHitLeft;
				moveSpeedY = vHitUp;
			}else if(game.getKeyManger().right_2P) {
				moveSpeedX = vHitRight;
				moveSpeedY = vHitUp;
			}else {
				moveSpeedX = 0;
				moveSpeedY = vHitUp;
			}
			if(game.getKeyManger().up_2P & game.getKeyManger().spike_2P) {
				if(game.getPlayer2P().getHitCount() > 5) {
					moveSpeedX = vMaxLeft;
					moveSpeedY = 10;
					spiking = true;
				}
				else {
					moveSpeedX = vSpikeLeft;
					moveSpeedY = 0;
				}
				game.getPlayer2P().setHitCount(0);
			}
		}
	}
}
