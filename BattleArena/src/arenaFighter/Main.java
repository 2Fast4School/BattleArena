package arenaFighter;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import controller.Client;
import controller.Game;
import controller.Input;
import map.Map;
import map.MapGenerator;
import model.GameState;
import view.GameWindow;

public class Main{

	private static final String ip = "127.0.0.1";
	private static final int port=5050;
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		
	
		BufferedImage logicMap;
		try {
		    logicMap = ImageIO.read(new File("res/mapBackground.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		    logicMap = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		}
		
		
		Map map = MapGenerator.generateMap(logicMap, "lava");
		GameState state=new GameState();
		GameWindow window=new GameWindow();
		Client client = new Client(port, ip, state, map);
		client.requestConnection();
		Input input = new Input();
		
		Game game=new Game(state);
		
		input.setup(state.returnPlayer());
		frame.setTitle(""+state.getID());
		
		frame.add(window);
		frame.setPreferredSize(new Dimension(800, 800));
		
		window.addKeyListener(input);
		window.addMouseListener(input);
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
