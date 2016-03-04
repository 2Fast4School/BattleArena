package model;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Message implements Externalizable{
	int code=-1;int id;int xPos;int yPos;int rotVar;int playerHP=-1;
	boolean attacking;
	int enemyID=-1;int enemyHP=-1;
	int maxNrPlayers=-1;int nrPlayers;
	String mapName, playerName;
	String type;
	boolean ready, tostart;
	boolean alive, gameOver;
	private int weapon; //1 = sword, 2 = Bow.
	
	/**
	 * Contructor of message, does not init any variables.
	 */
	public Message(){
	}
	

	/**
	 * Constructor of message
	 * @param id the id the message should represent
	 * @param xPos the Xpos to be sent
	 * @param yPos the Ypos to be sent
	 * @param rotVar the rotation variable to be sent
	 * @param weapon the weapon type to be sent
	 */
	public Message(int id, int xPos, int yPos, int rotVar, int weapon){
		this.id=id; this.xPos=xPos;
		this.yPos=yPos;this.rotVar=rotVar;
		ready=false;
		gameOver=false;
		this.weapon = weapon;
	}
	
	/**
	 * Used to get the OP-code set in the message
	 * @return returns the OP-code
	 */
	public int getCode(){return code;}
	
	/**
	 * Used to set the OP-code in the message
	 * @param code the desired OP-code
	 */
	public void setCode(int code){this.code=code;}
	
	/**
	 * Returns the ID of the message
	 * @return the ID
	 */
	public int getID(){return id;}
	
	/**
	 * Used to set the ID of the message
	 * @param id the desired ID
	 */
	public void setID(int id){
		this.id = id;
	}
	
	/**
	 * 
	 * @return the Xpos sent
	 */
	public int getXPos(){return xPos;}
	
	/**
	 * 
	 * @return the Ypos sent
	 */
	public int getYPos(){return yPos;}
	
	/**
	 * 
	 * @return the rotationvariable sent
	 */
	public int getRotVar(){return rotVar;}
	
	/**
	 * 
	 * @return if the weapon is attacking
	 */
	public boolean getAttacking(){return attacking;}
	
	/**
	 * 
	 * @param b sets the attacking boolean
	 */
	public void setAttacking(boolean b){
		attacking = b;
	}
	
	/**
	 * 
	 * @return returns the enemyID
	 */
	public int getEnemeyID(){return enemyID;}
	
	/**
	 * 
	 * @param enemyID used to set the enemyID
	 */
	public void setEnemyID(int enemyID){this.enemyID=enemyID;}
	
	/**
	 * 
	 * @return the max number of players
	 */
	public int getMaxNrPlayers(){return maxNrPlayers;}
	
	/**
	 * sets the maximum number of players
	 * @param maxNrPlayers the desired maximum number of players
	 */
	public void setMaxNrPlayers(int maxNrPlayers){this.maxNrPlayers=maxNrPlayers;}
	
	/**
	 * Sets the new enemy hp
	 * @param enemyHP the desired enemy hp
	 */
	public void setEnemyHP(int enemyHP){this.enemyHP=enemyHP;}
	
	/**
	 * Returns the enemy hp
	 * @return the enemy hp
	 */
	public int getEnemyHP(){return enemyHP;}
	
	/**
	 * Returns the player hp
	 * @return the player hp
	 */
	public int getPlayerHP(){return playerHP;}
	
	/**
	 * Sets the player hp
	 * @param playerHP the desired player hp
	 */
	public void setPlayerHP(int playerHP){this.playerHP=playerHP;}
	
	/**
	 * Checks if the client(ID) is ready
	 * @return if the client is ready
	 */
	public boolean getReady(){return ready;}
	
	/**
	 * Sets your client to ready
	 * @param state your current state of readyness
	 */
	public void setReady(boolean state){ready=state;}
	
	/**
	 * Returns the mapname
	 * @return the mapname
	 */
	public String getMapName(){return mapName;}
	
	/**
	 * Sets the mapname
	 * @param mapName the desired mapname
	 */
	public void setMapName(String mapName){this.mapName=mapName;}
	
	/**
	 * Gets the desired maptype
	 * @return the map type
	 */
	public String getMapType(){return type;}
	
	/**
	 * Sets the maptype
	 * @param type the desired map type
	 */
	public void setMapType(String type){this.type=type;}
	
	/**
	 * Check if the enemy(ID) is alive
	 * @return if the enemy(ID) is alive
	 */
	public boolean getAlive(){return alive;}
	
	/**
	 * Set if player is alive
	 * @param state if the player is alive
	 */
	public void setAlive(boolean state){alive=state;}
	
	/**
	 * Check if the game is over
	 * @return if the game is over
	 */
	public boolean getGameOver(){return gameOver;}
	
	/**
	 * Set if the game is over
	 * @param state if the game is over
	 */
	public void setGameOver(boolean state){gameOver=state;}
	
	/**
	 * Returns the playername(ID)
	 * @return the playername
	 */
	public String getPlayerName(){return playerName;}
	
	/**
	 * Set your playername
	 * @param playerName desired playername
	 */
	public void setPlayerName(String playerName){this.playerName=playerName;}
	
	/**
	 * Sets the number of players
	 * @param nrPlayers the desired number of players
	 */
	public void setNrPlayers(int nrPlayers){this.nrPlayers=nrPlayers;}
	
	/**
	 * Get the number of players
	 * @return number of players
	 */
	public int getNrPlayers(){return nrPlayers;}
	
	/**
	 * The server sets this to start game
	 * @param b true/false if the game should start
	 */
	public void setToStart(boolean b){
		tostart = b;
	}
	
	/**
	 * If the game should start
	 * @return if the game should start
	 */
	public boolean toStart(){
		return tostart;
	}
	
	/**
	 * Maps an ID to a weapon
	 * @param weapon the desired ID of the weapon
	 */
	public void setWeaponByID(int weapon){
		this.weapon = weapon;
	}
	
	/**
	 * Get the weaponID
	 * @return the weaponID
	 */
	public int getWeaponByID(){
		return weapon;
	}
	
	/**
	 * Implements the readExtrenal
	 * @param arg0 The ObjectInput to read from
	 * @throws IOexception
	 * @throws ClassNotFoundException
	 */
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
		nrPlayers=arg0.readInt();
	}
	/**
	 * Implements the write External
	 * @param arg0 the ObjectOutput to read from
	 * @throws IOException
	 */
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
		arg0.writeInt(nrPlayers);
	}
}
