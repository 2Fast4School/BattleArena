package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Meny;
import BAServer.ServerMain;


/**
 * The Class PreGameInput.
 * Exclusive actionlistener for mainmenu components.
 * Gets actionevent when the user presses any button.
 * Takes care of starting a server, showing a submenu or exiting the game.
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class PreGameInput implements ActionListener {
	
	/** The frame, application window */
	private Meny theFrame;
	
	/**
	 * Instantiates the main-menu input.
	 *
	 * @param theFrame the application window
	 */
	public PreGameInput(Meny theFrame) {
		this.theFrame = theFrame;
	}
	
	/**
	 * Takes care of any actionevent within the
	 * the main-menu.
	 * Can start sub-menus, server or exit application
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
		case "createGameBtn":
			ServerMain m = ServerMain.getInstance();
			break;
			
		case "connectGameBtn":
			theFrame.setView("CONNECT");

			break;
			
		case "findGameBtn":

			break;
			
		case "settingsBtn":
			theFrame.setView("SETTINGS");
			break;
			
		case "quitBtn":
			System.exit(0);
			break;
			
		default:
			System.out.println("Other");
			break;
		}
	}
}
