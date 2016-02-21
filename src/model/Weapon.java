package model;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <h1>Weapon</h1>
 * Weapon stores data about a weapon. Each Player will have at least one weapon.
 * @author William Bj�rklund. // Victor Dahlberg
 * @version 2.0
 */
public class Weapon extends Entity{
	private boolean isAttacking;
	private Entity owner;
	private int x,y;
	private int duration;
	private int weaponRotation;
	private int radius;
	private int damage;
	private boolean dmgDone;
	
	/**
	 * Create a Sweeping-sword Weapon.
	 * @param owner The Owner of the weapon.
	 * @param width The width of the weapon / weapon-sprite.
	 * @param height The height of the weapon/weapon-sprite.
	 */
	public Weapon(Entity owner, int width, int height){
		super(owner.getCenterX(), owner.getCenterY(), width, height, true);
		isAttacking = false;
		this.owner = owner;
		radius = owner.getHeight() / 2;
		loadImages();
		damage = 5;
	}
	
	/**
	 * Call this method for the weapon to start an attack. It will only start an attack if an attack isn't already ongoing.
	 */
	public void attack(){
		if(!isAttacking){
			isAttacking = true;
			dmgDone = false;
			weaponRotation = 225;
			duration = 90;
		}
	}
	
	/**
	 * Call this method to stop an attack. This is the method that is called when an attack has "finished".
	 */
	public void stopAttack(){
		isAttacking = false;
		dmgDone = false;
		duration = 0;
		weaponRotation = 0;
		
		//Set's the weapon's x and y position to the center of the owners.
		x = owner.getCenterX();
		y = owner.getCenterY();
	}
	
	/**
	 * 
	 * @return isAttacking returns true if this weapon is currently attacking.
	 */
	public boolean isAttacking(){
		return isAttacking;
	}

	@Override
	public void tick() {
		/*
		 * If an attack is ongoing. Otherwise do nothing.
		 */
		if(isAttacking){
			if(duration <= 0){
				
				stopAttack();
			} else {
				
				double rot = Math.toRadians(weaponRotation + owner.getRotVar());
				x = Math.round((float)(owner.getCenterX() + Math.cos(rot)*radius));
				y = Math.round((float)(owner.getCenterY() + Math.sin(rot)*radius));
				setX(x);
				setY(y);
				weaponRotation += 1; // duration * the weaponrotation base, 1 in this case. must equal 90 for a 90degree swipe.
				duration--;
			}
		}
	}
	
	
	
	/**
	 * Loads the weapon with a Sprite.
	 */
	public void loadImages(){
		try{
			sprite = ImageIO.read(new File("res/sw.png"));
		}catch(IOException e){}
	}
	
	/**
	 * @return The correct rotation, the Owners rotation and the weapon's rotation.
	 */
	public int getRotVar(){
		return (weaponRotation - 90) + owner.getRotVar() ;
	}
	
	/**
	 * @return A Rectangle that is created at the "tip" of the sword, used for collision.
	 */
	public Rectangle getBounds(){
		
		int x2 = x + (int) (getHeight() *Math.cos(Math.toRadians(getRotVar() + 90)));
		int y2 = y + (int) (getHeight()*Math.sin(Math.toRadians(getRotVar() + 90)));
		return new Rectangle(x2 - w, y2 - w , w , w );
	}
	
	/**
	 * @return The center x positioin is the owners central position, because we rotate the sweep-sword around this.
	 */
	public int getCenterX(){
		return owner.getCenterX();
	}
	
	/**
	 * @return The center y positioin is the owners central position, because we rotate the sweep-sword around this.
	 */
	public int getCenterY(){
		return owner.getCenterY();
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
}
