package arenaFighter;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import controller.Client;
import controller.Game;
import controller.GameInput;
import map.Map;
import map.MapGenerator;
import model.GameState;
import view.GameWindow;

public class GameMain{
	private static final String ip = "127.0.0.1";
	private static final int port=5050;
	private static Game game;
	private static GameWindow window;
	private static GameInput input;
	private static JFrame frame;
	private static Client client;
	private static GameState state;
	public static void main(String[] args){
		frame = new JFrame();
		BufferedImage logicMap;
		try {
		    logicMap = ImageIO.read(new File("res/mapBackground.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		    logicMap = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		}
		
		Map map = MapGenerator.generateMap(logicMap, "lava");
		state=new GameState();
		window=new GameWindow(null);
		client = new Client(port, ip, state, map);
		input = new GameInput();
		game=new Game(state);
	
		frame.add(window);
		frame.setPreferredSize(new Dimension(800, 800));
		
		state.addObserver(client);
		state.addObserver(window);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(false);
		//requestConnection();
		//startGame();
		new Thread(client).start();
	}
	public static void startGame(){
		window.addKeyListener(input);
		window.addMouseListener(input);
		window.addMouseMotionListener(input);
		frame.setVisible(true);
		game.start();
	}
	public static void requestConnection(){
		client.requestConnection();
		input.setup(state.returnPlayer());
		frame.setTitle(""+state.getID());
	}
}