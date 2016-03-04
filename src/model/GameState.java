
package model;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import map.Map;

/**
 * Contains all the information about everything in the game. I.e Units, weapons, tiles etc.
 * @author Victor Dahlberg
 * @version 04-03-16
 */
public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private ArrayList<Enemy> enemies;
	private ArrayList<Entity> objInNode;
	private ArrayList<Weapon> arrows;
	private Enemy gotHit;
	private ArrayList<SpawnPoint> spawnPoints;
	private boolean alive;
	private boolean ready;
	private Player player;
	private int id;
	private Quadtree quadtree;;
	private Map map = null;
	private boolean gameOver;
	private boolean sendArrow;
	private String name;
	private String mapName="";
	private int nrPlayers=1;private int maxPlayers;
	// Keeps track of which players have been hit by an attack from an entity. Since they should only be hit once per attack
	
	
	/**
	 * Creates a new GameState, creates all the Lists that stores the various Entities.
	 */
	public GameState(){
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		arrows = new ArrayList<Weapon>();

		enemies=new ArrayList<Enemy>();

		gotHit = null;
		spawnPoints = new ArrayList<SpawnPoint>();
		alive = false;
		ready = false;
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,800,800));
	}
	
	/**
	 * 
	 * @return A list with all the enemies in the game.
	 */
	public List<Enemy> getTheEnemies(){
		return enemies;
	}

	
	/**
	 * 
	 * @param id 
	 * @param maxPlayers 
	 * @param map
	 */
	
	/**
	 * Setups the Gamestate with a map, new spawnpoints, it also creates the player.
	 * @param id The id to give the gamestate.
	 * @param maxPlayers The maximum amount of players in the current game.
	 * @param map The map which the game should be run on.
	 * @param mapName The name of the map
	 */
	public void setup(int id, int maxPlayers, Map map, String mapName){

		this.id = id;
		this.map = map;
		this.mapName=mapName;
		this.maxPlayers=maxPlayers;
		setChanged();
		notifyObservers();
		
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,map.getBackground().getWidth(),map.getBackground().getHeight()));
				
		//Add all entities from the map to gamestate
		for(Entity e : map.getTiles()){
			if(!gameObjects.contains(e)){
				gameObjects.add(e);
			}
		}
				
		for(SpawnPoint e : map.getSpawnPoints()){
			spawnPoints.add(e);
		}
		

		player=new Player(spawnPoints.get(id-1).getX(), spawnPoints.get(id-1).getY(), 40, 40);
		gameObjects.add(player);
		
		for(int i = 1; i < maxPlayers+1; i++){
			if(id != i){
				Enemy en = new Enemy(spawnPoints.get(i-1).getX(), spawnPoints.get(i-1).getY(), 40, 40);
				en.setID(i);
				gameObjects.add(en);
				enemies.add(en);
			}
			
		}
		
		
	}
	
	/**
	 * If the Game has started. do the "game-tick", otherwise notify the observers with no information.
	 */
	public void tick(){
		if(isAlive()){
			actualtick();
		} else {

			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Basically does everything in the game when it is active. It checks for collision, makes sure all objects to be rendered
	 * are in the proper List. It makes sure every object's "tick" is called. Makes the game move forward.
	 */
	public void actualtick(){
		//Clear the QuadTree every tick.
		quadtree.clear();
		gotHit=null;
		
		ArrayList<Weapon> arrowToDelete = new ArrayList<Weapon>();
		for(Weapon w : arrows){
			if(!w.isAttacking()){
				arrowToDelete.add(w);
			}
		} arrows.removeAll(arrowToDelete);
		
		ArrayList<Entity> weaponHandler = new ArrayList<Entity>();
		for(Entity e : gameObjects){
			if(e instanceof Weapon){
				weaponHandler.add(e);
			}
		}
		gameObjects.removeAll(weaponHandler);
		weaponHandler.clear();
		
		for(Entity e : gameObjects){
			if(e instanceof Unit){
				Unit unit = (Unit)e;
				Weapon w = unit.getWeapon();
				
				if(w instanceof Bow){
					weaponHandler.add(w);
					Weapon arrow = ((Bow) w).getNewArrow();
					if(arrow != null){
						weaponHandler.add(arrow);
						if(w.getOwner() instanceof Player){
							sendArrow = true;
						}
						
						arrows.add(arrow);
					}
									
				} else if (w.isAttacking() && w instanceof SweepSword){
					weaponHandler.add(w);
				} 
			} 
			
			
		}
		
		gameObjects.addAll(weaponHandler);
		gameObjects.addAll(arrows);
		
		
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
				
			} else if (e instanceof Weapon) {
				
				Weapon w = (Weapon)e;
		
				for(Entity ent : objInNode){
					//If the weapon intersects the Entity and the entity is an Enemy and the weapon hasnt already done damage during 1 cycle.
					if(e.getBounds().intersects(ent.getBounds()) && !w.getDmgDone() && w.getOwner() instanceof Player){
						
						if((ent instanceof Enemy)){
							//Set the enemies new hp to it's old hp minus the damage of the weapon.
							((Enemy)ent).setHP(((Enemy)ent).getHP() - w.getDmg());
							
							//Since damage was dealt, set so the weapon can't deal more damage dring this cycle.
							w.damageDone();
							
							//Add the enemy to the list which is used in the Client class.
							gotHit=(Enemy)ent;
							
							//We can't hit more than one object so break the loop if a hit occured.
							break;
						} else if(ent.isSolid() && !(ent instanceof Player)){
							w.damageDone();
						}
					} 
				}
				
				
				e.tick();
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
	//END OF ACTUALTICK.

	/**
	 * 
	 * @return The enemy which got hit, if an enemy got hit, otherwise null.
	 */
	public Enemy gotHit(){
		return gotHit;
	}
	
	/**
	 * 
	 * @return The complete list of every object in the game.
	 */
	public ArrayList<Entity> getList(){
		return gameObjects;
	}
	
	/**
	 * 
	 * @return A list of spawnpoints associated with the current map.
	 */
	public ArrayList<SpawnPoint> getSpawns(){
		return spawnPoints;
	}
	
	/**
	 * 
	 * @return the gamestates player.
	 */
	public Player returnPlayer(){
		return player;
	}
	
	/**
	 * 
	 * @return the gamestates ID.
	 */
	public int getID(){ 
		return id; 
	}
	
	/**
	 * 
	 * @return The background image.
	 */
	public BufferedImage getBackground(){
		return map.getBackground();
	}
	
	/**
	 * Starts the game.
	 */
	public void startGame(){
		alive = true;
		ready = false;
	}
	
	/**
	 * 
	 * @return true if the game is alive, false otherwise.
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * 
	 * @return true if the gamestate is ready to start, false otherwise.
	 */
	public boolean isReady(){
		return ready;
	}
	
	/**
	 * Sets the gamestate to be ready.
	 */
	public void setToReady(){
		ready = true;
	}
	
	/**
	 * Set gameover.
	 * @param state setgameover.
	 */
	public void setGameOver(boolean state){gameOver=state;}
	
	/**
	 * 
	 * @return true if the game is over. false otherwise.
	 */
	public boolean getGameOver(){return gameOver;}
	
	/**
	 * Set's the gamestates name.
	 * @param name the gamestates new name.
	 */
	public void setName(String name){this.name=name;}
	
	/**
	 * 
	 * @return the gamestates name.
	 */
	public String getName(){return name;}

	
	/**
	 * 
	 * @return true if there has been an arrow-attack. false otherwise.
	 */

	public boolean getNewBowAttack(){
		if(sendArrow){
			sendArrow = false;
			return true;
		} return false;
	}
	public String getMapName(){return mapName;}
	public Map getMap(){return map;}
	public void setNrPlayers(int nrPlayers){this.nrPlayers=nrPlayers;}
	public int getNrPlayers(){return nrPlayers;}
	public int getMaxNrPlayers(){return maxPlayers;}

}