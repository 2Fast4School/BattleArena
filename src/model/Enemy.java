package model;

public class Enemy extends Entity {
	/**
	 * Constructor for Enemy. 
	 * @param id This is the id of the Enemy.
	 * @param x The x-position of the Enemy.
	 * @param y	The y-position of the Enemy.
	 * @param w	The width of the Enemy.
	 * @param h	The height of the Enemy
	 */
	public Enemy(int id, int x, int y, int w, int h){
		super(id, x, y, w ,h, true);
	}
	
	public void tick() {
	
	}
}