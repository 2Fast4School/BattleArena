package view;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import controller.GameInput;
import model.Entity;
import model.GameState;
import model.Tile;
import model.Unit;
import model.Weapon;

public class GameWindow extends Canvas implements Observer{
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> gameObjects;
	private BufferedImage img = null;
	private GameInput gameInput;
	
	//Kanske ta bort
	public GameWindow(GameInput gameInput, BufferedImage img){
		this.img = img;
		this.gameInput = gameInput;
		addKeyListener(gameInput);
		addMouseListener(gameInput);
		addMouseMotionListener(gameInput);
	}

	
	/**
	 * The actual drawing of the current GameState
	 * using triple-buffering.
	 * Checks all entities and draws them.
	 */
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			//Triple-buffer
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		///// Paint method
		if(img != null){
			g2d.drawImage(img, null, 0, 0);
			
		}else{
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, 800, 800);
		}
		for(Entity e : gameObjects){

			if(e instanceof Unit){
				
				//Draw a unit
				g2d.rotate(Math.toRadians(e.getRotVar()), e.getCenterX(), e.getCenterY());
				g2d.drawImage(e.getSprite(), e.getX(), e.getY(), null);
				g2d.rotate(Math.toRadians(-e.getRotVar()), e.getCenterX(), e.getCenterY());
				
				Unit unit = (Unit)e;
				Stroke oldStroke = g2d.getStroke();
				g2d.setStroke(new BasicStroke(1));
				
				g2d.setColor(Color.BLACK);
				g2d.drawRect(e.getX(), e.getY()+49, 40, 8);
				g2d.setStroke(oldStroke);
				
				g2d.setColor(Color.GREEN);
				g2d.fillRect(e.getX()+1, e.getY()+50, (39*unit.getHP()/100), 7);
				
				g2d.setColor(Color.black);			
				g2d.fillRect(e.getX() + (39*unit.getHP()/100) + 1, e.getY() + 50, 1, 7);
			} 
			
			//If e is a weapon and e is attacking, draw the weapon.
			if(e instanceof Weapon && ((Weapon) e).isAttacking()){
				
				g2d.rotate(Math.toRadians(e.getRotVar()), e.getX(), e.getY());
				g2d.drawImage(e.getSprite(), e.getX(), e.getY(), null);
				g2d.rotate(Math.toRadians(-e.getRotVar()), e.getX(), e.getY());
				
				//The weapons hitbox.
				g2d.setColor(Color.RED);
				g2d.fill(e.getBounds());
			}
			
			
			//Draw the healthbar if player/enemy
			if(e instanceof Tile){
				g2d.drawImage(e.getSprite(), e.getX(), e.getY(), null);
				
			}
			/*if(e instanceof mapObject){
				g2d.setColor(Color.GREEN);
				g2d.fillRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
				
			}*/
		}
		////
		g2d.dispose();
		g.dispose();
		bs.show();
	}
	/**
	 * GameWindow is an observer to GameState.
	 * GameState notifies GameWindow that it needs to render.
	 * @param arg0 the object that sent the notification (observable object)
	 * @param arg1 notification can be sent with an argument, which ends up in arg1
	 * 
	 */
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState){
			GameState state = (GameState)arg0;
			gameObjects = state.getList();
			img = state.getBackground();
			render();
		}
	}
}