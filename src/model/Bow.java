package model;

import java.util.ArrayList;

public class Bow extends Weapon {
	private int  arrowWidth, arrowHeight;
	private ArrayList<Arrow> arrows;
	private int delay;
	
	public Bow(Unit owner, int arrowWidth, int arrowHeight){
		super(owner, arrowWidth, arrowHeight, 5);
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;
		delay = 60;
		ArrayList<Arrow> arrows = new ArrayList<Arrow>();
	}
	
	public ArrayList<Arrow> getActiveArrows(){
		return arrows;
	}

	public void tick() {
		for(Arrow a : arrows){
			if(a.isAlive()){
				a.tick();
			} else {
				arrows.remove(a);
			}
		}	
		delay--;
	}

	
	public void attack() {
		if(!attacking){
			attacking = true;
			dmgDone = false;
			double rotation = owner.getRotVar();
			double dx = Math.cos(Math.toRadians(rotation));
			double dy = Math.sin(Math.toRadians(rotation));
			delay = 60;
			arrows.add(new Arrow(owner.getCenterX(), owner.getCenterY(), arrowWidth, arrowHeight, dx, dy, true));
		} if(delay <= 0){
			stopAttack();
		}
		
	}
	
	private class Arrow extends Entity{
		private double dx, dy;
		private int duration;
		private boolean isAlive;
		
		public Arrow(int x, int y, int w, int h, double dx, double dy, boolean solid) {
			super(x, y, w, h, solid);
			this.dx = Math.round(dx);
			this.dy = Math.round(dy);
			isAlive = true;
			duration = 100;
		}

		@Override
		public void tick() {
			if(duration <= 0){
				isAlive = true;
			} else {
				this.x += dx;
				this.y += dy;
			}
		}
		
		public boolean isAlive(){
			return isAlive;
		}
		
	}

}
