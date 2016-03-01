package arenaFighter;

import java.awt.Dimension;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import controller.Client;
import controller.Game;
import controller.GameInput;
import map.Map;
import map.MapGenerator;
import model.GameState;

import view.GameFrame;

import view.GameWindow;

public class Main{
	private static GameFrame frame;
	private static Game game;
	private static GameWindow window;
	private static GameInput input;
	private static Client client;
	private static GameState state;
	
	public static void main(String[] args){
		frame = new GameFrame("BattleArena");
	}
	
	public static void gameSetup(String ip, int port){
		BufferedImage logicMap;
		int sizeOfPixel = 16;
		try {
		    logicMap = ImageIO.read(new File("res/mapBackground.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		    logicMap = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		}
		
		Map map = MapGenerator.generateMap(logicMap, "lava", sizeOfPixel);
		state=new GameState();
		window=new GameWindow(null);
		client = new Client(port, ip, state, map);
		input = new GameInput();
		game=new Game(state);
	
		frame.setClient(client);
		//frame.add(window);
		frame.switchToGameWindow(window);
		
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
		client.startLobbyProtocol();	
	}
}


/*
//Serialize map
try
  {
     FileOutputStream fileOut =
     new FileOutputStream("serializedMap.ser");
     ObjectOutputStream out = new ObjectOutputStream(fileOut);
     out.writeObject(map);
     out.close();
     fileOut.close();
     System.out.printf("Serialized data is saved in serializedMap.ser \n");
  }catch(IOException i)
  {
      i.printStackTrace();
  }

map = null;
//Unserialize map
  try
  {
     FileInputStream fileIn = new FileInputStream("serializedMap.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn);
     map = (Map) in.readObject();
     in.close();
     fileIn.close();
  }catch(IOException i)
  {
     i.printStackTrace();
     return;
  }catch(ClassNotFoundException c)
  {
     System.out.println("Map class not found");
     c.printStackTrace();
     return;
  }
  System.out.println("Deserialized serializedMap...");
*/
