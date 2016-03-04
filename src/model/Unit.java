package model;

public abstract class Unit extends Entity{
	private final int MAXHP = 100;
	protected int hp;
	private Weapon weapon, secondaryWeapon;
	private boolean alive;
	
	public Unit(int x, int y, int w, int h, boolean solid) {
		super(x, y, w, h, solid);
		hp = MAXHP;
		weapon = new SweepSword(this, 8, 50, 5);
		secondaryWeapon = new Bow(this, 5, 18, 5);
		alive = true;
	}
	
	public void switchWeapon(){
		weapon.stopAttack();
		Weapon t = weapon;
		weapon = secondaryWeapon;
		secondaryWeapon = t;
		System.out.println("WEAPON SWAPPED");
	}
	
	public Weapon getWeapon(){
		return weapon;
	}

	public int getWeaponID(){
		if(weapon instanceof Bow){
			return 2;
		} else if(weapon instanceof SweepSword){
			return 1;
		} return -1;
	}
	
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
	
	public Weapon secondaryWeapon(){
		return secondaryWeapon;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}

	
	public int getHP(){
		return hp;
	}
	
	public void setHP(int newHp){
		if(newHp <= 0){
			hp = 0;
			alive = false;
		} else {
			hp = newHp;
		}
	}
	
	public void doAttack(){
		weapon.attack();
	}
	
	public int getMAXHP(){
		return MAXHP;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void revive(){
		alive = true;
	}
}
