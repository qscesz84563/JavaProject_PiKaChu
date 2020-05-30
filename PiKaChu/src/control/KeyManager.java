package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean keys[];
	public boolean up_1P, up_2P, down_1P, down_2P, right_1P, right_2P, left_1P, left_2P, spike_1P, spike_2P, restart; 
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void update() {
		up_1P = keys[KeyEvent.VK_W];
		down_1P = keys[KeyEvent.VK_S];
		left_1P = keys[KeyEvent.VK_A];
		right_1P = keys[KeyEvent.VK_D];
		spike_1P = keys[KeyEvent.VK_SPACE];
		
		up_2P = keys[KeyEvent.VK_UP];
		down_2P = keys[KeyEvent.VK_DOWN];
		left_2P = keys[KeyEvent.VK_LEFT];
		right_2P = keys[KeyEvent.VK_RIGHT];
		spike_2P = keys[KeyEvent.VK_SHIFT];
		
		restart = keys[KeyEvent.VK_R];
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;

	}

}