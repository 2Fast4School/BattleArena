public class Game implements Runnable {

	private GameState GAMESTATE;
	private boolean running;
	private Thread thread;
	
	public Game(GameState GAMESTATE){
		this.GAMESTATE = GAMESTATE;
		running = false;
	}

	public synchronized void start(){
		if(running){return;}
			
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(running)
			running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {

		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){ 
				GAMESTATE.tick();
				updates++;
				delta--;
			}
			
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("TICKS: "+updates);
				updates = 0;
			}
		}
		
		stop();	
	}
	
}