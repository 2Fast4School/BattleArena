package model;

import java.io.Serializable;

public class Message implements Serializable{
	private int code;
	private int id;
	private int xPos;
	private int yPos;
	private int rotVar;
	private boolean attacking;
	private int enemyID;
	public Message(int code){
		this.code=code;
	}
	public Message(int code, int id){
		this.code=code;this.id=id;
	}
	public Message(int id, int xPos, int yPos, int rotVar, boolean attacking){
		this.id=id;this.xPos=xPos;this.yPos=yPos;
		this.rotVar=rotVar;this.attacking=attacking;
	}
	public int getCode(){return code;}
	public int getID(){return id;}
	public int getXPos(){return xPos;}
	public int getYPos(){return yPos;}
	public int getRotVar(){return rotVar;}
	public boolean getAttacking(){return attacking;}
	public void setCode(int code){this.code=code;}
	public int getEnemyID(){return enemyID;}
	public void setEnemeyID(int enemyID){this.enemyID=enemyID;}
}
