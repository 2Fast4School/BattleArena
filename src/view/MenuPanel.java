package view;

import java.awt.Image;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MenuInput;

/**
 * The Class MenuPanel.
 * Main-menu which the user enters when starting the application.
 * User can navigate to different submenus and choose to start a server here
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel{

	
	/** The background image for menus. */
	private Image preGameArt;
	
	/** The menuInput. Exclusive actionlistener for menupanel */
	private MenuInput menuInput;
	/**
	 * Instantiates the main menu.
	 * 
	 *
	 * @param menuInput menuinput, the exclusive actionlistener for menuPanel
	 * @param preGameArt the background image for menus
	 */
	public MenuPanel(MenuInput menuInput, Image preGameArt) {
		setLayout(null);
		this.menuInput = menuInput;
		this.preGameArt = preGameArt;
		initMenuPanel();
	}
	
	/**
	 * Inits the menu window.
	 * Creates all buttons and applies actionlisteners to them.
	 * Sets the background
	 */
	private void initMenuPanel() {
		
		
		setBounds(0, 0, 800, 800);
		
		Font font = new Font("Comic Sans MS", Font.PLAIN, 21);
		
					// Create game
		JButton createGameBtn = new JButton("Create a game");
		createGameBtn.setBounds(300, 240, 200, 60);
		createGameBtn.setFont(font);
		createGameBtn.setActionCommand("createGameBtn");
		createGameBtn.addActionListener(menuInput);
		add(createGameBtn);
					// Connect game (by IP)
		JButton connectGameBtn = new JButton("Connect by IP");
		connectGameBtn.setBounds(300, 320, 200, 60);
		connectGameBtn.setFont(font);
		connectGameBtn.setActionCommand("connectGameBtn");
		connectGameBtn.addActionListener(menuInput);
		add(connectGameBtn);
					// Settings
		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setBounds(300, 400, 200, 60);
		settingsBtn.setFont(font);
		settingsBtn.setActionCommand("settingsBtn");
		settingsBtn.addActionListener(menuInput);
		add(settingsBtn);
					// Quit
		JButton quitBtn = new JButton("Quit");
		quitBtn.setBounds(300, 480, 200, 60);
		quitBtn.setFont(font);
		quitBtn.setActionCommand("quitBtn");
		quitBtn.addActionListener(menuInput);
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
