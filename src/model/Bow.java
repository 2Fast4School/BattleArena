package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class Bow extends Weapon {
	private int  arrowWidth, arrowHeight;
	private ArrayList<Arrow> arrows;
	private int delay;
	private int damage;
	
	public Bow(Unit owner, int arrowWidth, int arrowHeight, int damage){
		super(owner, arrowWidth, arrowHeight, damage);
		this.damage = damage;
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;
		arrows = new ArrayList<Arrow>();
		delay = 0;
	}

	public Rectangle getBounds() {return null;}
	
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
		private double dx, dy;
		private int duration;
		private int rotation;
		
		public Arrow(Bow bow, Unit owner) {
			super(owner, bow.arrowWidth, bow.arrowHeight, bow.damage);
			this.dx = Math.round(Math.cos(owner.getRotVar()));
			this.dy = Math.round(Math.sin(owner.getRotVar()));
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
			return (new Rectangle(x, y, w, h));
		}
		
	}



}
