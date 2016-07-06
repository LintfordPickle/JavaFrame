package test;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener, FocusListener {

	private boolean [] keys = new boolean[256];
	
	
	public void handleInput(){
		
	}
	
	public boolean isKeyDown(int k) {return keys[k];}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() > 0 && e.getKeyCode() < keys.length)
			keys[e.getKeyCode()] = true;
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() > 0 && e.getKeyCode() < keys.length)
			keys[e.getKeyCode()] = false;
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
