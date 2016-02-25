package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import controller.GameInput;
import controller.PreGameInput;

public class GameFrame extends JFrame {
	private Container contentPane;
	private PreGameWindow preGameWindow;
	private GameWindow gameWindow;
	private PreGameInput preGameInput;
	private GameInput gameInput;
	
	
	public GameFrame(String frameName) {
		super(frameName);
		initGameFrame();
	}
	
	private void initGameFrame() {
		setLayout(new BorderLayout());
				// ContentPane
		PreGameWindow preGameWindow = new PreGameWindow(this);
		contentPane = getContentPane();
		contentPane.add(preGameWindow);
		
		
		
				// makeVisible
				setPreferredSize(new Dimension(800, 800));
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				pack();
				setVisible(true);
				
				// Initialise input to be observing frame (to access menu actions)
				//Initialise input to be observing preGameWindow
				
	}
	
	public void switchToGameWindow() {
		//contentPane.remove(preGameWindow);
		contentPane.add(gameWindow);
		// Lägg till lyssnare till gameWindow
		//GameInput gameInput = new GameInput();
		// Starta spel
	}
}
