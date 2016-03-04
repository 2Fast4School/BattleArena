package model;


import java.io.IOException;

import javax.imageio.ImageIO;

import arenaFighter.Main;
/**
 * Each instance represents a player placed on a different client.
 * @author Victor Dahlberg - Modified 28-02-16
 * @version 1.0
 */
public class Enemy extends Unit {
	private int ID;
	private boolean hasAttacked;
	/**
	 * Constructor for Enemy. 
	 * @param x The x-position of the Enemy.
	 * @param y	The y-position of the Enemy.
	 * @param w	The width of the Enemy.
	 * @param h	The height of the Enemy
	 */
	public Enemy(int x, int y, int w, int h){
		super(x, y, w ,h, true);
		hasAttacked=false;
		//Init the enemies Id with -1 which means this enemy object dont have a "human counter-part".
		setID(-1);
		loadImages();
	}

	public void tick() {}
	
	/**
	 * @return The ID of this enemy.
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * @param ID The id to give this enemy.
	 */
	public void setID(int ID){
		this.ID = ID;
	}
	/**
	 * Loads in all the sprites associated with the Enemy's character to a TreeMap.
	 * The keys to the map are which direction the character is facing, IE: (R)ight, (L)eft etc.
	 * The associated value is an ArrayList containing all sprites comprising the attack animation,
	 * where [0] is used as regular standing animation. 
	 */
	public void loadImages(){
		try{
			sprite = ImageIO.read(Main.class.getResource("/testa.png"));
		}catch(IOException e){}	
	}
	public void setHasAttacked(boolean state){hasAttacked=state;}
	public boolean getHasAttacked(){return hasAttacked;}
}