package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

	private boolean[] keys;
	public boolean up, down, left, right,escape; 
	
	
	
	
	public KeyManager() {
		keys = new boolean[256];
	}
	public void tick() {
		up = keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN]|| keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT]|| keys[KeyEvent.VK_D];
		
		escape = keys[KeyEvent.VK_ESCAPE];
		

		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		System.out.println("pressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public boolean[] getKeys() {
		return keys;
	}
	public void setKeys(boolean[] keys) {
		this.keys = keys;
	}

}