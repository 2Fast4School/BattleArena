package model;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import arenaFighter.Main;

/**
 * Stores information of the Bow.
 * Bow creates a new arrow each time an attack is initiated.
 * @author Victor Dahlberg
 * @since 03-03-16
 *
 */
public class Bow extends Weapon {
	private int  arrowWidth, arrowHeight;
	private Arrow newArrow;
	private int delay;
	
	/**
	 * 
	 */
	private boolean newArrowFetched = false;
	
	/**
	 * 
	 * @param owner The bow's owner.
	 * @param arrowWidth The width of the arrows the bow creates.
	 * @param arrowHeight The height of the arrows the bow creates.
	 * @param damage The damage of each arrow the bow creates.
	 */
	public Bow(Unit owner, int arrowWidth, int arrowHeight, int damage){
		super(owner, 0, 0, damage);
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;
		newArrow = null;
		delay = 0;
	}
	
	/**
	 * Returns the latest arrow created by the Bow, will only return the Arrow the first time
	 * the method is called. Otherwise it will return null.
	 * @return The latest arrow created by the Bow.
	 */
	public Arrow getNewArrow(){
		newArrowFetched = true;
		return newArrow;
	}

	public Rectangle getBounds() {return (new Rectangle(owner.getCenterX(),owner.getCenterY(),0,0));}

	/**
	 * If the latest arrow has been fetched, it stop's the attack and set's newArrow to null.
	 * It also decreases the delay.
	 */
	public void tick() {
		if(newArrowFetched){
			newArrow = null;
			stopAttack();
		}
		
		delay--;
	}

	
	/**
	 * Creates a new arrow and set's the boolean newArrowFetched to false if an attack can happen.
	 * You can only create a new arrow once per second.
	 */
	public void attack() {
		if(delay <= 0){
			attacking = true;
			dmgDone = false;
			newArrow = new Arrow(this, owner);
			newArrowFetched = false;
			delay = 60;
		} 
	}
	
	/**
	 * Stores data about an Arrow. Arrows are created by the Bow when the bow attacks.
	 * @author Victor Dahlberg
	 * @version 03-03-16
	 *
	 */
	private class Arrow extends Weapon{
		private double dx, dy, v;
		private int duration;
		private int rotation;
		
		/**
		 * Creates a new arrow and start's it attack.
		 * @param bow The bow which shot the arrow.
		 * @param owner The owner of the Bow that shot the arrow.
		 */
		public Arrow(Bow bow, Unit owner) {
			super(owner, bow.arrowWidth, bow.arrowHeight, bow.getDmg());
			try{
				sprite = ImageIO.read(Main.class.getResource("/arrow.png"));
			}catch(IOException e){}
			
			v = 10;
			
			rotation = owner.getRotVar() - 90;
			if(rotation <= 0)
				rotation += 360;
			
			dx =  v * Math.cos(Math.toRadians(rotation));
			dy =  v * Math.sin(Math.toRadians(rotation));
			attack();
		}

		/**
		 * If the arrow is attacking and it hasn't reached its range or hasn't done any damage,
		 * it ticks the arrow forward. Otherwise it stops the arrow's attack.
		 */
		public void tick() {
			if(isAttacking()){
				if(dmgDone || duration <= 0){
					stopAttack();
				} else {
					this.x += dx;
					this.y += dy;
					duration--;
				}
			}
			
		}
		
		/**
		 * Returns the arrows rotation.
		 */
		public int getRotVar(){
			return rotation;
		}

		/**
		 * Start the arrow-attack.
		 */
		public void attack() {
			if(!attacking){
				rotation = owner.getRotVar();
				attacking = true;
				dmgDone = false;
				duration = 100;
			}
		}
		

		/**
		 *  @return A new Rectangle in the top of the arrow, which is the arrows hitbox.
		 */
		public Rectangle getBounds(){
			return (new Rectangle(x - w , y - w, 2*w, 2*w));
		}
		
	}



}