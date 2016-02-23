package model;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * <h1>Entity</h1>
 * The Entity Class is a model that stores data of a single objects that will be displayed on a JFrame.
 * Entity is an abstract class and therefore used as a superclass.
 * 
 * @author Victor Dahlberg
 * @version 1.0
 */
public abstract class Entity{
	private boolean solid;
	protected int x, y, w, h, rotation, hp;
	protected BufferedImage sprite;
	
	/**
	 * Constructor for Entity. 
	 * Temporarily sets the Sprite of the Image to null.
	 * @param id This is the id of the Entity.
	 * @param x The x-position of the Entity.
	 * @param y	The y-position of the Entity
	 * @param w	The width of the Entity.
	 * @param h	The height of the Entity
	 * @param solid Boolean that determines if the Entity will be solid, or able to move through.
	 */

	public Entity(int x, int y, int w, int h, boolean solid){
		this.x = x; this.y = y; this.w = w; this.h = h;
		this.solid = solid;
		sprite = null;
	}
	
	/**
	 * UNUSED
	 * @return BufferedImage sprite
	 */
	public BufferedImage getSprite(){
		return sprite;
	}
	/**
	 * UNUSED
	 * @param sprite
	 */
	public void setSprite(BufferedImage sprite){
		this.sprite = sprite;
	}
	
	/**
	 * 
	 * @return int The x-position of the Entity.
	 */
 	public int getX(){
		return x;
	}
	
 	/**
 	 * 
 	 * @return int The y-position of the Entity.
 	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @return int The Width of the Entity.
	 */
	public int getWidth(){
		return w;
	}
	
	/**
	 * 
	 * @return int The height of the Entity.
	 */
	public int getHeight(){
		return h;
	}
	
	/**
	 * Set's a new x-position to the Entity.
	 * @param x The new x-position
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Set's a new y-position to the Entity.
	 * @param y The new y-position.
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Returns a Rectangle, used to check collission.
	 * Check collission in front of the Object. So for example x + dx, as the parameter x.
	 * @param x The x-pos of the Rectangle.
	 * @param y The y-pos of The Rectangle.
	 * @return A Rectangle specified by the parameters with the same width and height as the Entity.
	 */
	public Rectangle getBounds(){
		return new Rectangle(x, y, w, h);
	}
	
	/**
	 * UNUSED
	 * Set a rotation-variable.
	 * @param rotation
	 */
	public void setRotVar(int rotation){
		this.rotation = rotation;
	}
	
	/**
	 * UNUSEd
	 * Get the rotation variable.
	 * @return int rotation
	 */
	public int getRotVar(){
		return rotation;
	}
	
	/**
	 * 
	 * @return int x, The x-pos of the center of the Entity.
	 */
	public int getCenterX(){
		return x+(getWidth()/2);
	}
	
	/**
	 * 
	 * @return int y, The y-pos of the center of the Entity.
	 */
	public int getCenterY(){
		return y+(getHeight()/2);
	}
	
	/**
	 * 
	 * @return true if the Entity is solid. Otherwise false.
	 */
	public boolean isSolid(){
		return solid;
	}
	
	/**
	 * Abstract class, "handle" the logic in this method in subclasses of Entity.
	 */
	public abstract void tick();

}