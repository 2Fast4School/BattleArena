package ArenaFighter;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main{
	private static final int numberOfPlayers=1;
	public static void main(String[] args){
		JFrame frame = new JFrame();
		GameWindow window=new GameWindow();
		GameState state=new GameState(numberOfPlayers);
		Client client = new Client(1234, "127.0.0.1", state);
		Input input = new Input(state.returnPlayer());
		Game game=new Game(state);
		
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
}
