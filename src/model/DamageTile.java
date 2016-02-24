package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * The logic tiles to represent damage when stepping on them, for example lava or bushes
 * 
 * @author Alexander Erenstedt
 *
 */
public class DamageTile extends Entity {
	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public DamageTile(int x, int y, int w, int h){
		super(x, y, w ,h, false);
	}
	
	@Override
	public void tick() {
		
	}
	
}
