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
 * @version 1.0 A1
 */
public class MapGenerator { //Implement serialization
	private static final int sizeOfPixel = 16;
	/**
	 * Huge method which generates entities as boundaries in the Map object it returns
	 * @param fileName The filename of the image the map should be based on
	 * @return The map it created
	 */
	public static Map generateMap(String fileName){
		BufferedImage origin;
		try {
		    origin = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		    e.printStackTrace();
		    return generateBlankMap();
		}
		
		if(origin == null){
			return generateBlankMap();
		}
		int height = origin.getHeight();
		int width = origin.getWidth();
		if(height != 50 || width != 50){ //Temp limit, generates 800x800 map.
			return generateBlankMap();
		}
		BufferedImage background = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		int k = 1000;
		Map map = new Map();
		//Create map boundaries
			//Top boundary
			map.addEntity(new Enemy(900, -10, -10, width*sizeOfPixel+20, 10));
			//Left boundary
			map.addEntity(new Enemy(901, -10, 0, 10, height*sizeOfPixel));
			//Right boundary
			map.addEntity(new Enemy(902, width*sizeOfPixel, 0, 10, height*sizeOfPixel));
			//Bottom boundary
			map.addEntity(new Enemy(903, -10, height*sizeOfPixel, width*sizeOfPixel+20, 10));
		
		//Create map tiles, the format ARGB is used with hexcode
		//example "ff000000" gives A = ff, R = 00, G = 00, B = 00 which gives black
		for(int x = 0; x < height; x++){
			for(int y = 0; y < width; y++){
				int rgb = origin.getRGB(x, y);
				switch(Integer.toHexString(rgb)){
				case "ff000000": //Black
					map.addEntity(new Enemy(k, x*sizeOfPixel, y*sizeOfPixel, sizeOfPixel,sizeOfPixel));
					System.out.println("Created Enemy at: " + x*sizeOfPixel + "," + y*sizeOfPixel);
					
					break;
					
				default:	
					
					//System.out.println(rgb);
					break;
				}
				for(int i=0; i < sizeOfPixel; i++){
					for(int j = 0; j < sizeOfPixel; j++){
						background.setRGB(i+(x*sizeOfPixel), j+(y*sizeOfPixel), rgb);
					}
				}
				System.out.println();
				k++;
			}
			
		}
		
		
		
		System.out.println(map.getEntities().size());
		map.setBackground(background);
		return map;
		
	}
	/**
	 * Method used to create a empty map if something went wrong
	 * @return Empty Map with white background
	 */
	private static Map generateBlankMap(){
		Map map = new Map();
		map.setBackground(new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB));;
		return map;
	
	}
}
