
package model;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private ArrayList<Entity> objInNode;
	private int numberOfPlayers;
	private Player player;
	private int id;
	private Quadtree quadtree;
	// Keeps track of which players have been hit by an attack from an entity. Since they should only be hit once per attack
	
	public GameState(int numberOfPlayers, int id){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		this.id = id;
		for(int i = 0; i < numberOfPlayers-1; i++){
			gameObjects.add(new Enemy(0,0,20,20));
		}
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,800,800));
	}
	
	public ArrayList<Enemy> getTheEnemies(){
		ArrayList<Enemy> ens = new ArrayList<Enemy>();
		for(Entity e : gameObjects){
			if(e instanceof Enemy){
				ens.add((Enemy)e);
			}
		} return ens;
	}
	public void setup(){
		player=new Player((int)(Math.random()*400+200), (int)(Math.random()*400+200), 20, 20);
		gameObjects.add(player);
		
		//Generate 50^2 16x16 entities
		/*
		int k = 0;
		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				gameObjects.add(new Enemy(k, 16*i, 16*j, 16, 16 ));
				k++;
			}
		}
		*/
		
	}
	
	public void tick(){
		
		Player player = null;
		//Clear the QuadTree every tick.
		quadtree.clear();
		
		//Insert every object in the quadtree.
		for(Entity e : gameObjects){
			quadtree.insert(e);
		}
		
		
		//This loop will go through every object the game has.
		for(Entity e : gameObjects){
			objInNode.clear();
			objInNode = quadtree.retrive(objInNode, e);
			
			//We actually only need to do something when 'e' is the Player.
			if(e instanceof Player){
				player = (Player)e;
				
				/* When we check collision for the player movement, we need to check the players NEXT position.
				*  We can't do it here nicely, so instead the player checks it's own movement in the tick method.
				*/
				player.tick(objInNode);
				
			} else {
				e.tick(); //If 'e' isn't the Player. Just tick as usual.
			}
		}
		
		setChanged();
		if(player != null){
			notifyObservers(player);
		}else {
			notifyObservers();
		}		
	}
	
	public ArrayList<Entity> getList(){return gameObjects;}
	public Player returnPlayer(){return player;}
	public void setID(int id){this.id=id;}
	public int getID(){ return id; }
	
}