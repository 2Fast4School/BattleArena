
package model;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private int numberOfPlayers;
	private Player player;
	private int id;
	private Quadtree quadtree;
	
	public GameState(int numberOfPlayers){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
		quadtree = new Quadtree(0, new Rectangle(0,0,800,800));
	}
	
	public void setup(){
		player=new Player(id, (id+1)*100, (id+1)*100, 20, 20);
		gameObjects.add(player);
		
		for(int n=0; n<numberOfPlayers;n++){
			if(n!=id){
				gameObjects.add(new Enemy(n, (n+1)*100, (n+1)*100, 20, 20));
			}
		}
	}
	
	public void tick(){
		Player player=null;
		quadtree.clear();
		for(Entity e : gameObjects){
			quadtree.insert(e);
		}
		
		ArrayList<Entity> returnObjects = new ArrayList<Entity>();
		for(Entity e : gameObjects){
			returnObjects.clear();
			quadtree.retrive(returnObjects, e);
			for(Entity e2 : returnObjects){
				if(e.getBounds().intersects(e2.getBounds())){
					//Kollision, gör något.
				}
			}
		}
		
		
		for(Entity e : gameObjects){
				//Implementera collision
				if(e instanceof Player){ player = (Player) e; }
				
				e.tick();
				setChanged();
				if(player != null){
					notifyObservers(player);
				}else {
					notifyObservers();
				}
		}
	}
	
	public ArrayList<Entity> getList(){return gameObjects;}
	public Player returnPlayer(){return player;}
	public void setID(int id){this.id=id;}
}