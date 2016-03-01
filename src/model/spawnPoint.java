package model;

public class spawnPoint extends Entity {
	private boolean isUsed = false;
	public spawnPoint(int x, int y, int w, int h) {
		super(x, y, w, h, false);
	}
	
	public void setUsed(){
		isUsed = true;
	}
	
	public void reset(){
		isUsed = false;
	}
	public boolean checkUsed(){
		return isUsed;
	}
	
	@Override
	public void tick() {
		
	}
	
}
