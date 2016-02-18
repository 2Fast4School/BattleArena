package model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Weapon{
	private int length;
	private int width;
	private int damage;
	private BufferedImage sprite;
	
	public Weapon(int length, int width, int damage){
		this.length=length;
		this.width=width;
		this.damage=damage;
		try{
			sprite = ImageIO.read(getClass().getResourceAsStream("/SmallSword.gif"));
		}catch(IOException e){}
	}
	
	public int getLength(){return length;}
	public int getWidth(){return width;}
	public int getDamage(){return damage;}
	public BufferedImage getSprite(){return sprite;}
}
