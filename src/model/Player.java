package model;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Player extends Entity {
	private int dx, dy;
	private ArrayList<Entity> closeObjects = new ArrayList<Entity>();
	private boolean attacking;
	private Weapon weapon;
	
	public Player(int id, int x, int y, int w, int h){
		super(id, x, y, w, h, true);
		dx = dy = 0;
		attacking = false;
	}
	
	//Dummy-tick
	public void tick(){}
	
	//Tick u
	public void tick(ArrayList<Entity> closeObjects){
		this.closeObjects.clear();
		this.closeObjects.addAll(closeObjects);
		move(dx, dy);
		
	}
	
	
	public void move(int dx, int dy){ 
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		if(!collision(dx, dy)){
			
			this.x += dx;
			this.y += dy;
		}
	}
	
	private boolean collision(int dx, int dy){
		Rectangle pNext = new Rectangle(x+dx, y+dy, w, h);
		for(Entity e : closeObjects){
			if(pNext.intersects(e.getBounds())){
				return true;
			}
			
		}
		return false;
	}
	
	public void setdx(int dx){
		this.dx = dx;
	}
	
	public void setdy(int dy){
		this.dy = dy;
	}
	
	public void setAttacking(boolean state){
		attacking=state;
	}
	public boolean getAttacking(){
		return attacking;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
}