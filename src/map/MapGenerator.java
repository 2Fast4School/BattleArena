package map;

import model.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <h1>MapGenerator</h1>
 * Class used to generate the map based on a image
 * 
 * @author Alexander Erenstedt
 * @version 1.0 A2
 */
public class MapGenerator { //Implement serialization
	private static final int sizeOfPixel = 16;
	/**
	 * Enormous method which generates entities as boundaries in the Map object it returns
	 * and paints the background depending on the parameter type.
	 * Don't try to understand it, please... ;)
	 * @param fileName The filename of the image the map should be based on
	 * @param type The desired type of the map generated
	 * @return The map it created
	 */
	public static Map generateMap(String fileName, String type){
		//Should be in support methods
		BufferedImage backgroundType = null;
		switch(type){
			case "grass":
				try {
				   backgroundType = ImageIO.read(new File("res/grass.png"));
				} catch (IOException e) {
				    e.printStackTrace();
				    backgroundType = null;
				}
				break;
			default:
				backgroundType = null;
				break;
		}
		//Also should be in support methods
		BufferedImage rock;
		try {
			   rock = ImageIO.read(new File("res/rock.png"));
			} catch (IOException e) {
			    e.printStackTrace();
			    rock = null;
			}
		
		//More support methods
		BufferedImage origin;
		try {
		    origin = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		    e.printStackTrace();
		    return generateBlankMap();
		}
		
		//Safety first bitches
		if(origin == null){
			return generateBlankMap();
		}
		int height = origin.getHeight();
		int width = origin.getWidth();
		if(height != 50 || width != 50){ //Temp limit, generates 800x800 map.
			return generateBlankMap();
		}
		BufferedImage background = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Map map = new Map();
		
		//More support methods...
		
		//Create map boundaries
			//Top boundary
			map.addEntity(new mapObject(-10, -10, width*sizeOfPixel+20, 10));
			//Left boundary
			map.addEntity(new mapObject(-10, 0, 10, height*sizeOfPixel));
			//Right boundary
			map.addEntity(new mapObject(width*sizeOfPixel, 0, 10, height*sizeOfPixel));
			//Bottom boundary
			map.addEntity(new mapObject(-10, height*sizeOfPixel, width*sizeOfPixel+20, 10));
		
		//Possibly more support methods......
		//Create mapObjects based on pixels, the format ARGB is used with hexcode
		//example "ff000000" gives A = ff, R = 00, G = 00, B = 00 which gives black
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				int rgb = origin.getRGB(x, y);
				switch(Integer.toHexString(rgb)){
				case "ff000000": //Black
					int temp = checkAdjacentToRight(x, y, origin);
					map.addEntity(new mapObject(x*sizeOfPixel, y*sizeOfPixel, temp*sizeOfPixel, sizeOfPixel));
					x += temp;
					break;
				case "fffff200": //yellow
					map.addSpawnPoint(new spawnPoint(x*sizeOfPixel, y*sizeOfPixel, 1, 1));
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
				int rgb = origin.getRGB(x, y);
				System.out.println(Integer.toHexString(rgb));
				String temp = "";
				if(Integer.toHexString(rgb).compareTo("ffffffff") == 0) {
					temp = "white";
				}
				if(Integer.toHexString(rgb).compareTo("fffff200") == 0) {
					temp = "white";
				}
				if(Integer.toHexString(rgb).compareTo("ff000000") == 0) {
					temp = "black";
				}
				for(int i=0; i < sizeOfPixel; i++){
					for(int j = 0; j < sizeOfPixel; j++){
						switch(temp){
						case "white":							
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), backgroundType.getRGB(i, j));
							break;
						case "black":
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rock.getRGB(i, j));
							break;
						default:
							background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rgb);
							break;
						}
					}
				}
			}
		}
		
		System.out.println(map.getEntities().size());
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
		if(rgb == img.getRGB(x+1, y) && x+1 < img.getWidth()){
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
