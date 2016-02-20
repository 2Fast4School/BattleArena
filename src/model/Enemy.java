package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	private final int maxHP=100;
	private boolean attacking=false;
	private Weapon weapon;
	private int ID;
	
	public Enemy(int x, int y, int w, int h){
		super(x, y, w ,h, true);
		hp=maxHP;
		weapon=new Weapon(50,10,20);
		setID(-1);
		loadImages();
	}

	public void tick() {
	
	}
	public int getID(){
		return ID;
	}
	
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
			sprite = ImageIO.read(new File("res/Character1.gif"));
		}catch(IOException e){}	
	}
	/**
	 * Returns the Enemy's Weapon
	 * @return weapon : Weapon
	 */
	public Weapon getWeapon(){return weapon;}
	/**
	 * Returns an ArrayList containing the ID:s of all other Enemys hit by
	 * this Enemy's latest attack
	 * @return idsHitByAttack : ArrayList(Integer)
	 */
}