package model;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
/**
 * <h1>Player</h1>
 * Player class extends the Entity Class. Used to store the data of YOUR player. Only one player per Game.
 * @author Victor Dahlberg.
 * @version 1.0
 */
public class Player extends Unit{
	private int dx, dy;
	private ArrayList<Entity> closeObjects = new ArrayList<Entity>();
	/**
	 * Constructor.
	 * @param id ID of the entity.
	 * @param x The players x position.
	 * @param y The player y position.
	 * @param w The player's hitbox-width.
	 * @param h The player's hitbox height.
	 */
	public Player(int x, int y, int w, int h){
		super(x, y, w, h);
		dx = dy = 0;
		//setWeapon(new SweepSword(this, 8, 50));
		setWeapon(new Bow(this, 4, 4, 5));
		loadImages();
	}
	
	//Dummy-tick
	public void tick(){}
	
	/**
	 * An override of the usual tick because this tick needs to know about the objects closeby so it can check for collission.
	 * @param closeObjects Possible object the Player can collide with.
	 */
	public void tick(ArrayList<Entity> closeObjects){
		this.closeObjects.clear();
		this.closeObjects.addAll(closeObjects);
		move(dx, dy);
	}
	
	/**
	 * Loads in all the sprites associated with the Player's character to a TreeMap.
	 * The keys to the map are which direction the character is facing, IE: (R)ight, (L)eft etc.
	 * The associated value is an ArrayList containing all sprites comprising the attack animation,
	 * where [0] is used as regular standing animation. 
	 */
	public void loadImages(){
		try{
			sprite = ImageIO.read(new File("res/testa.png"));
		}catch(IOException e){}
	}
	
	/**
	 * TEMPORARY. WILL BE CHANGED.
	 * Check's if the players next position, intersects with any object. If it doesnt, changes the x and y position. Recursivly uses move for vertical and horizontal speed.
	 * @param dx x-velocity
	 * @param dy y-velocity
	 */
	public void move(int dx, int dy){
		/*
		 * If the player is moving diagonally, has a x velocity and a y velocity.
		 * Call move recursivly with only the x velocity and then the y velocity.
		 * This is used so the player can "slide" against walls.
		 * So if you're moving diagonally and will intersect a wall, if you will intersect the wall horizontally, you can still move vertically and vice verse.
		 */
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		/*
		 * CHECK if collision. If no collision change the position.
		 */
		if(!collision(dx, dy)){
			this.x += dx;
			this.y += dy;
		}
	}
	
	/**
	 * Checks if the players NEXT position, x + dx, y + dy, intersects a closeby object.
	 * @return boolean, returns true if the player intersects an object, false otherwise.
	 */
	private boolean collision(int dx, int dy){
		Rectangle pNext = new Rectangle(x+dx, y+dy, w, h);
		for(Entity e : closeObjects){
			if(pNext.intersects(e.getBounds()) && e.isSolid()){
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * Set the players horizontal velocity.
	 * @param dx the horizontal speed.
	 */
	public void setdx(int dx){
		this.dx = dx;
	}
	
	/**
	 * Set the vertical velocity.
	 * @param dy the vertical speed.
	 */
	public void setdy(int dy){
		this.dy = dy;
	}
}