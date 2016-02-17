package model;

public class Enemy extends Entity {
	private final int maxHP=100;
	public Enemy(int id, int x, int y, int w, int h){
		super(id, x, y, w ,h, true);
		hp=maxHP;
	}

	public void tick() {
	
	}
}