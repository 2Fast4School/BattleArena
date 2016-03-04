package view;

import java.awt.Image;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.PreGameInput;

/**
 * The Class PreGameWindow.
 * Main-menu which the user enters when starting the application.
 * User can navigate to different submenus and choose to start a server here
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class PreGameWindow extends JPanel{

	
	/** The background image for menus. */
	private Image preGameArt;
	
	/** The pregameinput. Exclusive actionlistener for pregamewindow */
	private PreGameInput preGameInput;
	
	/** The create game button. Starts a server */
	private JButton createGameBtn;
	
	/** The connect game btn. Enters the submenu mode Connect*/
	private JButton connectGameBtn;
	
	/** The settings btn. Enters the submenu mode settings*/
	private JButton settingsBtn;
	
	/** The quit btn. Exits the application */
	private JButton quitBtn;
	
	/**
	 * Instantiates the main menu.
	 * 
	 *
	 * @param preGameInput pregameinput, the exclusive actionlistener for pregamewindow
	 * @param preGameArt the background image for menus
	 */
	public PreGameWindow(PreGameInput preGameInput, Image preGameArt) {
		setLayout(null);
		this.preGameInput = preGameInput;
		this.preGameArt = preGameArt;
		initPreGameWindow();
	}
	
	/**
	 * Inits the pre game window.
	 * Creates all buttons and applies actionlisteners to them.
	 * Sets the background
	 */
	private void initPreGameWindow() {
		
		
		setBounds(0, 0, 800, 800);
		
		Font font = new Font("Comic Sans MS", Font.PLAIN, 21);
		
					// Create game
		JButton createGameBtn = new JButton("Create a game");
		createGameBtn.setBounds(300, 240, 200, 60);
		createGameBtn.setFont(font);
		createGameBtn.setActionCommand("createGameBtn");
		createGameBtn.addActionListener(preGameInput);
		add(createGameBtn);
					// Connect game (by IP)
		JButton connectGameBtn = new JButton("Connect by IP");
		connectGameBtn.setBounds(300, 320, 200, 60);
		connectGameBtn.setFont(font);
		connectGameBtn.setActionCommand("connectGameBtn");
		connectGameBtn.addActionListener(preGameInput);
		add(connectGameBtn);
					// Settings
		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setBounds(300, 400, 200, 60);
		settingsBtn.setFont(font);
		settingsBtn.setActionCommand("settingsBtn");
		settingsBtn.addActionListener(preGameInput);
		add(settingsBtn);
					// Quit
		JButton quitBtn = new JButton("Quit");
		quitBtn.setBounds(300, 480, 200, 60);
		quitBtn.setFont(font);
		quitBtn.setActionCommand("quitBtn");
		quitBtn.addActionListener(preGameInput);
		add(quitBtn);
		quitBtn.repaint();
		
		// picLabel is added last because it will then be painted first in the JPanel
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
}
