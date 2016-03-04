package model;

/**
 * <h1>WallTile</h1>
 * A simple class which represents a wall in the game
 * @author Alexander Erenstedt - Modified 28-02-16
 * @version 1.0  28-02-16
 */
public class WallTile extends Tile{
	/**
	 * The constructor, creates a Solid WallTile at x,y with the size of w and h
	 * @param x The x-position of the WallTile.
	 * @param y	The y-position of the WallTile
	 * @param w	The width of the WallTile.
	 * @param h	The height of the WallTile.
	 */
	public WallTile(int x, int y, int w, int h) {
		super(x, y, w, h, true); //Is always solid
	}
	/**
	 * This tile should'nt do anything on tick
	 */
	public void tick() {

	}

	
}
