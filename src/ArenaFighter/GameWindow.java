package ArenaFighter;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class GameWindow extends Canvas implements Observer{
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> gameObjects;
	
	public void render(){
		//BufferStrategy bs = this.getBufferStrategy();
		////if(bs == null){
		//	createBufferStrategy(3);
		//}
		//Graphics g = bs.getDrawGraphics();
		///// Paint method
		for(Entity e : gameObjects){
			//g.drawImage(e.getSprite(), e.getX(),e.getY(), null);
			//Graphics2D g2d = (Graphics2D)g;
			//g2d.rotate(Math.toRadians(e.getRotVar()));
			Graphics g = getGraphics();
			g.fillOval(e.getX(), e.getY(), 50, 50); // To test
		}
		////
		//bs.show();
		//g.dispose();
	}
	
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState){
			GameState state = (GameState)arg0;
			gameObjects=state.getList();
			render();
		}
	}
}
