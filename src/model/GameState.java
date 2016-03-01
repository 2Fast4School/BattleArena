
package model;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import map.Map;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private ArrayList<Entity> objInNode;
	private Enemy gotHit;
	private ArrayList<spawnPoint> spawnPoints;
	
	private Player player;
	private int id;
	private Quadtree quadtree;;
	private Map map = null;
	// Keeps track of which players have been hit by an attack from an entity. Since they should only be hit once per attack
	
	public GameState(){
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		gotHit = null;
		spawnPoints = new ArrayList<spawnPoint>();
		
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,800,800));
	}
	
	/**
	 * 
	 * @return A list with all the enemies in the game.
	 */
	public ArrayList<Enemy> getTheEnemies(){
		ArrayList<Enemy> ens = new ArrayList<Enemy>();
		for(Entity e : gameObjects){
			if(e instanceof Enemy){
				ens.add((Enemy)e);
			}
		} return ens;
	}
	
	public void setup(int id, int maxPlayers, Map map){
		this.id = id;
		this.map = map;
		
		//Add all entities from the map to gamestate
		for(Tile e : map.getTiles()){
			if(!gameObjects.contains(e)){
				gameObjects.add(e);
			}
		}
		
		for(spawnPoint e : map.getSpawnPoints()){
			spawnPoints.add(e);
		}
		
			
		
		player=new Player(spawnPoints.get(id-1).getX(), spawnPoints.get(id-1).getY(), 40, 40);
		gameObjects.add(player);
		
		for(int i = 1; i < maxPlayers+1; i++){
			if(id != i){
				Enemy en = new Enemy(spawnPoints.get(i-1).getX(), spawnPoints.get(i-1).getY(), 40, 40);
				en.setID(i);
				gameObjects.add(en);
			}
			
		}
		
		
	}
	public void tick(){
		//Clear the QuadTree every tick.
		quadtree.clear();
		gotHit=null;
		
		//If a weapon is active, i.e attacking, add it to the gameObjects list.
		for(Enemy e : getTheEnemies()){
			if(e.getWeapon().isAttacking()){
				gameObjects.remove(e.getWeapon());
				gameObjects.add(e.getWeapon());
			} else {
				gameObjects.remove(e.getWeapon());
			}
		}
		
		if(player.getWeapon().isAttacking()){
			gameObjects.remove(player.getWeapon());
			gameObjects.add(player.getWeapon());
		} else {
			gameObjects.remove(player.getWeapon());
		}
		
		
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
		
				for(Entity enemy : objInNode){
					//If the weapon intersects the Entity and the entity is an Enemy and the weapon hasnt already done damage during 1 cycle.
					if(e.getBounds().intersects(enemy.getBounds()) && enemy instanceof Enemy && !w.getDmgDone()){
						
						//Set the enemies new hp to it's old hp minus the damage of the weapon.
						((Enemy)enemy).setHP(((Enemy)enemy).getHP() - w.getDmg());
						
						//Since damage was dealt, set so the weapon can't deal more damage dring this cycle.
						w.damageDone();
						
						//Add the enemy to the list which is used in the Client class.
						gotHit=(Enemy)enemy;
						
						//We can't hit more than one object so break the loop if a hit occured.
						break;
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
	
	public void addEntity(Entity e){
		if(e != null){
			gameObjects.add(e);
		}
	}
	
	public Enemy gotHit(){return gotHit;}
	public ArrayList<Entity> getList(){return gameObjects;}
	public ArrayList<spawnPoint> getSpawns(){return spawnPoints;}
	public Player returnPlayer(){return player;}
	public int getID(){ return id; }
	public BufferedImage getBackground(){
		return map.getBackground();
	}
}