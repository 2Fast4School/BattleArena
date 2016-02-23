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
public class SweepSword extends Weapon{
	
	private int x,y;
	private int weaponRotation;
	private int radius;
	private int duration;
	
	/**
	 * Create a Sweeping-sword Weapon.
	 * @param owner The Owner of the weapon.
	 * @param width The width of the weapon / weapon-sprite.
	 * @param height The height of the weapon/weapon-sprite.
	 */
	public SweepSword(Unit owner, int width, int height){
		super(owner, width, height, 5);
		radius = (owner.getHeight() / 2);
	}
	
	public void attack(){
		if(!attacking){
			attacking = true;
			dmgDone = false;
			duration = 90;
			weaponRotation = 225;
		}
	}
	

	public void tick() {
		/*
		 * If an attack is ongoing. Otherwise do nothing.
		 */
		if(isAttacking()){
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
	
	
}
