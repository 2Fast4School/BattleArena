package view;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PreGameWindow preGameWindow = new PreGameWindow(this);
		contentPane.add(preGameWindow);
		preGameWindow.setLayout(null);
		
		
		
				// makeVisible
				setVisible(true); 
				
				// Initialise input to be observing frame (to access menu actions)
				//Initialise input to be observing preGameWindow
				
	}
	
	public void switchToGameWindow(GameWindow window) {
		contentPane.removeAll();
		contentPane.add(window);
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
