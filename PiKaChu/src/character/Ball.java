package character;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import volleyball.Game;
import volleyball.Game2PMode;
import javax.swing.*;

public class Ball {
	
	private JFrame frame;
	private Game game;
	private int x, y, width, height;
	private int moveSpeedX = 0, moveSpeedY = 5, speedY_decrease = 1;
	private final int vHitRight = 15, vHitLeft = -15, vHitUp = -20,
					  energy_increase = 20;
	
	public Ball(JFrame frame, Game game, int x, int y, int width, int height) {
		
		this.frame = frame;
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	public void update() {	
		//restart
		if(game.getKeyManager().restart) {
			
			x = y = 100;
			moveSpeedX = 0;
			moveSpeedY = 5;
			speedY_decrease = 1;
			game.getPlayer1P().setHitCount(0);
			game.getPlayer2P().setHitCount(0);
			game.getPlayer1P().setScore(0);
			game.getPlayer2P().setScore(0);
			game.getPlayer1P().setLocation(70, 350);
			game.getPlayer2P().setLocation(810, 350);
		}
		//check hit net
		check_collision_with_net();
		//check out of border
		check_collision_with_border();
		//check collision with player
		check_collision_with_player1();
		check_collision_with_player2(); 
		
		x += moveSpeedX;
		y += moveSpeedY;
		game.ball.setLocation(x, y);
		moveSpeedY += speedY_decrease;
	}
	
	
	
	public void check_collision_with_border() {
		//ball going right
		if(moveSpeedX > 0) {
			if(x + width + moveSpeedX > game.width) {
				x = game.width - width;
				moveSpeedX = -5;
				speedY_decrease = 1;
			}
		}
		else if(moveSpeedX < 0) {
			if(x + moveSpeedX < 0) {
				x = 0;
				moveSpeedX = 5;
				speedY_decrease = 1;
			}
		}
		
		if(y + moveSpeedY < 0 & moveSpeedY < 0) {
			y = 0;
			moveSpeedY = 5;
			speedY_decrease = 1;
		}
		else if(y + height > 500 - 30) {
			//2P win
			if(x < game.getNet().getLocation().x) {
				x = 900 - width;
				y = 100;
				moveSpeedX = 0;
				moveSpeedY = 5;
				speedY_decrease = 1;
				game.getPlayer1P().setHitCount(0);
				game.getPlayer2P().setHitCount(0);
				game.getPlayer2P().setScore(game.getPlayer2P().getScore() + 1);
				game.getPlayer1P().setLocation(70, 350);
				game.getPlayer2P().setLocation(810, 350);
			//1P win
			}else {
				x = y = 100;
				moveSpeedX = 0;
				moveSpeedY = 5;
				speedY_decrease = 1;
				game.getPlayer1P().setHitCount(0);
				game.getPlayer2P().setHitCount(0);
				game.getPlayer1P().setScore(game.getPlayer1P().getScore() + 1);
				game.getPlayer1P().setLocation(70, 350);
				game.getPlayer2P().setLocation(810, 350);
			}
		}
	}
	
	public void check_collision_with_net() {
		int nx = game.getNet().getLocation().x;
		int ny = game.getNet().getLocation().y;
		int nw = game.getNet().getSize().width;
		int nh = game.getNet().getSize().height;
		
		if(moveSpeedX > 0) {
			if((x + width + moveSpeedX > nx) & (x + width + moveSpeedX < nx + nw + width)) {
				if(y + height / 2 + moveSpeedY > ny) {
					x = nx - width;
					moveSpeedX = -5;
					speedY_decrease = 1;
				}
			}
		}
		else if(moveSpeedX < 0){
			if((x + moveSpeedX < nx + nw) & (x + moveSpeedX > nx - width)) {
				if(y + height / 2 + moveSpeedY > ny) {
					x = nx + nw;
					moveSpeedX = 5;
					speedY_decrease = 1;
				}
			}
		}
	}
	
	public void check_collision_with_player1() {
		int px = game.getPlayer1P().getX();
		int py = game.getPlayer1P().getY();
		int pw = game.getPlayer1P().getWidth();
		int hc = game.getPlayer1P().getHitCount();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < 500 - height)) {
			game.getPlayer1P().setHitCount(hc + 1);
			if(game.getKeyManager().left_1P) {
				moveSpeedX = vHitLeft;
				moveSpeedY = vHitUp;
				speedY_decrease = 1;
			}else if(game.getKeyManager().right_1P) {
				moveSpeedX = vHitRight;
				moveSpeedY = vHitUp;
				speedY_decrease = 1;

			}else {
				moveSpeedX = 0;
				moveSpeedY = vHitUp;
				speedY_decrease = 1;
			}
			if(game.getKeyManager().up_1P & game.getKeyManager().spike_1P) {
				moveSpeedX = game.getPlayer1P().getHitCount() * energy_increase;
				moveSpeedY = -10;
				speedY_decrease = game.getPlayer1P().getHitCount();
				game.getPlayer1P().setHitCount(0);
			}
		}
	}
	
	public void check_collision_with_player2() {
		int px = game.getPlayer2P().getX();
		int py = game.getPlayer2P().getY();
		int pw = game.getPlayer2P().getWidth();
		int hc = game.getPlayer2P().getHitCount();
		
		if((x > px - width) & (x + width < px + pw + width) & (y > py - height) & (y < 500 - height)) {
			game.getPlayer2P().setHitCount(hc + 1);
			if(game.getKeyManager().left_2P) {
				moveSpeedX = vHitLeft;
				moveSpeedY = vHitUp;
				speedY_decrease = 1;
			}else if(game.getKeyManager().right_2P) {
				moveSpeedX = vHitRight;
				moveSpeedY = vHitUp;
			}else {
				moveSpeedX = 0;
				moveSpeedY = vHitUp;
				speedY_decrease = 1;
			}
			if(game.getKeyManager().up_2P & game.getKeyManager().spike_2P) {
				moveSpeedX = game.getPlayer2P().getHitCount() * energy_increase * (-1);
				moveSpeedY = -10;
				speedY_decrease = game.getPlayer2P().getHitCount();
				game.getPlayer2P().setHitCount(0);
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
