package ArenaFighter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, MouseMotionListener{
	private Player p;
	private final int VELOCITY = 1;

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

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent e) {
		int angle = (int)Math.toDegrees(Math.atan2(e.getY() - p.getCenterY(), e.getX() - p.getCenterX()));
		angle += 90;
		
		if(angle < 0){
			angle += 360;
		}
		
		p.setRotVar(angle);
	}

}