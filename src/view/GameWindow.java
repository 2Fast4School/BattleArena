package view;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import model.Enemy;
import model.Entity;
import model.GameState;
import model.Player;

public class GameWindow extends Canvas implements Observer{
	private static final long serialVersionUID = 1L;
	private ArrayList<Entity> gameObjects;
	private int attackAnimationStage=0;
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
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
			
			//Draw the objects visual appearance.
			g2d.rotate(Math.toRadians(e.getRotVar()), e.getCenterX(), e.getCenterY());
			g2d.setColor(Color.blue);
			g2d.drawRect(e.getX(), e.getY(), e.getWidth(), e.getHeight());
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
			//Draw an attack
			if((e instanceof Player || e instanceof Enemy) && e.getAttacking()){
				double weaponRotation=0;
				
				attackAnimationStage++;
				if(attackAnimationStage<12){weaponRotation=((Math.PI*2)-(Math.PI/4));}
				else if(attackAnimationStage>=12 && attackAnimationStage<24){weaponRotation=(Math.PI*2)-(Math.PI/8);}
				else if(attackAnimationStage>=24 && attackAnimationStage<36){weaponRotation=(Math.PI*2);}
				else if(attackAnimationStage>=36){
					weaponRotation=(Math.PI*2);
					e.setAttacking(false);
					attackAnimationStage=0;
				}
				g2d.setColor(Color.BLACK);
				g2d.rotate(weaponRotation, e.getCenterX(), e.getCenterY());
				g2d.fillRect(e.getX()+10, e.getY()+10, 70, 5);
				g2d.rotate(weaponRotation*-1, e.getCenterX(), e.getCenterY());
			}
		}
		
		////
		g2d.dispose();
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