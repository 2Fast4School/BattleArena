package view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import arenaFighter.Main;
import controller.GameInput;
import controller.PreGameInput;

public class GameFrame extends JFrame {
	private JPanel contentPane;
	private PreGameWindow preGameWindow;
	private GameWindow gameWindow;
	private PreGameInput preGameInput;
	private GameInput gameInput;
	
	public GameFrame(String frameName) {
		super(frameName);
		initGameFrame();
	}
	
	private void initGameFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/testa.png"));
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
		//contentPane.removeAll();
		//contentPane.add(window);
		preGameWindow.setVisible(false);
		//remove(preGameWindow);
		add(window);
		//Main.runClient();
		//Main.startGame();
		// Lägg till lyssnare till gameWindow
		//GameInput gameInput = new GameInput();
		// Starta spel
	}
	
	public boolean getToolTipsBool() {
		return preGameWindow.checkToolTipsEnabled();
	}
	
	public void toolsTipsEnable(boolean enable) {
		preGameWindow.toggleToolTips(enable);
	}

}
