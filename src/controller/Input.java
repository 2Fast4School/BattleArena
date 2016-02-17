package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.Player;

/**
 * <h1>Input</h1>
 * Input is the class that handles all the input in this game.
 * It implements the interfaces KeyListener and MouseMotionListener.
 * 
 * Since the input class changes the status of a player, a player model is needed.
 * Initialize the player with the setup method.
 * 
 */
public class Input implements KeyListener, MouseMotionListener{
	private Player p;
	private final int VELOCITY = 1;

	/**
	 * Initiliazes the Input object with the specified Player object.
	 * @param Player p The player object that the Class will manipulate.
	 */
	public void setup(Player p){this.p = p;}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			p.setdy(-VELOCITY);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A){
			p.setdx(-VELOCITY);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S){
			p.setdy(VELOCITY);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D){
			p.setdx(VELOCITY);
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
		
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			p.setdy(0);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A){
			p.setdx(0);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S){
			p.setdy(0);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D){
			p.setdx(0);
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		//Calculates the angle between the mouse-pointer and the player's central coordinates.
		int angle = (int)Math.toDegrees(Math.atan2(e.getY() - p.getCenterY(), e.getX() - p.getCenterX()));
		
		/*
		 * We need to add 90 degrees because for us, 0 degrees angle is straight up,
		 * but we calculate it as if it were to the right. Think about the unit circle, (SE Enhetscirkeln).
		 */
		angle += 90;
		
		//Instead of using negative angles, we just add 360 degrees to get the positive value of the same angle.
		if(angle < 0){
			angle += 360;
		}
		
		p.setRotVar(angle);
	}

}