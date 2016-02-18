package model;

public class Enemy extends Entity {

	public Enemy(int id, int x, int y, int w, int h){
		super(id, x, y, w ,h, true);
	}

	public void tick() {
	
	}

	@Override
	public void setAttacking(boolean state) {
		
	}

	@Override
	public boolean getAttacking() {
		// TODO Auto-generated method stub
		return false;
	}
}