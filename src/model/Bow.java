package model;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import arenaFighter.Main;

public class Bow extends Weapon {
	private int  arrowWidth, arrowHeight;
	private Arrow newArrow;
	private int delay;
	private boolean newArrowFetched = false;
	
	public Bow(Unit owner, int arrowWidth, int arrowHeight, int damage){
		super(owner, 0, 0, damage);
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;
		newArrow = null;
		delay = 0;
	}
	
	public Arrow getNewArrow(){
		newArrowFetched = true;
		return newArrow;
	}

	public Rectangle getBounds() {return (new Rectangle(owner.getCenterX(),owner.getCenterY(),0,0));}

	public void tick() {
		if(newArrowFetched){
			newArrow = null;
			stopAttack();
		}
		
		delay--;
	}

	
	public void attack() {
		if(delay <= 0){
			attacking = true;
			dmgDone = false;
			newArrow = new Arrow(this, owner);
			newArrowFetched = false;
			delay = 60;
		} 
	}
	
	private class Arrow extends Weapon{
		private double dx, dy, v;
		private int duration;
		private int rotation;
		
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

		@Override
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
		
		public int getRotVar(){
			return rotation;
		}

		@Override
		public void attack() {
			if(!attacking){
				rotation = owner.getRotVar();
				attacking = true;
				dmgDone = false;
				duration = 100;
			}
		}
		
		public Rectangle getBounds(){
			return (new Rectangle(x - w , y - w, 2*w, 2*w));
		}
		
	}



}