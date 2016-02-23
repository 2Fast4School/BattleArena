package model;


import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Model to store info of an Enemy. Enemies should only contain information about the other Players.
 * @author Victor Dahlberg
 *
 */
public class Enemy extends Unit {
	private int ID;
	/**
	 * Constructor for Enemy. 
	 * @param x The x-position of the Enemy.
	 * @param y	The y-position of the Enemy.
	 * @param w	The width of the Enemy.
	 * @param h	The height of the Enemy
	 */
	public Enemy(int x, int y, int w, int h){
		super(x, y, w ,h);
		setWeapon(new SweepSword(this, 8, 50));
		
		//Init the enemies Id with -1 which means this enemy object dont have a "human counter-part".
		setID(-1);
		loadImages();
	}

	
	public void tick() {}
	/**
	 * 
	 * @return The ID of this enemy.
	 */
	public int getID(){
		return ID;
	}
	
	/**
	 * 
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
			sprite = ImageIO.read(new File("res/testa.png"));
		}catch(IOException e){}	
	}
}