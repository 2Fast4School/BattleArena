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
 * Class used to generate the map based on a image
 * 
 * @author Alexander Erenstedt
 * @version 1.0 A2
 */
public class MapGenerator { //Perhaps implement serialization
	private static final int sizeOfPixel = 16;
	/**
	 * Enormous method which generates entities as boundaries in the Map object it returns
	 * and paints the background depending on the parameter type.
	 * Don't try to understand it, please... ;)
	 * @param fileName The filename of the image the map should be based on
	 * @param type The desired type of the map generated
	 * @return The map it created
	 */
	public static Map generateMap(BufferedImage logicMap, String type){
		
		
		//The different images used
		BufferedImage standardBackground = null;
		BufferedImage wallBackground = null;
		BufferedImage damageTileBackground = null;
		
		try {
			standardBackground = ImageIO.read(new File("res/" + type + "/standardBackground.png"));
			wallBackground = ImageIO.read(new File("res/" + type + "/wall.png"));
			damageTileBackground = ImageIO.read(new File("res/" + type + "/damageTileBackground.png"));
			
		} catch (IOException e) {	
		    e.printStackTrace();
		}
		
		
		int height = logicMap.getHeight();
		int width = logicMap.getWidth();
		if(height != 50 || width != 50){ //Temp limit, generates 800x800 map.
			return generateBlankMap();
		}
		BufferedImage background = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Map map = new Map();
		
		//More support methods...
		
		//Create map boundaries
			//Top boundary
			map.addTile(new WallTile(-10, -10, width*sizeOfPixel+20, 10));
			//Left boundary
			map.addTile(new WallTile(-10, 0, 10, height*sizeOfPixel));
			//Right boundary
			map.addTile(new WallTile(width*sizeOfPixel, 0, 10, height*sizeOfPixel));
			//Bottom boundary
			map.addTile(new WallTile(-10, height*sizeOfPixel, width*sizeOfPixel+20, 10));
		
		//Possibly more support methods......
		//Create mapObjects based on pixels, the format ARGB is used with hexcode
		//example "ff000000" gives A = ff, R = 00, G = 00, B = 00 which gives black
		int temp;
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int rgb = logicMap.getRGB(x, y);
				switch(Integer.toHexString(rgb)){
				case "ff000000": //Black, represents wall
					temp = checkAdjacentToRight(x, y, logicMap);
					map.addTile(new WallTile(x*sizeOfPixel, y*sizeOfPixel, temp*sizeOfPixel, sizeOfPixel));
					x += temp;
					break;
				case "fffff200": //yellow, represents spawnpoints
					map.addSpawnPoint(new spawnPoint(x*sizeOfPixel, y*sizeOfPixel, 1, 1));
					break;
				case "ffed1c24":
					//temp = checkAdjacentToRight(x, y, logicMap);
					map.addTile(new DamageTile(x*sizeOfPixel, y*sizeOfPixel, 1*sizeOfPixel, sizeOfPixel));
					break;
				default:
					break;
				}
				
				//System.out.println(Integer.toHexString(rgb));
			
			}
			
		}
		//More.
		//Paint the background
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int rgb = logicMap.getRGB(x, y);
				//System.out.println(Integer.toHexString(rgb));
				String tileType = "";
				if(Integer.toHexString(rgb).compareTo("ffffffff") == 0) {
					tileType = "standardPath"; 
				}
				if(Integer.toHexString(rgb).compareTo("fffff200") == 0) { //Yellow
					tileType = "standardPath"; //Should not be visible to the player
				}
				if(Integer.toHexString(rgb).compareTo("ff000000") == 0) {
					tileType = "wall"; //Wall
				}
				if(Integer.toHexString(rgb).compareTo("ffed1c24") == 0) {
					tileType = "damage";
				}
				for(int i=0; i < sizeOfPixel; i++){
					for(int j = 0; j < sizeOfPixel; j++){
						switch(tileType){
						case "standardPath":							
							if(standardBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), standardBackground.getRGB(i, j));
								break;
							}else{
								tileType="getrektfaggot";
							}
							
						case "wall":
							if(wallBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), wallBackground.getRGB(i, j));
								break;
							}else{
								tileType="getrektfaggot";
							}
						case "damage":
							if(damageTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), damageTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="getrektfaggot";
							}
						default:
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rgb);
							break;
						}
					}
				}
			}
		}
		
		System.out.println(map.getTiles().size());
		map.setBackground(background);
		return map;
		
	}
	/**
	 * Method used to create a empty map if something went wrong in generateMap
	 * @return Empty Map with white background
	 */
	private static Map generateBlankMap(){
		Map map = new Map();
		map.setBackground(new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB));;
		return map;
	
	}
	/**
	 * A recursive function to check if the pixel to the right is adjacent
	 * is used to optimize the creation of entities in MapGenerator.
	 * @param x The x-coord of the pixel comparing to
	 * @param y	The y-coord of the pixel comparing to
	 * @param img The img used to compare
	 * @return Returns the number of adjacent pixels to the right
	 */
	private static int checkAdjacentToRight(int x, int y, BufferedImage img){
		int rgb = img.getRGB(x, y);
		if(rgb == img.getRGB(x+1, y) && x+1 <= img.getWidth()){
			int temp = checkAdjacentToRight(x+1, y, img)+1 ;
			return temp;
		}
		return 1;
	}
	
	/*private static void paintBackground(int x, int y, BufferedImage origin, BufferedImage background){
		for(int i=0; i < sizeOfPixel; i++){
			for(int j = 0; j < sizeOfPixel; j++){
				origin.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), background.getRGB(i, j));				
			}
		}
		//return origin;
	}*/
	
	
	
}
