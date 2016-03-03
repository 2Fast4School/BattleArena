package model;

import java.io.Serializable;

/**
 * A class which represents the spawnpoints in the map
 * Has it's own ArrayList in Map.
 * @author Alexander Erenstedt - Modified 28-02-16
 * @version 1.0  
 * 
 */
public class SpawnPoint extends Tile{
	/**
	 * 
	 */
	private boolean isUsed;
	
	/**
	 * Creates a spawnpoint at x,y with size = 1*1, not solid
	 * @param x The x-position of the SpawnPoint.
	 * @param y	The y-position of the SpawnPoint
	 */
	
	public SpawnPoint(int x, int y) {
		super(x, y, 1, 1, false);
		isUsed=false;
	}
	
	/**
	 * Sets the SpawnPoint as used so the server can keep track on used SpawnPoints
	 */
	public void setUsed(){
		isUsed = true;
	}
	
	/**
	 * Sets the SpawnPoint as unused
	 */
	public void setUnused(){
		isUsed = false;
	}
	
	/**
	 * @return If the SpawnPoint has been used
	 */
	public boolean checkUsed(){
		return isUsed;
	}
	
	@Override
	public void tick() {
		
	}
	
}
