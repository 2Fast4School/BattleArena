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
			// I gamelobby n�r man connectat och f�tt information fr�n servern om vad f�r bana
			Map map = MapGenerator.generateMap("res/mapBackground.png", "grass");
			GameState state=new GameState(numberOfPlayers, map);
			GameWindow gameWindow=new GameWindow(map.getBackground());
			// separera port, ip fr�n state.
			 * Client startas n�r connectGame har gjorts och ip och port har valts.
			 * state f�r initieras senare, n�r servern har skickat det.
			Client client = new Client(port, ip, state);
			// samma som f�r state ovan
			Game game=new Game(state);
			state.setup();
			input.setup(state.returnPlayer());
			state.addObserver(client);
			state.addObserver(gameWindow);
			
			// N�r spelet ska startas
			game.start();
			
			// N�r man klickat connect i connect by ip dialog och ip och port lagts till
			new Thread(client).start();
		}
		 */
	}
}
