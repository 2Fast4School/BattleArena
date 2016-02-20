package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * <h1>Weapon</h1>
 * Weapon stores data about a weapon. Each Player will have at least one weapon.
 * @author William Bj�rklund.
 * @version 1.0
 */
public class Weapon{
	private int length;
	private int width;
	private int damage;
	
	public Weapon(int length, int width, int damage){
		this.length=length;
		this.width=width;
		this.damage=damage;
	}
	/**
	 * Returns the length of the weapon
	 * @return length : int
	 */
	public int getLength(){return length;}
	/**
	 * Returns the width of the weapon
	 * @return width : int
	 */
	public int getWidth(){return width;}
	/**
	 * Returns the damage of the Weapon
	 * @return damage : int
	 */
	public int getDamage(){return damage;}
}
