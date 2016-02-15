package ArenaFighter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public abstract class Entity {
	private boolean solid;
	protected int id, x, y, w, h, rotation;
	private BufferedImage sprite;
	
	
	
	public Entity(int id, int x, int y, int w, int h, boolean solid){
		this.id=id;this.x = x; this.y = y; this.w = w; this.h = h;
		this.solid = solid;
		sprite = null;
	}
	
	
	public BufferedImage getSprite(){
		return sprite;
	}
	
	public void setSprite(BufferedImage sprite){
		this.sprite = sprite;
	}
	
	public int getID(){
		return id;
	}
	
 	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return w;
	}
	
	public int getHeight(){
		return h;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public Rectangle getBounds(int x, int y, int w, int h){
		return new Rectangle(x , y, w, h);
	}
	
	public void setRotVar(int rotation){
		this.rotation = rotation;
	}
	
	public int getRotVar(){
		return rotation;
	}
	
	public int getCenterX(){
		return x+(getWidth()/2);
	}
	
	public int getCenterY(){
		return y+(getHeight()/2);
	}
	
	public abstract void tick();
}