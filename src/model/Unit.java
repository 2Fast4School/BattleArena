package model;

public abstract class Unit extends Entity{
	private final int MAXHP = 100;
	protected int hp;
	private Weapon weapon;
	private boolean alive;
	
	public Unit(int x, int y, int w, int h, boolean solid) {
		super(x, y, w, h, solid);
		hp = MAXHP;
		alive = true;
	}

	public Weapon getWeapon(){
		return weapon;
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
