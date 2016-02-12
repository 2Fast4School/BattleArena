package ArenaFighter;
public class Game implements Runnable {

	private GameState GAMESTATE;
	private boolean running;
	private Thread thread;
	
	public Game(GameState GAMESTATE){
		this.GAMESTATE = GAMESTATE;
		//running = false;
		running=true;
	}

	/*public synchronized void start(){
		if(!running)
			running = true;
		
		thread = new Thread(this);
		thread.start();
	}*/
	
	public synchronized void stop(){
		if(running)
			running = false;
		try{
			thread.join();
		}catch(InterruptedException e){}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 100000000 / amountOfTicks;
		double delta = 0;
		int ups = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			System.out.println("test");
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = 0;
			
			while(delta >= 1){
				GAMESTATE.tick();
				ups++;
			}
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("UPS: "+ups);
				ups = 0;
			}
		}		
	}
	
}
