package model;
/**
 * Player class, extends the Entity Class. Used to store the data of YOUR player. Only one player per Game.
 * @author Victor Dahlberg.
 * @version 1.0
 */
public class Player extends Entity {
	private int dx, dy;
	
	/**
	 * Constructor.
	 * @param id ID of the entity.
	 * @param x The players x position.
	 * @param y The player y position.
	 * @param w The player's hitbox-width.
	 * @param h The player's hitbox height.
	 */
	public Player(int id, int x, int y, int w, int h){
		super(id, x, y, w, h, true);
		dx = dy = 0;
	}
	
	/**
	 * 
	 */
	public void tick(){
		move(dx, dy);
		
	}
	
	/**
	 * TEMPORARY. WILL BE CHANGED.
	 * Check's if the players next position, intersects with any object. If it doesnt, changes the x and y position. Recursivly uses move for vertical and horizontal speed.
	 * @param dx x-velocity
	 * @param dy y-velocity
	 */
	public void move(int dx, int dy){
		/*
		 * If the player is moving diagonally, has a x velocity and a y velocity.
		 * Call move recursivly with only the x velocity and then the y velocity.
		 * This is used so the player can "slide" against walls.
		 * So if you're moving diagonally and will intersect a wall, if you will intersect the wall horizontally, you can still move vertically and vice verse.
		 */
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		/*
		 * CHECK if collision. If no collision change the position.
		 */
		if(!collision()){
			this.x += dx;
			this.y += dy;
		}
	}
	
	/**
	 * DUMMY-method. will be implemented.
	 * @return boolean, returns true if the player intersects an object, false otherwise.
	 */
	private boolean collision(){
		
		return false;
	}
	
	/**
	 * Set the players horizontal velocity.
	 * @param dx the horizontal speed.
	 */
	public void setdx(int dx){
		this.dx = dx;
	}
	
	/**
	 * Set the vertical velocity.
	 * @param dy the vertical speed.
	 */
	public void setdy(int dy){
		this.dy = dy;
	}
	
}