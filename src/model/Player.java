package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
/**
 * Player class, extends the Entity Class. Used to store the data of YOUR player. Only one player per Game.
 * @author Victor Dahlberg.
 * @version 1.0
 */

public class Player extends Entity {
	private final int maxHP=100;
	private int dx, dy;
	private boolean attacking;
	private Weapon weapon;
	private TreeMap<Integer, ArrayList<BufferedImage>> sprites;
	private int facing=1;
	private ArrayList<Entity> closeObjects = new ArrayList<Entity>();
	
	/**
	 * Constructor.
	 * @param id ID of the entity.
	 * @param x The players x position.
	 * @param y The player y position.
	 * @param w The player's hitbox-width.
	 * @param h The player's hitbox height.
	 */
	public Player(int id, int x, int y, int w, int h){
		super(id, x, y, w, h, true);
		dx = dy = 0;
		hp=maxHP;
		weapon=new Weapon(50,10,20);
		sprites=new TreeMap<Integer, ArrayList<BufferedImage>>();
		loadImages();
		attacking = false;
	}
	
	//Dummy-tick
	public void tick(){}
	
	/**
	 * An override of the usual tick because this tick needs to know about the objects closeby so it can check for collission.
	 * @param closeObjects Possible object the Player can collide with.
	 */
	public void tick(ArrayList<Entity> closeObjects){
		this.closeObjects.clear();
		this.closeObjects.addAll(closeObjects);
		move(dx, dy);
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
	
	/**
	 * TEMPORARY. WILL BE CHANGED.
	 * Check's if the players next position, intersects with any object. If it doesnt, changes the x and y position. Recursivly uses move for vertical and horizontal speed.
	 * @param dx x-velocity
	 * @param dy y-velocity
	 */
	public void move(int dx, int dy){
		/*
		 * If the player is moving diagonally, has a x velocity and a y velocity.
		 * Call move recursivly with only the x velocity and then the y velocity.
		 * This is used so the player can "slide" against walls.
		 * So if you're moving diagonally and will intersect a wall, if you will intersect the wall horizontally, you can still move vertically and vice verse.
		 */
		if(dx != 0 && dy != 0){
			move(dx, 0);
			move(0, dy);
			return;
		}
		
		/*
		 * CHECK if collision. If no collision change the position.
		 */
		if(!collision(dx, dy)){
			this.x += dx;
			this.y += dy;
		}
	}
	
	/**
	 * Checks if the players NEXT position, x + dx, y + dy, intersects a closeby object.
	 * @return boolean, returns true if the player intersects an object, false otherwise.
	 */
	private boolean collision(int dx, int dy){
		Rectangle pNext = new Rectangle(x+dx, y+dy, w, h);
		for(Entity e : closeObjects){
			if(pNext.intersects(e.getBounds())){
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * Set the players horizontal velocity.
	 * @param dx the horizontal speed.
	 */
	public void setdx(int dx){
		this.dx = dx;
	}
	
	/**
	 * Set the vertical velocity.
	 * @param dy the vertical speed.
	 */
	public void setdy(int dy){
		this.dy = dy;
	}

	public void setAttacking(boolean state){
		attacking=state;
	}
	public boolean getAttacking(){
		return attacking;
	}

	public void setFacing(int facing){this.facing=facing;}
	public int getFacing(){return facing;}
	public Weapon getWeapon(){return weapon;}
	public TreeMap<Integer, ArrayList<BufferedImage>> getSprites(){return sprites;}
}