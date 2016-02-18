package model;

public class Weapon{
	private int length;
	private int width;
	private int damage;
	
	public Weapon(int length, int width, int damage){
		this.length=length;
		this.width=width;
		this.damage=damage;
	}
	
	public int getLength(){return length;}
	public int getWidth(){return width;}
	public int getDamage(){return damage;}
}