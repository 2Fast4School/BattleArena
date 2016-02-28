
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
	
	private int numberOfPlayers;
	private Player player;
	private int id;
	private Quadtree quadtree;;
	private Map map = null;
	// Keeps track of which players have been hit by an attack from an entity. Since they should only be hit once per attack
	
	public GameState(int numberOfPlayers, Map map){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		gotHit = null;
		spawnPoints = new ArrayList<spawnPoint>();
		this.map = map;
		
		
		//Add dummy-enemies to start with. These enemies will get initilized with real values one's there's proper info from the server about them.
		for(int i = 0; i < numberOfPlayers-1; i++){
			gameObjects.add(new Enemy(100,100,40,40));
		}
		//Init the quadtree with the size of the screen.
		quadtree = new Quadtree(new Rectangle(0,0,map.getBackground().getWidth(),map.getBackground().getHeight()));
		
		//Add all entities from the map to gamestate
		for(Entity e : map.getTiles()){
			if(!gameObjects.contains(e)){
				gameObjects.add(e);
			}
		}
		
		for(spawnPoint e : map.getSpawnPoints()){
			spawnPoints.add(e);
		}
		//System.out.println("Number of spawn points: " + spawnPoints.size());

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
	public void setup(){
		Random randomGenerator = new Random();
		
		
		int spawnPointIndex = randomGenerator.nextInt(spawnPoints.size());
		spawnPoints.get(spawnPointIndex).setUsed();
		player = new Player(spawnPoints.get(spawnPointIndex).getX(), spawnPoints.get(spawnPointIndex).getY(), 40, 40);
		//player=new Player((int)(Math.random()*400+200), (int)(Math.random()*400+200), 40, 40);
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
	public void setID(int id){this.id=id;}
	public int getID(){ return id; }
	public int getNrOfPlayers(){return numberOfPlayers;}
	public BufferedImage getBackground(){
		return map.getBackground();
	}
}