import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class GameWindow extends Canvas implements Observer{
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> gameObjects;
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		///// Paint method
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 800, 800);
		for(Entity e : gameObjects){
			g.drawImage(e.getSprite(), e.getX(),e.getY(), null);
			g.setColor(Color.blue);
			g.fillRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
		}
		
		////
		g.dispose();
		bs.show();
	}
	
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState){
			GameState state = (GameState)arg0;
			gameObjects=state.getList();
			render();
		}
	}
}