import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, MouseMotionListener{
	private Player p;
	private final int VELOCITY = 2;

	public Input(Player p){
		this.p = p;
	}

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
		int deltax, deltay;
		deltax = p.getX()-e.getX();
		deltay = p.getY()-e.getY();
		
		if(deltax == 0){
			if(deltay < 0){
				p.setRotVar(180);
			} else {
				p.setRotVar(0);
			}
		}
		
		if(deltay == 0){
			if(deltax < 0){
				p.setRotVar(270);
			} else {
				p.setRotVar(90);
			}
		}
		
		if(deltax > 0 && deltay > 0){
			p.setRotVar((int)(90-Math.atan(deltax/deltay)));
		}
		if(deltax > 0 && deltay < 0){
			p.setRotVar((int)(90+Math.atan(deltax/deltay)));
		}
		if(deltax < 0 && deltay < 0){
			p.setRotVar((int)(180+Math.atan(deltay/deltax)));
		}
		if(deltax < 0 && deltay > 0){
			p.setRotVar((int)(270+Math.atan(deltax/deltay)));
		}
		
	}

}