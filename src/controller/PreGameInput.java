package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import server.ServerMain;
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

			break;
		case "dialogConnectBtn":

			break;
		default:
			System.out.println("Annat");
			break;
		}
		// Create game
		// Connect by IP
		// Find a game
		// Settings
		// Quit
	}
}
