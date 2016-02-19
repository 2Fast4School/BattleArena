
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
	
	public GameState(int numberOfPlayers){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,800,800));
	}
	
	public void setup(){
		player=new Player(id, (id+1)*100, (id+1)*100, 20, 20);
		gameObjects.add(player);
		
		for(int n=0; n<numberOfPlayers;n++){
			if(n!=id){
				gameObjects.add(new Enemy(n, (n+1)*100, (n+1)*100, 20, 20));
			}
		}
		
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
				//We store some variables just for easy access.
				player = (Player)e;
				//The player is currently executing an attack.
				if(player.getAttacking()){
					//Variables just for easý access.
					int rot = player.getRotVar();
					int wLen = player.getWeapon().getLength();
					int wWid = player.getWeapon().getWidth();
							
					//Creates a Rectangle at the tip of the sword. The Rectangle is a square with the side the size of the swords width.
					Rectangle wBounds = new Rectangle(player.getCenterX() + wLen*(int)Math.cos(rot) - (wWid / 2),
								player.getCenterY() + wLen*(int)Math.sin(rot) - (int)(wWid / 2), wWid, wWid);
					
					//So if an attack is going on, we see if the Rectangle wBoudns intersects with any of the closeby Enemys, and then lowers the HP.
					//Attacks only hit a target once, so we only do damage to a particular ID if they aren't in getHitByList.
					for(Entity f : objInNode){
						if(wBounds.intersects(f.getBounds()) && f instanceof Enemy && !player.getHitByList().contains(f.getID())){
							f.setHP(f.getHP() - player.getWeapon().getDamage());
							player.getHitByList().add(f.getID());
						}
					}
				}
				else{player.getHitByList().clear();player.setHasSentHP(false);}	// Attack is over, clear list of who was hit by it.
				
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
}