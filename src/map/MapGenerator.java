package map;

import model.*;
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
		int k = 0;
		Map map = new Map();
		for(int x = 0; x < height; x++){
			for(int y = 0; y < width; y++){
				int rgb = origin.getRGB(x, y);
				switch(rgb){
				case -16777216: //Black ARGB=(0,0,0,0)
					map.addEntity(new Enemy(k, x*16, y*16, 16,16));
					System.out.println("Created Enemy at: " + x*16 + "," + y*16);
					break;
					
				default:	
					
					//System.out.println(rgb);
					break;
				}
				for(int i=0; i < 16; i++){
					for(int j = 0; j < 16; j++){
						background.setRGB(i+(x*16), j+(y*16), rgb);
					}
				}
				k++;
			}
			
		}
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
