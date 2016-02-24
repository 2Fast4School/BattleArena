package model;

/**
 * The logic tiles to represent damage when stepping on them, for example lava or bushes
 * 
 * @author Alexander Erenstedt
 *
 */
public class DamageTile extends Tile {
	private final int MAXDMG = 1;
	private int dmg;
	private int counter;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public DamageTile(int x, int y, int w, int h){
		super(x, y, w ,h, false);
	}
	
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
	
	public int getDmg(){
		return dmg;
	}
	
}
