package arenaFighter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Client;
import controller.Game;
import controller.GameInput;
import controller.PreGameInput;
import map.Map;
import map.MapGenerator;
import model.GameState;
import view.GameFrame;
import view.GameWindow;
import view.PreGameWindow;

public class Main{
	private static GameFrame frame;
	private static Game game;
	private static GameWindow window;
	private static GameInput input;
	private static Client client;
	private static GameState state;
	
	public static void main(String[] args){
		frame = new GameFrame("BattleArena");
		frame.setView("MENY");
	}
	
	public static void gameSetup(String ip, int port){
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
	
		frame.setClient(client);
		//frame.add(window);
		frame.setView("GAME");
		
		state.addObserver(client);
		state.addObserver(window);
		requestConnection();
		//startGame();
		//new Thread(client).start();
	}
	public static void startGame(){
		window.addKeyListener(input);
		window.addMouseListener(input);
		window.addMouseMotionListener(input);
		game.start();
		frame.setVisible(true);
	}
	public static void requestConnection(){
		client.requestConnection();
		input.setup(state.returnPlayer());
	}
	public static void runClient(){
		new Thread(client).start();
	}
	public static void lobbyProtocol(){
		client.startLobbyProtocol();;
	}
}
