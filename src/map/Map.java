package map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Entity;
import model.Tile;
import model.spawnPoint;
/**
 * <h1>Map</h1>
 * Object to hold the map-specific objects such as spawnPoints and boundries
 * 
 * @author Alexander Erenstedt
 * @version 1.0
 */
public class Map {
	//All the mapObjects created by MapGenerator except spawnpoints
	private ArrayList<Tile> tiles; 
	
	private BufferedImage background;	
	private ArrayList<spawnPoint> spawnPoints;
	
	/**
	 * Constructor for Map,
	 * Initializes the background image as null temporarily
	 */
	public Map(){
		background = null;
		tiles = new ArrayList<Tile>();
		spawnPoints = new ArrayList<spawnPoint>();
	}
	
	/**
	 * @param img The background image for the board
	 */
	public void setBackground(BufferedImage img){
		background = img;
	}
	
	/**
	 * @param e The entity which should be added to the map
	 */
	public void addTile(Tile e){
		tiles.add(e);
	}
	/**
	 * @param e Adds spawnpoints
	 */
	public void addSpawnPoint(spawnPoint sp){
		spawnPoints.add(sp);
	}
	
	/**
	 * @return Returns the background as a BufferedImage, could be null
	 */
	public BufferedImage getBackground(){
		return background;
	}
	/**
	 * @return The array of the Map:s entities
	 */
	public ArrayList<Tile> getTiles(){
		return tiles;
	}
	/**
	 * @return Returns the spawnpoints of the map
	 */
	
	public ArrayList<spawnPoint> getSpawnPoints(){
		return spawnPoints;
	}
}
