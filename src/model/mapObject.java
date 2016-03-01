package model;

import java.awt.image.BufferedImage;

public class mapObject extends Entity {
	
	public mapObject(int x, int y, int w, int h){
		super(x, y, w ,h, true);
		super.setSprite(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
}
