package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
	private final int maxHP=100;
	private boolean attacking=false;
	private Weapon weapon;
	private TreeMap<Integer, ArrayList<BufferedImage>> sprites;
	private int facing=1;
	
	public Enemy(int id, int x, int y, int w, int h){
		super(id, x, y, w ,h, true);
		hp=maxHP;
		weapon=new Weapon(50,10,20);
		sprites=new TreeMap<Integer, ArrayList<BufferedImage>>();
		loadImages();
	}

	public void tick() {
	
	}

	public void setAttacking(boolean state){
		attacking=state;
	}
	public boolean getAttacking(){
		return attacking;
	}
	
	public void loadImages(){
		try{
			ArrayList<BufferedImage> list=new ArrayList<BufferedImage>();
			BufferedImage sprite;
			String direction="R";
			for(int n=0;n<4;n++){
				list=new ArrayList<BufferedImage>();
				if(n==0){direction="R";}
				else if(n==1){direction="L";}
				else if(n==2){direction="D";}
				else if(n==3){direction="U";}
				for(int m=1;m<7;m++){
					sprite = ImageIO.read(getClass().getResourceAsStream("/C1_"+direction+m+".gif"));
					list.add(sprite);
				}
				sprites.put(n, list);
			}
		}catch(IOException e){}
	}
	public void setFacing(int facing){this.facing=facing;}
	public int getFacing(){return facing;}
	public Weapon getWeapon(){return weapon;}
	public TreeMap<Integer, ArrayList<BufferedImage>> getSprites(){return sprites;}
}