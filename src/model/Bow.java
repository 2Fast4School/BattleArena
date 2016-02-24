package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class Bow extends Weapon {
	private int  arrowWidth, arrowHeight;
	private ArrayList<Arrow> arrows;
	private int delay;
	
	public Bow(Unit owner, int arrowWidth, int arrowHeight, int damage){
		super(owner, 0, 0, damage);
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;
		arrows = new ArrayList<Arrow>();
		delay = 0;
	}
	
	public void stopAttack(){
		arrows.clear();
		dmgDone = false;
	}

	public Rectangle getBounds() {return (new Rectangle(owner.getCenterX(),owner.getCenterY(),0,0));}
	
	public ArrayList<Arrow> getArrows(){
		if(arrows.size() != 0){
			return arrows;
		} return null;
	}

	public void tick() {
		for(Iterator<Arrow> iterator = arrows.iterator(); iterator.hasNext();){
			Arrow a = iterator.next();
			if(!a.isAttacking()){
				iterator.remove();
			} 
		}
		delay--;
	}

	
	public void attack() {
		if(delay <= 0){
			attacking = true;
			dmgDone = false;
			arrows.add(new Arrow(this, owner));
			delay = 60;
		} 
	}
	
	private class Arrow extends Weapon{
		private double dx, dy, v;
		private int duration;
		private int rotation;
		
		public Arrow(Bow bow, Unit owner) {
			super(owner, bow.arrowWidth, bow.arrowHeight, bow.getDmg());
			v = 10;
			
			rotation = owner.getRotVar() - 90;
			if(rotation <= 0)
				rotation += 360;
			
			System.out.println(rotation);
			System.out.println(Math.toRadians(rotation));
			dx =  v * Math.cos(Math.toRadians(rotation));
			dy =  v * Math.sin(Math.toRadians(rotation));
			attack();
		}

		@Override
		public void tick() {
			if(dmgDone || duration <= 0){
				stopAttack();
			} else {
				this.x += dx;
				this.y += dy;
				duration--;
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
