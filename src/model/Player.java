package model;

public class Player extends Entity {
	private final int maxHP=100;
	private int dx, dy;
	
	public Player(int id, int x, int y, int w, int h){
		super(id, x, y, w, h, true);
		dx = dy = 0;
		hp=50;
	}
	
	public void tick(){
		move(dx, dy);
		
	}
	
	//Temporär lösning.
	public void move(int dx, int dy){ // Use for collision
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		if(!collision()){
			this.x += dx;
			this.y += dy;
		}
	}
	
	private boolean collision(){
		
		return false;
	}
	
	public void setdx(int dx){
		this.dx = dx;
	}
	
	public void setdy(int dy){
		this.dy = dy;
	}
}