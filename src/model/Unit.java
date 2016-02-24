package model;

/**
 * Abstract class that stores data about a Unit, i.e Player or Enemy.
 * @author Victor Dahlberg
 *
 */
public abstract class Unit extends Entity{
	private final int MAXHP = 100;
	private int hp;
	private Weapon weapon, secWeapon;
	private boolean alive;
	
	/**
	 * 
	 * @param x the x position of the Unit.
	 * @param y the y position of the Unit.
	 * @param w the width of the Unit.
	 * @param h the height of the Unit.
	 */
	public Unit(int x, int y, int w, int h) {
		super(x, y, w, h, true);
		hp = MAXHP;
		alive = true;
		weapon = new SweepSword(this, 8, 50, 5);
		secWeapon = new Bow(this, 4, 20, 5);
	}
	
	public void switchWeapon(){
		weapon.stopAttack();
		Weapon tempWep = weapon;
		weapon = secWeapon;
		secWeapon = tempWep;
	}

	/**
	 * 
	 * @return the current weapon the unit is "holding".
	 */
	public Weapon getWeapon(){
		return weapon;
	}
	
	/**
	 * 
	 * @param weapon Give the unit this weapon.
	 */
	/*public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}*/
	
	/**
	 * 
	 * @return The current HP of the unit.
	 */
	public int getHP(){
		return hp;
	}
	
	/**
	 * Give the unit a new hp. If it is less than zero, it set's the hp to zero.
	 * @param newHp The Units new HP.
	 */
	public void setHP(int newHp){
		if(newHp <= 0){
			hp = 0;
			alive = false;
		} else {
			hp = newHp;
		}
	}
	
	/**
	 * Calls the weapons attack method, which starts an actual attack.
	 */
	public void doAttack(){
		weapon.attack();
	}
	
	/**
	 * 
	 * @return The players maximum HP.
	 */
	public int getMAXHP(){
		return MAXHP;
	}
	
	/**
	 * 
	 * @return True if the player is alive, false otherwise.
	 */
	public boolean isAlive(){
		return alive;
	}
	
	/**
	 * Set's the private variable alive to true.
	 */
	public void revive(){
		alive = true;
	}
}
