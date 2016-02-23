
package model;
import java.util.ArrayList;
import java.util.Observable;

import map.Map;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private int numberOfPlayers;
	private Player player;
	private int id;
	
	public GameState(int numberOfPlayers, Map map){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
		
		//Add all entities from the map to gamestate
		for(Entity e : map.getEntities()){
			if(!gameObjects.contains(e)){
				gameObjects.add(e);
			}
		}
		
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
		Player player=null;
		for(Entity e : gameObjects){
				//Implementera collision
				if(e instanceof Player){ player = (Player) e; }
				e.tick();
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
	
	public ArrayList<Entity> getList(){return gameObjects;}
	public Player returnPlayer(){return player;}
	public void setID(int id){this.id=id;}
}