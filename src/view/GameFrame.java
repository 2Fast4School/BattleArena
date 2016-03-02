package view;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arenaFighter.Main;
import controller.Client;
import controller.GameInput;
import controller.PreGameInput;

public class GameFrame extends JFrame {
	private JPanel contentPane;
	private PreGameWindow preGameWindow;
	private GameWindow gameWindow;
	private PreGameInput preGameInput;
	private GameInput gameInput;
	private Client client;
	
	public GameFrame(String frameName) {
		super(frameName);
		initGameFrame();
	}
	
	private void initGameFrame() {
		try {
			setIconImage(ImageIO.read(Main.class.getResource("/testa.png")));
		}
		catch(IOException e) {}
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		
		preGameWindow = new PreGameWindow(this);
		add(preGameWindow);
		preGameWindow.setLayout(null);
		
				// makeVisible
				setVisible(true); 
				
				// Initialise input to be observing frame (to access menu actions)
				//Initialise input to be observing preGameWindow
				
	}
	
	public void switchToGameWindow(GameWindow window) {

		preGameWindow.setVisible(false);
		setVisible(false);
		//remove(preGameWindow);
		
		gameWindow=window;
		add(window);
		new LobbyDialog(this, client);
		//Main.runClient();
		//Main.startGame();
		// Lägg till lyssnare till gameWindow
		//GameInput gameInput = new GameInput();
		// Starta spel
	}
	
	public void switchToPreGameWindow(){
		remove(gameWindow);
		preGameWindow.setVisible(true);
	}
	
	public boolean getToolTipsBool() {
		return preGameWindow.checkToolTipsEnabled();
	}
	
	public void toolsTipsEnable(boolean enable) {
		preGameWindow.toggleToolTips(enable);
	}
	public void setClient(Client client){this.client=client;}
}
