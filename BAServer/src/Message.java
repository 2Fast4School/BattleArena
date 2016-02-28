
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Message implements Externalizable{
	int code=-1;int id;int xPos;int yPos;int rotVar;int playerHP=-1;
	boolean attacking;
	int enemyID=-1;int enemyHP=-1;
	int maxNrPlayers=-1;
	public Message(){
		
	}
	public Message(int id, int xPos, int yPos, int rotVar, boolean attacking){
		this.id=id;this.attacking=attacking;this.xPos=xPos;
		this.yPos=yPos;this.rotVar=rotVar;
	}
	public int getCode(){return code;}
	public void setCode(int code){this.code=code;}
	public int getID(){return id;}
	public int getXPos(){return xPos;}
	public int getYPos(){return yPos;}
	public int getRotVar(){return rotVar;}
	public boolean getAttacking(){return attacking;}
	public int getEnemeyID(){return enemyID;}
	public void setEnemyID(int enemyID){this.enemyID=enemyID;}
	public int getMaxNrPlayers(){return maxNrPlayers;}
	public void setMaxNrPlayers(int maxNrPlayers){this.maxNrPlayers=maxNrPlayers;}
	public void setEnemyHP(int enemyHP){this.enemyHP=enemyHP;}
	public int getEnemyHP(){return enemyHP;}
	public int getPlayerHP(){return playerHP;}
	public void setPlayerHP(int playerHP){this.playerHP=playerHP;}
	
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
	}
}
