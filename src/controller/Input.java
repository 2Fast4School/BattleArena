package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class Input implements KeyListener, MouseListener, MouseMotionListener{
	private Player p;
	private final int VELOCITY = 2;

	/**
	 * Initiliazes the Input object with the specified Player object.
	 * @param Player p The player object that the Class will manipulate.
	 */
	public void setup(Player p){this.p = p;}
	
	/**
	 * keyPressed will set the player velocity, listening to the WASD keys.
	 * The player's facing direction will also be set to match this.
	 * @param e : KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		if(p.isAlive()){
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
			
			//Force shut down the game.
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
				System.exit(1);
			}
		}
	}

	/**
	 * keyReleased will set the player's x or y velocity to 0, depending on which of the WASD keys is released.
	 */
	public void keyReleased(KeyEvent e) {
		if(p.isAlive()){
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
	}
	
	//Unused
	public void keyTyped(KeyEvent e) {}

	//Unused
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		if(p.isAlive()){
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

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(p.isAlive()){
			if(arg0.getButton()==MouseEvent.BUTTON1){
				p.doAttack();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
				
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
				
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
				
	}

}