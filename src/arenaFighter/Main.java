package arenaFighter;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import controller.Client;
import controller.Game;
import controller.GameInput;
import map.Map;
import map.MapGenerator;
import model.GameState;
import server.Server;
import view.GameFrame;
import view.GameWindow;

public class Main{
	private static final int numberOfPlayers = 2;
	private static final String ip = "192.168.43.23";
	private static final int port=1337;
	public static void main(String[] args){
		
		new GameFrame("BattleArena");
		
		/*
		
		
		for(int n=0;n<numberOfPlayers;n++){
			// I gamelobby när man connectat och fått information från servern om vad för bana
			Map map = MapGenerator.generateMap("res/mapBackground.png", "grass");
			GameState state=new GameState(numberOfPlayers, map);
			GameWindow gameWindow=new GameWindow(map.getBackground());
			// separera port, ip från state.
			 * Client startas när connectGame har gjorts och ip och port har valts.
			 * state får initieras senare, när servern har skickat det.
			Client client = new Client(port, ip, state);
			// samma som för state ovan
			Game game=new Game(state);
			state.setup();
			input.setup(state.returnPlayer());
			state.addObserver(client);
			state.addObserver(gameWindow);
			
			// När spelet ska startas
			game.start();
			
			// När man klickat connect i connect by ip dialog och ip och port lagts till
			new Thread(client).start();
		}
		 */
	}
}
