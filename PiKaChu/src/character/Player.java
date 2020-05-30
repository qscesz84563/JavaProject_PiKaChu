package character;

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
	
	
}
