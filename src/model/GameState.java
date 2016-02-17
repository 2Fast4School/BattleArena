
package model;
import java.util.ArrayList;
import java.util.Observable;

public class GameState extends Observable{
	private ArrayList<Entity> gameObjects;
	private int numberOfPlayers;
	private Player player;
	private int id;
	
	public GameState(int numberOfPlayers){
		this.numberOfPlayers=numberOfPlayers;
		gameObjects=new ArrayList<Entity>();
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