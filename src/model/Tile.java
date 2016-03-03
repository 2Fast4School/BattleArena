package model;

import java.io.Serializable;

/**
 * A simple class which basically does nothing.
 * 
 * @author Alexander Erenstedt - Modified 28-02-16
 * @version 1.0  28-02-16
 * 
 */
public abstract class Tile extends Entity{
	/**
	 * 
	 */

	/**
	 * 
	 * @param x The x-position of the Tile.
	 * @param y	The y-position of the Tile.
	 * @param w	The width of the Tile.
	 * @param h	The height of the Tile.
	 * @param solid If the tile should be solid
	 */
	public Tile(int x, int y, int w, int h, boolean solid) {
		super(x, y, w, h, solid);
	}
}
