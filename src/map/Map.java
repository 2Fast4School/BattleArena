package map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Tile;
import model.SpawnPoint;
/**
 * <h1>Map</h1>
 * Object to hold the map-specific objects such as spawnPoints, boundaries and damagetiles
 * @author Alexander Erenstedt - Modified: 28-02-16 
 * @version 1.0 
 */
public class Map{
	private ArrayList<Tile> tiles; 
	private BufferedImage background;
	private ArrayList<SpawnPoint> spawnPoints;
	
	/**
	 * Constructor for Map,
	 * Initializes the background image as null
	 */
	public Map(){
		background = null;
		tiles = new ArrayList<Tile>();
		spawnPoints = new ArrayList<SpawnPoint>();
	}
	
	/**
	 * Used to set the background image of the map
	 * @param img The background image for the board
	 */
	public void setBackground(BufferedImage img){
		background = img;
	}
	
	/**
	 * Used to add tiles to the map, used by MapGenerator
	 * @param e The tile which should be added to the map
	 */
	public void addTile(Tile e){
		tiles.add(e);
	}
	
	/**
	 * Used to add spawnpoints to the map, used by MapGenerator
	 * @param sp Adds spawnpoints
	 */
	public void addSpawnPoint(SpawnPoint sp){
		spawnPoints.add(sp);
	}
	
	/**
	 * Used to return the background image
	 * @return Returns the background as a BufferedImage
	 */
	public BufferedImage getBackground(){
		return background;
	}
	
	/**
	 * Returns the tiles of the map, not the spawnpoints
	 * @return The array of the Map:s entities
	 */
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
	
	/**
	 * Returns the spawnpoints of the map
	 * @return Returns the spawnpoints of the map
	 */
	public ArrayList<SpawnPoint> getSpawnPoints(){
		return spawnPoints;
	}
	

}
