package model;

/**
 * <h1>SpawnPoint</h1>
 * A class which represents the spawnpoints in the map
 * Has it's own ArrayList in Map.
 * @author Alexander Erenstedt - Modified 28-02-16
 * @version 1.0  
 * 
 */
public class SpawnPoint extends Tile{
	/**
	 * Creates a spawnpoint at x,y with size = 1*1, not solid
	 * @param x The x-position of the SpawnPoint.
	 * @param y	The y-position of the SpawnPoint
	 */
	public SpawnPoint(int x, int y) {
		super(x, y, 1, 1, false);
	}
	
	/**
	 * This tile should'nt do anything on tick
	 */
	@Override
	public void tick() {
		
	}
	
}
