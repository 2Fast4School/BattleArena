package model;

/**
 * The logic tiles to represent damage when stepping on them, for example lava or bushes
 * 
 * @author Alexander Erenstedt - Modified 28-02-16
 * @version 1.0
 */
public class DamageTile extends Tile {
	private final int MAXDMG = 1;
	private int dmg;
	private int counter;
	/**
	 * Constructor for DamageTile. 
	 * @param x The x-position of the DamageTile.
	 * @param y	The y-position of the DamageTile.
	 * @param w	The width of the DamageTile.
	 * @param h	The height of the DamageTile.
	 * */
	public DamageTile(int x, int y, int w, int h){
		super(x, y, w ,h, false); //Never is solid.
	}
	/**
	 * The logic used for collision with players, deals dmg every 5 ticks
	 */
	@Override
	public void tick() {
		if(counter >= 5){
			dmg = MAXDMG;
			counter = 0;
		} else {
			dmg = 0;
			counter++;
		}
	}
	
	/**
	 * @return The damage it deals.
	 */
	public int getDmg(){
		return dmg;
	}
}
