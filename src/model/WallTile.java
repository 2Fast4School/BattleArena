package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WallTile extends Entity {
	
	public WallTile(int x, int y, int w, int h){
		super(x, y, w ,h, true);
	}
	
	@Override
	public void tick() {
		
	}
	
}
