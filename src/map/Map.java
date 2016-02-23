package map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Entity;
/**
 * <h1>Enemy</h1>
 * Used to represent other players in the game
 * 
 * @author 
 * @version 1.0 A1
 */
public class Map {
	private ArrayList<Entity> gameObjects;
	private BufferedImage background;
	
	/**
	 * Constructor for Map,
	 * Initializes the background image as null temporarily
	 */
	public Map(){
		background = null;
		gameObjects = new ArrayList<Entity>();
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
	public void addEntity(Entity e){
		gameObjects.add(e);
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
	public ArrayList<Entity> getEntities(){
		return gameObjects;
	}
	
}
