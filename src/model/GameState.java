
package model;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import map.Map;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private ArrayList<Entity> objInNode;
	private ArrayList<Weapon> arrows;
	private List<Enemy> theEnemies;
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
	// Keeps track of which players have been hit by an attack from an entity. Since they should only be hit once per attack
	
	public GameState(){
		gameObjects=new ArrayList<Entity>();
		objInNode = new ArrayList<Entity>();
		arrows = new ArrayList<Weapon>();
		theEnemies = Collections.synchronizedList(new ArrayList<Enemy>());
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
		ArrayList<Enemy> ens = new ArrayList<Enemy>();
		for(Entity e : gameObjects){
			if(e instanceof Enemy){
				ens.add((Enemy)e);
			}
		} return ens;
		//return theEnemies;
	}

	
	public void setup(int id, int maxPlayers, Map map){
		this.id = id;
		this.map = map;

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

/*		Random randomGenerator = new Random();
		
		if(spawnPoints.size() > numberOfPlayers){
			int spawnPointIndex = randomGenerator.nextInt(spawnPoints.size());
			spawnPoints.get(spawnPointIndex).setUsed();
			player = new Player(spawnPoints.get(spawnPointIndex).getX(), spawnPoints.get(spawnPointIndex).getY(), 40, 40);
			
		}else{
			player=new Player((int)(Math.random()*400+200), (int)(Math.random()*400+200), 40, 40);
			
		}
		gameObjects.add(player);

*/		

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
		if(isAlive()){
			actualtick();
		} else {

			setChanged();
			notifyObservers();
		}
	}
	
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
							System.out.println("Stuck here?");
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
	
	public void addEntity(Entity e){
		if(e != null){
			gameObjects.add(e);
		}
	}
	
	public Enemy gotHit(){return gotHit;}
	public ArrayList<Entity> getList(){return gameObjects;}
	public ArrayList<SpawnPoint> getSpawns(){return spawnPoints;}
	public Player returnPlayer(){return player;}
	public int getID(){ return id; }
	public BufferedImage getBackground(){
		return map.getBackground();
	}
	
	public void startGame(){
		alive = true;
		ready = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public void setToReady(){
		ready = true;
	}
	public void setGameOver(boolean state){gameOver=state;}
	public boolean getGameOver(){return gameOver;}
	public void setName(String name){this.name=name;}
	public String getName(){return name;}
	public boolean getNewBowAttack(){
		if(sendArrow){
			sendArrow = false;
			return true;
		} return false;
	}
	
}