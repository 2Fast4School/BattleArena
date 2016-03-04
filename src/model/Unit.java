package model;
/**
 * <h1>Unit</h1>
 * Unit stores basic information about a Unit. Unit's are objects controlled by a user.
 * @author Victor Dahlberg
 * @version 29-02-16
 *
 */
public abstract class Unit extends Entity{
	private final int MAXHP = 100;
	protected int hp;
	private Weapon weapon, secondaryWeapon;
	
	/**
	 * Is true when the unit is alive, false otherwise. 
	 */
	private boolean alive;
	
	/**
	 * The Unit constructor takes the basic information about a Unit.
	 * It set's the Unit's health to maximum and creates objects of the different weapons in the game.
	 * @param x the unit's x position.
	 * @param y the unit's y position.
	 * @param w the unit's widht.
	 * @param h the unit's height.
	 * @param solid true if the unit should be a solid object, false otherwise.
	 */
	public Unit(int x, int y, int w, int h, boolean solid) {
		super(x, y, w, h, solid);
		hp = MAXHP;
		weapon = new SweepSword(this, 8, 50, 5);
		secondaryWeapon = new Bow(this, 5, 18, 5);
		alive = true;
	}
	
	/**
	 * Swaps from the current Weapon to the secondary weapon.
	 */
	public void switchWeapon(){
		weapon.stopAttack();
		Weapon t = weapon;
		weapon = secondaryWeapon;
		secondaryWeapon = t;
		System.out.println("WEAPON SWAPPED");
	}
	
	/**
	 * 
	 * @return The weapon that is currently used.
	 */
	public Weapon getWeapon(){
		return weapon;
	}

	/**
	 * 
	 * @return The id of the weapon currently used, 2 if Bow, 1 if Sweepsword.
	 */
	public int getWeaponID(){
		if(weapon instanceof Bow){
			return 2;
		} else if(weapon instanceof SweepSword){
			return 1;
		} return -1;
	}
	
	/**
	 * Sets the primay weapon to the weapon associated with the wepID.
	 * @param wepID  The weapon id of the weapon to swap to.
	 */
	public void setWeaponID(int wepID){
		if(wepID == 2){
			if(!(weapon instanceof Bow)){
				switchWeapon();
			}
		} else if(wepID == 1){
			if(!(weapon instanceof SweepSword)){
				switchWeapon();
			}
		}
	}
	
	/**
	 * 
	 * @return The weapon that is currently not used.
	 */
	public Weapon secondaryWeapon(){
		return secondaryWeapon;
	}

	
	/**
	 * 
	 * @return The unit's current health.
	 */
	public int getHP(){
		return hp;
	}
	
	/**
	 *  Set's the unit to newHp. If newHp is less than 0, it set's the health to 0 and alive to false.
	 * @param newHp The desired hp
	 */
	public void setHP(int newHp){
		if(newHp <= 0){
			hp = 0;
			alive = false;
			if(this instanceof Player){
				Player player = (Player)this;
				player.setdx(0);
				player.setdy(0);
			}
		} else {
			hp = newHp;
		}
	}
	
	/**
	 * Start's an attack with the current weapon.
	 */
	public void doAttack(){
		weapon.attack();
	}
	
	/**
	 * 
	 * @return The maximum health of the unit.
	 */
	public int getMAXHP(){
		return MAXHP;
	}
	
	/**
	 * 
	 * @return true if the unit is still alive.
	 */
	public boolean isAlive(){
		return alive;
	}
	
}
