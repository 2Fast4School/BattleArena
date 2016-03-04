package model;


/**
 * The Weapon class stores basic information about a Weapon.
 * @author Victor Dahlberg
 * @version 03-03-16
 *
 */
public abstract class Weapon extends Entity {

	protected boolean attacking, dmgDone;
	protected Unit owner;
	private int damage;
	
	/**
	 * 
	 * @param owner The Unit who is the owner of the Weapon.
	 * @param width The Weapons width.
	 * @param height The Weapons Height.
	 * @param damage The Weapons damage.
	 */
	public Weapon(Unit owner, int width, int height, int damage) {
		super(owner.getCenterX(), owner.getCenterY(), width, height, true);
		this.owner = owner;
		attacking = false;
		this.damage = damage;
	}
	
	/**
	 * 
	 * @return The Owner of the Weapon.
	 */
	public Unit getOwner(){
		return owner;
	}
	
	
	/**
	 * Initiate an attack.
	 */
	public abstract void attack();
	

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
	 * Call this if damage's been dealt, the sweep-sword will finish it sweep but you can't do damage to multiple enemies in one sweep.
	 */
	public void damageDone(){
		dmgDone = true;
	}
	
	/**
	 * Stop the current Attack.
	 */
	public void stopAttack(){
		attacking = false;
		dmgDone = false;
	}
	
	/**
	 * 
	 * @return True if this weapon is currently attacking.
	 */
	public boolean isAttacking(){
		return attacking;
	}

}
