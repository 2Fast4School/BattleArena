package model;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import arenaFighter.Main;

/**
 * <h1>SweepSword</h1>
 * Stores information of a sweep-swords current state and information.
 * @author Victor Dahlberg
 * @version 21-02-16
 */
public class SweepSword extends Weapon{
	
	private int x,y;
	private int weaponRotation;
	private int radius;
	
	/**
	 * An attack can not be ongoing forever. The duration tells if the attack should and or not.
	 */
	private int duration;
	
	/**
	 * Create a Sweeping-sword Weapon.
	 * @param owner The Owner of the weapon.
	 * @param width The width of the weapon / weapon-sprite.
	 * @param height The height of the weapon/weapon-sprite.
	 * @param dmg The damage of the weapon.
	 */
	public SweepSword(Unit owner, int width, int height, int dmg){
		super(owner, width, height, dmg);
		radius = (owner.getHeight() / 2);
		try{
			sprite = ImageIO.read(Main.class.getResource("/sw.png"));
		}catch(IOException e){}
	}
	
	/**
	 * If the SweepSword is currently not attacking, it starts a new attack.
	 */
	public void attack(){
		if(!attacking){
			attacking = true;
			dmgDone = false;
			duration = 10;
			weaponRotation = 225;
		}
	}
	
	/**
	 * If an attack is ongoing, it proceeds the attack and change's the sweep-sword's, rotation etc.
	 * Otherwise, it does nothing.
	 */
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
				weaponRotation += 9; // duration * the weaponrotation base, 1 in this case. must equal 90 for a 90degree swipe.
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
