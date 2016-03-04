package model;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Message implements Externalizable{
	int code=-1;int id;int xPos;int yPos;int rotVar;int playerHP=-1;
	boolean attacking;
	int enemyID=-1;int enemyHP=-1;
	int maxNrPlayers=-1;
	String mapName, playerName;
	String type;
	boolean ready, tostart;
	boolean alive, gameOver;
	private int weapon; //1 = sword, 2 = Bow.
	
	
	public Message(){
		
	}
	public Message(int id, int xPos, int yPos, int rotVar, int weapon){
		this.id=id; this.xPos=xPos;
		this.yPos=yPos;this.rotVar=rotVar;
		ready=false;
		gameOver=false;
		this.weapon = weapon;
	}
	
	public int getCode(){return code;}
	public void setCode(int code){this.code=code;}
	public int getID(){return id;}
	public void setID(int id){
		this.id = id;
	}
	public int getXPos(){return xPos;}
	public int getYPos(){return yPos;}
	public int getRotVar(){return rotVar;}
	public boolean getAttacking(){return attacking;}
	public void setAttacking(boolean b){
		attacking = b;
	}
	public int getEnemeyID(){return enemyID;}
	public void setEnemyID(int enemyID){this.enemyID=enemyID;}
	public int getMaxNrPlayers(){return maxNrPlayers;}
	public void setMaxNrPlayers(int maxNrPlayers){this.maxNrPlayers=maxNrPlayers;}
	public void setEnemyHP(int enemyHP){this.enemyHP=enemyHP;}
	public int getEnemyHP(){return enemyHP;}
	public int getPlayerHP(){return playerHP;}
	public void setPlayerHP(int playerHP){this.playerHP=playerHP;}
	public boolean getReady(){return ready;}
	public void setReady(boolean state){ready=state;}
	public String getMapName(){return mapName;}
	public void setMapName(String mapName){this.mapName=mapName;}
	public String getMapType(){return type;}
	public void setMapType(String type){this.type=type;}
	public boolean getAlive(){return alive;}
	public void setAlive(boolean state){alive=state;}
	public boolean getGameOver(){return gameOver;}
	public void setGameOver(boolean state){gameOver=state;}
	public String getPlayerName(){return playerName;}
	public void setPlayerName(String playerName){this.playerName=playerName;}
	
	public void setToStart(boolean b){
		tostart = b;
	}
	
	public boolean toStart(){
		return tostart;
	}
	
	public void setWeaponByID(int weapon){
		this.weapon = weapon;
	}
	
	public int getWeaponByID(){
		return weapon;
	}
	
	@Override
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		code=arg0.readInt();
		id=arg0.readInt();
		xPos=arg0.readInt();
		yPos=arg0.readInt();
		rotVar=arg0.readInt();
		attacking=arg0.readBoolean();
		enemyID=arg0.readInt();
		maxNrPlayers=arg0.readInt();
		enemyHP=arg0.readInt();
		playerHP=arg0.readInt();
		ready=arg0.readBoolean();
		mapName=(String)arg0.readObject();
		type=(String)arg0.readObject();
		tostart = arg0.readBoolean();
		alive=arg0.readBoolean();
		gameOver=arg0.readBoolean();
		playerName=(String)arg0.readObject();
		weapon = arg0.readInt();
	}
	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		arg0.writeInt(code);
		arg0.writeInt(id);
		arg0.writeInt(xPos);
		arg0.writeInt(yPos);
		arg0.writeInt(rotVar);
		arg0.writeBoolean(attacking);
		arg0.writeInt(enemyID);
		arg0.writeInt(maxNrPlayers);;
		arg0.writeInt(enemyHP);
		arg0.writeInt(playerHP);
		arg0.writeBoolean(ready);
		arg0.writeObject(mapName);
		arg0.writeObject(type);
		arg0.writeBoolean(tostart);
		arg0.writeBoolean(alive);
		arg0.writeBoolean(gameOver);
		arg0.writeObject(playerName);
		arg0.writeInt(weapon);
	}
}
