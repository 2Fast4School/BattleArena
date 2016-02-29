package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BAServer.ServerMain;
import view.PreGameWindow;

public class PreGameInput implements ActionListener {
	private PreGameWindow preGameWindow;
	public PreGameInput(PreGameWindow preGameWindow) {
		this.preGameWindow = preGameWindow;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
		case "createGameBtn":
			ServerMain.main(null);
			break;
		case "connectGameBtn":

			break;
		case "findGameBtn":
			
			break;
		case "settingsBtn":

			break;
		case "quitBtn":
			System.exit(0);
			break;
		case "dialogConnectBtn":

			break;
		default:
			System.out.println("Other");
			break;
		}
		// Create game
		// Connect by IP
		// Find a game
		// Settings
		// Quit
	}
}
