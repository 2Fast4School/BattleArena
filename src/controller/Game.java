package controller;

import javax.swing.JOptionPane;

import model.*;

/**
 * The Game class is in charge of the main game thread.
 * @author Victor Dahlberg
 *
 */
public class Game implements Runnable {

	private GameState GAMESTATE;
	private boolean running;
	private Thread thread;
	
	/**
	 * Constructor.
	 * @param GAMESTATE The gamestate which the game class will be responsible of "running".
	 */
	public Game(GameState GAMESTATE){
		this.GAMESTATE = GAMESTATE;
		running = false;
	}

	/**
	 * Start the main game thread.
	 */
	public synchronized void start(){
		if(running){return;}
			
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the main game thread.
	 */
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
	
	/**
	 * 
	 */
	public void run() {

		//Initializes the lastTime variable, which later will be used to compare how long time something took.
		long lastTime = System.nanoTime();
		
		//Amount of ticks/updates we want every second.
		final double amountOfTicks = 60.0;
		
		//ns = 10^9 is 1 second in nano seconds. We want to achieve 60 ticks every second and therefore divide 10^9 with 60.
		double ns = 1000000000 / amountOfTicks;
		
		//Used so the game will tick a little faster if you because of some reason had a couple of fewer ticks.
		double delta = 0;
		
		//Used to calculate the number of ticks, i.e updates.
		int updates = 0;
		
		//Just a variable used to calculate when a second has passed.
		long timer = System.currentTimeMillis();
		
		
		while(running){
			//The current time in nanoseconds.
			long now = System.nanoTime();
			
			 
			// We divide the time a loop takes with our ns variable. delta will then achieve approx >1 every 1/60 of a second.
			delta += (now - lastTime) / ns;
			
			//set lasttime to now, so we can calculate how long each "loop" takes.
			lastTime = now;
			
			//Check if the round is over (1 player that hasn't lost)
			int nrHasLost=0;
			if(!GAMESTATE.returnPlayer().isAlive()){nrHasLost++;}
			for(Enemy e : GAMESTATE.getTheEnemies()){
				if(!e.isAlive()){
					nrHasLost++;
				}
			}
			
			if(GAMESTATE.getNrOfPlayers()-nrHasLost==1){ // If the round is over
				int option = JOptionPane.showConfirmDialog(null, "A player won! Play again?", "GameOver", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				
				if(option==JOptionPane.YES_OPTION){
					for(Entity e : GAMESTATE.getList()){	// Simple 'restart'. To be replaced.
						if(e instanceof Unit){
							((Unit)e).setHP(((Unit)e).getMAXHP());
							e.setX((int)(Math.random()*400+200));
							e.setY((int)(Math.random()*400+200));
							((Unit)e).revive();
						}
					}
					for(spawnPoint sp : GAMESTATE.getSpawns()){
						sp.reset();
					}
				}
				else if(option==JOptionPane.NO_OPTION || option==JOptionPane.CANCEL_OPTION){
					//Probably doesn't work if there's two different computers running clients.
					//Also doesn't send to close down server. (Might not be desired though)
					System.exit(1);
				}
			}
			else{	// The round isn't over
					
				//Wait until 1/60 of a second has passed by and then update gamestate.
				while(delta >= 1){ 
					//Update the gamestate.
					GAMESTATE.tick();
					updates++;
					
					//Decrement the tick with 1. We don't set it to 0 because if we would loose a couple of ticks, we want the next second to tick a few more times than 60.
					delta--;
				}
				//Used to print number of "ticks" the last second.
				if(System.currentTimeMillis() - timer > 1000){
					timer += 1000;
					//System.out.println("TICKS: "+updates);
					updates = 0;
				}
			}
		}
		
		stop();	
	}
	
}