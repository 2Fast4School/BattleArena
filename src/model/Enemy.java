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
	/**
	 * Sets wether the Enemy is attacking or not.
	 * @param state : boolean
	 */
	public void setAttacking(boolean state){
		attacking=state;
	}
	/**
	 * Returns wether the Enemy is currently attacking
	 * @return attacking : boolean
	 */
	public boolean getAttacking(){
		return attacking;
	}
	/**
	 * Loads in all the sprites associated with the Enemy's character to a TreeMap.
	 * The keys to the map are which direction the character is facing, IE: (R)ight, (L)eft etc.
	 * The associated value is an ArrayList containing all sprites comprising the attack animation,
	 * where [0] is used as regular standing animation. 
	 */
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
	/**
	 * Sets the direction the Enemy's sprite should be facing
	 * @param facing : int(0-3) 0=Right, 1=Left, 2=Down, 3=Up
	 */
	public void setFacing(int facing){this.facing=facing;}
	/**
	 * Returns the Enemy's sprite's current facing direction
	 * @return facing : int(0-3) 0=Right, 1=Left, 2=Down, 3=Up
	 */
	public int getFacing(){return facing;}
	/**
	 * Returns the Enemy's Weapon
	 * @return weapon : Weapon
	 */
	public Weapon getWeapon(){return weapon;}
	/**
	 * Returns an ArrayList containing the ID:s of all other Enemys hit by
	 * this Enemy's latest attack
	 * @return idsHitByAttack : ArrayList(Integer)
	 */
	public TreeMap<Integer, ArrayList<BufferedImage>> getSprites(){return sprites;}
	
}