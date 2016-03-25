package map;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Main;
import model.DamageTile;
import model.SpawnPoint;
import model.WallTile;

/**
 * <h1>MapGenerator</h1>
 * Class used to generate the map based on a logicMap(BufferedImage), a theme 
 * and the size of which each pixel in the logicMap should represent
 * @author Alexander Erenstedt - Modified 02-28-16
 * @version 1.0
 */
public class MapGenerator {
	/**
	 * Method which generates entities as boundaries in the Map object it returns
	 * and paints the background depending on the parameter type.
	 * It creates tiles depending on which ARGB code in the image.
	 * "ff000000" is a black color which represents a wall
	 * "ffed1c24" is a red color which represents a damagetile
	 * "fffff200" is a white color which represents a spawnpoint
	 * If none of the above colors is matched not tile is created which means that the area will be pathable
	 * @param logicMap The logicmap which will be used to create walls and such, cannot be null
	 * @param type The desired theme of the map generated
	 * @param sizeOfPixel The size each pixel in the logicMap should represent in the game view (16 or 8)
	 * @return The map it created
	 */
	public static Map generateMap(BufferedImage logicMap, String type, int sizeOfPixel){		
		//The different images used
		
		Random random = new Random();
		
		
		
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
					map.addSpawnPoint(new SpawnPoint(x*sizeOfPixel, y*sizeOfPixel));
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
		//If the read failed they will be null and be painted as the colorcode on the background
		ArrayList<BufferedImage> standardTileBackgrounds = new ArrayList<BufferedImage>();
		
		BufferedImage wallTileBackground = null;
		BufferedImage damageTileBackground = null;
		
		try {
			standardTileBackgrounds.add(ImageIO.read(Main.class.getResource("/"+type + "/standardBackground0.png")));
			standardTileBackgrounds.add(ImageIO.read(Main.class.getResource("/"+type + "/standardBackground1.png")));
			standardTileBackgrounds.add(ImageIO.read(Main.class.getResource("/"+type + "/standardBackground2.png")));
			
			wallTileBackground = ImageIO.read(Main.class.getResource("/"+type + "/wall.png"));
			damageTileBackground = ImageIO.read(Main.class.getResource("/"+type + "/damageTileBackground.png"));
			
		} catch (IOException e) {	
		    e.printStackTrace();
		}
		
		
		
		//Paint the background
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int bIndex = 0;
				int rgb = logicMap.getRGB(x, y);
				
				bIndex = random.nextInt(3) - random.nextInt(20);
				if(bIndex < 0){
					bIndex = 0;
				}
				
				
				//System.out.println(rgb);
				String tileType = "";
				if(rgb == 0){ //No color at all, should never happen...
					
					
					tileType = "standard";
					rgb = -1; //Set the color to white
					
				}
				if(Integer.toHexString(rgb).compareTo("ffffffff") == 0) { //white
					
					tileType = "standard"; 
				}
				if(Integer.toHexString(rgb).compareTo("fffff200") == 0) { //Yellow
					tileType = "standard"; //Should not be visible to the player
					rgb = -1; //Set the color to white
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
							if(standardTileBackgrounds.get(bIndex) != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), standardTileBackgrounds.get(bIndex).getRGB(i, j));
								break;
							}else{
								tileType="donotmatchondis"; //Make sure there will be some color if image is null
							}
						case "wall":
							if(wallTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), wallTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="donotmatchondis"; //Make sure there will be some color if image is null
							}
						case "damage":
							if(damageTileBackground != null){
								background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), damageTileBackground.getRGB(i, j));
								break;
							}else{
								tileType="donotmatchondis"; //Make sure there will be some color if image is null
							}
						default: //The case where the color didn't match anything
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rgb);
							break;
						}
					}
				}
			}
		}
		
		//prints the number of entities created
		//

		System.out.println("Number of Tiles in map: " + (map.getTiles().size() + map.getSpawnPoints().size())); 
		//System.out.println("Number of Tiles in map: " + map.getTiles().size()+map.getSpawnPoints().size()); 
		map.setBackground(background);
		return map;
	}
	
	/**
	 * A recursive function to check if the pixel to the right is adjacent
	 * is used to optimize the creation of tiles in MapGenerator.
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
