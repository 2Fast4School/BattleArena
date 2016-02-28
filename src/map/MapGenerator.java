package map;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.DamageTile;
import model.WallTile;
import model.spawnPoint;

/**
 * <h1>MapGenerator</h1>
 * Class used to generate the map based on a logicMap(BufferedImage), a theme 
 * and the size of which each pixel in the logicMap should represent
 * @author Alexander Erenstedt - Modified 02-28-16
 * @version 1.0
 */
public class MapGenerator { //Perhaps implement serialization
	/**
	 * Enormous method which generates entities as boundaries in the Map object it returns
	 * and paints the background depending on the parameter type.
	 * @param The logicMap which will be used to create walls and such
	 * @param type The desired theme of the map generated
	 * @param sizeOfPixel The size each pixel in the logicMap should represent in the game view (16 or 8)
	 * @return The map it created
	 */
	public static Map generateMap(BufferedImage logicMap, String type, int sizeOfPixel){
		//The different images used
		BufferedImage standardTileBackground = null;
		BufferedImage wallTileBackground = null;
		BufferedImage damageTileBackground = null;
		
		//If the read failed they will be null and be painted as the colorcode on the background
		try {
			standardTileBackground = ImageIO.read(new File("res/" + type + "/standardBackground.png"));
			wallTileBackground = ImageIO.read(new File("res/" + type + "/wall.png"));
			damageTileBackground = ImageIO.read(new File("res/" + type + "/damageTileBackground.png"));
			
		} catch (IOException e) {	
		    e.printStackTrace();
		}
		
		
		int height = logicMap.getHeight();
		int width = logicMap.getWidth();
		BufferedImage background = new BufferedImage(logicMap.getWidth()*sizeOfPixel, logicMap.getHeight()*sizeOfPixel, BufferedImage.TYPE_INT_ARGB);
		Map map = new Map();
		//Create map boundaries so that you can't leave the map
			//Top boundary
			map.addTile(new WallTile(-10, -10, width*sizeOfPixel+20, 10));
			//Left boundary
			map.addTile(new WallTile(-10, 0, 10, height*sizeOfPixel));
			//Right boundary
			map.addTile(new WallTile(width*sizeOfPixel, 0, 10, height*sizeOfPixel));
			//Bottom boundary
			map.addTile(new WallTile(-10, height*sizeOfPixel, width*sizeOfPixel+20, 10));
		
		//Create mapObjects based on pixels, the format ARGB is used with hexcode
		//example "ff000000" gives A = ff, R = 00, G = 00, B = 00 which gives black
		for(int y = 0; y < height; y++){ //Need to loop in "wrong" order for the checkAdjacentToRIght to work
			for(int x = 0; x < width; x++){ 
				int temp = 0;
				int rgb = logicMap.getRGB(x, y);
				switch(Integer.toHexString(rgb)){
				case "ff000000": //Black, represents wall
					temp = checkAdjacentToRight(x, y, logicMap); 
					map.addTile(new WallTile(x*sizeOfPixel, y*sizeOfPixel, temp*sizeOfPixel, sizeOfPixel));
					x += temp; //Used so skip the pixels the optimization method created
					break;
				case "fffff200": //yellow, represents spawnpoints
					map.addSpawnPoint(new spawnPoint(x*sizeOfPixel, y*sizeOfPixel));
					break;
				case "ffed1c24": //red, represent a damageTile
					temp = checkAdjacentToRight(x, y, logicMap); 
					map.addTile(new DamageTile(x*sizeOfPixel, y*sizeOfPixel, temp*sizeOfPixel, sizeOfPixel));
					break;
				default:
					break;
				}
				//System.out.println(Integer.toHexString(rgb));
			}
		}
		
		//Paint the background
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int rgb = logicMap.getRGB(x, y);
				//System.out.println(Integer.toHexString(rgb));
				String tileType = "";
				if(Integer.toHexString(rgb).compareTo("ffffffff") == 0) { //white
					tileType = "standard"; 
				}
				if(Integer.toHexString(rgb).compareTo("fffff200") == 0) { //Yellow
					tileType = "standard"; //Should not be visible to the player
				}
				if(Integer.toHexString(rgb).compareTo("ff000000") == 0) { //Black
					tileType = "wall"; //Wall
				}
				if(Integer.toHexString(rgb).compareTo("ffed1c24") == 0) { //Wall
					tileType = "damage";
				}
				for(int i=0; i < sizeOfPixel; i++){
					for(int j = 0; j < sizeOfPixel; j++){
						switch(tileType){
						case "standard": //Paints the standardBackground 					
							if(standardTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), standardTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="donotmatchondis";
							}
						case "wall":
							if(wallTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), wallTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="onotmatchondis";
							}
						case "damage":
							if(damageTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), damageTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="onotmatchondis";
							}
						default:
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rgb);
							break;
						}
					}
				}
			}
		}
		
		//prints the number of entities created
		System.out.println(map.getTiles().size()+map.getSpawnPoints().size()); 
		map.setBackground(background);
		return map;
	}
	
	/**
	 * A recursive function to check if the pixel to the right is adjacent
	 * is used to optimize the creation of entities in MapGenerator.
	 * @param x The x-coord of the pixel comparing to
	 * @param y	The y-coord of the pixel comparing to
	 * @param img The img used to compare
	 * @return Returns the number of adjacent pixels to the right with the same color
	 */
	private static int checkAdjacentToRight(int x, int y, BufferedImage img){
		int width = img.getWidth();
		int rgb = img.getRGB(x, y);

		if(x+1 < width && rgb == img.getRGB(x+1, y)){
			int temp = checkAdjacentToRight(x+1, y, img)+1 ;
			return temp;
		}
		return 1;
	}	
}
