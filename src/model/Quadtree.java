package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Quadtree</h1>
 * This class splits up the game area to smaller pieces to reduce the area that has to be checked for collission.<p>
 * The Quadtree class is heavily based of off
 * <a href="http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374">this article. </a>
 * @author  Victor Dahlberg
 * @version 21-02-16
 *
 */
public class Quadtree {
	
	private int MAX_OBJECTS = 50;
	private int MAX_LEVELS = 5;
	 
	private int level;
	private List<Entity> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;
	 
	/**
	 * Creates a new Quadtree. 
	 * @param bounds The Bounds of the quadtree in form of a Rectangle. Should be the size of the screen.
	 */
	public Quadtree(Rectangle bounds) {
	  	this.level = 0;
	  	this.bounds = bounds;
	  	objects = new ArrayList<Entity>();
	  	nodes = new Quadtree[4];
	}

	/**
	 * The Quadtree creates a new Quadtree using this constructor. So a Quadtree can contain severl Quadtrees.
	 * @param level which level the new quadtree should be created at.
	 * @param bounds the bounds (size) of the level.
	 */
	private Quadtree(int level, Rectangle bounds){
		this.level = level;
	  	this.bounds = bounds;
	  	objects = new ArrayList<Entity>();
	  	nodes = new Quadtree[4];
	}
	
	
	/**
	 * Clears the entire Quadtree.
	 */
	public void clear(){
		objects.clear();
		
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i] != null){
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	/**
	 * Splits a Quadtree and create four new ones in the Quadtree.
	 */
	private void split(){
		int w = (int)(bounds.getWidth() / 2);
		int h = (int)(bounds.getHeight() / 2);
		int x = (int)(bounds.getX());
		int y = (int)(bounds.getY());
		
		nodes[0] = new Quadtree(level+1, new Rectangle(x+w, y, w, h));
		nodes[1] = new Quadtree(level+1, new Rectangle(x, y, w, h));
		nodes[2] = new Quadtree(level+1, new Rectangle(x, y+h, w, h));
		nodes[3] = new Quadtree(level+1, new Rectangle(x+w, y+h, w, h));
	}
	
	/**
	 * 
	 * @param e, entity
	 * @return The index of the node of the Quadtree the specified Entity is currently in. 
	 */
	private int getIndex(Entity e){
		int index = -1;
		double centerX = bounds.getX() + bounds.getWidth() / 2;
		double centerY = bounds.getY() + bounds.getHeight() / 2;
		boolean topQ = (e.getY() < centerY && e.getY() + e.getHeight() < centerY);
		boolean botQ = (e.getY() > centerY);
		
		if(e.getX() < centerX && e.getX() + e.getWidth() < centerX){
			if(topQ){
				index = 1;
			} else if(botQ){
				index = 2;
			}
		} else if(e.getX() > centerX){
			if(topQ){
				index = 0;
			} else if(botQ){
				index = 3;
			}
		}	
		return index;
	}
	
	/**
	 * Inserts the Entity e in the correct place in the Quadtree.
	 * @param e The Entity to add
	 */
	public void insert(Entity e){
		if(nodes[0] != null){
			int index = getIndex(e);
			
			if(index != -1){
				nodes[index].insert(e);
				return;
			}
		}
		
		objects.add(e);
		
		if(objects.size() > MAX_OBJECTS && level < MAX_LEVELS){
			if(nodes[0] == null){
				split();
			}
			int i = 0;
			while(i < objects.size()){
				int index = getIndex((Entity)objects.get(i));
				
				if(index != -1){
					nodes[index].insert((Entity)objects.remove(i));
				} else {
					i++;
				}
			}
		}
	}
	
	/**
	 * Returns a list of objects in the same node as the Entity e, not returning the specified Entity.
	 * @param returnObjects the List which to add Entities in the nodes to.
	 * @param e The specific Entity where you want to return the objects in the same node as this Entity.
	 * @return a list of the objects in the node where Entity e belongs.
	 */
	public ArrayList<Entity> retrive(ArrayList<Entity> returnObjects, Entity e){
		int index = getIndex(e);
		if(index != -1 && nodes[0] != null){
			nodes[index].retrive(returnObjects, e);
		}
		
		returnObjects.addAll(objects);
		returnObjects.remove((Entity)e);
		return returnObjects;
	}
	
	
}
