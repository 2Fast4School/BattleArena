package model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import arenaFighter.Main;

public abstract class Weapon extends Entity{
	
	protected boolean attacking, dmgDone;
	protected Entity owner;
	private int damage;
	
	public Weapon(Unit owner, int width, int height, int damage) {
		super(owner.getCenterX(), owner.getCenterY(), width, height, true);
		this.owner = owner;
		attacking = false;
		this.damage = damage;
		loadImages();
	}
	
	
	/**
	 * Call this method for the weapon to start an attack. It will only start an attack if an attack isn't already ongoing.
	 */
	public abstract void attack();
	
	/**
	 * Loads the weapon with a Sprite.
	 */
	public void loadImages(){
		try{
			sprite = ImageIO.read(Main.class.getResource("/sw.png"));
		}catch(IOException e){}
	}

	/**
	 * 
	 * @return The weapons damage.
	 */
	public int getDmg(){
		return damage;
	}
	
	/**
	 * 
	 * @return True if damage's been done in the current cycle.
	 */
	public boolean getDmgDone(){
		return dmgDone;
	}
	
	/**
	 * Called this if damage's been dealt, the sweep-sword will finish it sweep but you can't do damage to multiple enemies in one sweep.
	 */
	public void damageDone(){
		dmgDone = true;
	}
	
	/**
	 * Call this method to stop an attack. This is the method that is called when an attack has "finished".
	 */
	public void stopAttack(){
		attacking = false;
		dmgDone = false;
	}
	
	/**
	 * 
	 * @return isAttacking returns true if this weapon is currently attacking.
	 */
	public boolean isAttacking(){
		return attacking;
	}

}
