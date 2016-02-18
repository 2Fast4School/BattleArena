package model;

public class Enemy extends Entity {
	private final int maxHP=100;
	private boolean attacking=false;
	public Enemy(int id, int x, int y, int w, int h){
		super(id, x, y, w ,h, true);
		hp=maxHP;
	}

	public void tick() {
	
	}
	public void setAttacking(boolean state){
		attacking=state;
	}
	public boolean getAttacking(){
		return attacking;
	}
}