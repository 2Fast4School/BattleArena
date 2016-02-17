package arenaFighter;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import controller.Client;
import controller.Game;
import controller.Input;
import model.GameState;
import server.Server;
import view.GameWindow;

public class Main{
	private static final int numberOfPlayers=2;
	private static final String ip = "192.168.0.106";
	private static final int port=1555;
	public static void main(String[] args){
		
		for(int n=0;n<numberOfPlayers;n++){
			JFrame frame = new JFrame();
			GameWindow window=new GameWindow();
			GameState state=new GameState(numberOfPlayers);
			Client client = new Client(port, ip, state, n);
			Input input = new Input();
			Game game=new Game(state);
			
			state.setup();
			input.setup(state.returnPlayer());
			
			frame.add(window);
			frame.setPreferredSize(new Dimension(800, 800));
			
			window.addKeyListener(input);
			window.addMouseMotionListener(input);
			state.addObserver(client);
			state.addObserver(window);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			
			game.start();
			new Thread(client).start();
		}
		try{
			new Server(numberOfPlayers, ip, port);
		}catch(IOException e){}
	}
}