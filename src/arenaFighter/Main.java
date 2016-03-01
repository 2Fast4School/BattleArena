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
import controller.Input;
import map.Map;
import map.MapGenerator;
import model.GameState;
import view.GameWindow;

public class Main{
	private static final int numberOfPlayers = 2;
	private static final String ip = "127.0.0.1";
	private static final int port=7020;
	public static void main(String[] args){
		
		//for(int n=0;n<numberOfPlayers;n++){
			JFrame frame = new JFrame();
			
			int sizeOfPixel = 16;
			BufferedImage logicMap;
			
			try {
			    logicMap = ImageIO.read(new File("res/logicMap.png"));
			} catch (IOException e) {
			    e.printStackTrace();
			    logicMap = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
			    sizeOfPixel = 16;
			}
			Map map = MapGenerator.generateMap(logicMap, "lava", sizeOfPixel);
			
			
			
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
			
			
			
			GameState state=new GameState(numberOfPlayers, map);
			GameWindow window=new GameWindow();
			Client client = new Client(port, ip, state);
			Input input = new Input();
			Game game=new Game(state);
			
			
			
			


			
			
			
			
			
			
			state.setup();
			input.setup(state.returnPlayer());
			
			frame.add(window);
			frame.setPreferredSize(new Dimension(logicMap.getWidth()*sizeOfPixel+22, logicMap.getHeight()*sizeOfPixel+56));//Have to use constants (22 and 56) for some odd reason
			
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
		//}
	}
}
