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

import model.Enemy;
import model.Entity;
import model.GameState;
import model.Player;

public class GameWindow extends Canvas implements Observer{
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> gameObjects;
	private int attackAnimationStage=0;
	
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
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 800, 800);
		for(Entity e : gameObjects){
			//Graphics 2D object used for rotating sprites.
			if(e instanceof Player || e instanceof Enemy){
				//Draw an attack
					if(e.getAttacking()){
						attackAnimationStage++;
						double rot=0;
						if(attackAnimationStage<6){rot=(Math.PI*2)+Math.PI/8;}
						else if(attackAnimationStage>=6 && attackAnimationStage<12){rot=(Math.PI*2)+Math.PI/4;}
						else if(attackAnimationStage>=12 && attackAnimationStage<18){rot=(Math.PI*2)+Math.PI/2;}
						else if(attackAnimationStage>=18){
							rot=(Math.PI*2)+Math.PI/2;
							e.setAttacking(false);
							attackAnimationStage=0;
						}
						g2d.rotate(rot, e.getCenterX(), e.getCenterY());
						g2d.setColor(Color.BLACK);
						g2d.fillRect(e.getX()+10, e.getY()+10, e.getWeapon().getWidth(), e.getWeapon().getLength());
						g2d.rotate(-rot, e.getCenterX(), e.getCenterY());
					}
				//Draw the objects visual appearance.
					g2d.rotate(Math.toRadians(e.getRotVar()), e.getCenterX(), e.getCenterY());
					g2d.drawImage(e.getSprite(), e.getX(), e.getY(), null);
					g2d.rotate(Math.toRadians(-e.getRotVar()), e.getCenterX(), e.getCenterY());
			}
				g2d.rotate(Math.toRadians(e.getRotVar()), e.getCenterX(), e.getCenterY());
				g2d.drawImage(e.getSprite(), e.getX(), e.getY(), null);
				g2d.rotate(Math.toRadians(-e.getRotVar()), e.getCenterX(), e.getCenterY());
			
			//Draw the objects "hitbox"
			g2d.setColor(Color.RED);
			g2d.drawRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
			//Draw the healthbar if player/enemy
			if(e instanceof Player || e instanceof Enemy){
				Stroke oldStroke = g2d.getStroke();
				g2d.setStroke(new BasicStroke(4));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(e.getX(), e.getY()-20, 60, 20);
				g2d.setStroke(oldStroke);
				g2d.setColor(Color.RED);
				g2d.fillRect(e.getX()+2, e.getY()-18, (56*e.getHP()/100), 16);
			}
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
			render();
		}
	}
}