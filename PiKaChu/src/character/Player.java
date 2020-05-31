package character;

import java.util.Dictionary;

import javax.swing.JFrame;

import volleyball.Game2PMode;

public abstract class Player {
	protected JFrame frame;
//	protected Game2PMode game;
	
	public Player(JFrame frame) {
		this.frame = frame;
//		this.game = game;
	}
	
	
	public abstract void update();
	
	public abstract Dictionary<String, Integer> getFeatNum();
	public abstract Dictionary<String, Boolean> getFeatBool();
	
	public abstract void setHitCount(int hitCount);
	public abstract int getHitCount();
	public abstract int getX();
	public abstract int getY();
	public abstract int getWidth();
	public abstract int getHeight();
}
