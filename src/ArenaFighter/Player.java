package ArenaFighter;
public class Player extends Entity {
	private int dx, dy;
	
	public Player(int id, int x, int y, int w, int h){
		super(id, x, y, w, h, true);
		dx = dy = 0;
	}
	
	public void tick(){
		this.x += dx;
		this.y += dy;
	}
	
	public void move(int dx, int dy){ // Use for collision
		if(dx != 0 && dy != 0){
			move(this.x+dx, 0);
			move(0, this.y+dy);
		}
		
		if(!collision()){
			this.x += dx;
			this.y += dy;
		}
	}
	
	private boolean collision(){
		
		return false;
	}
	
	public void setdx(int dx){
		this.dx = dx;
	}
	
	public void setdy(int dy){
		this.dy = dy;
	}
	
}


