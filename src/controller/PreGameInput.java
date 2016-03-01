package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BAServer.ServerMain;
import view.ConnectPanel;
import view.GameFrame;
import view.SettingsPanel;

public class PreGameInput implements ActionListener {
	private GameFrame theFrame;
	
	public PreGameInput(GameFrame theFrame) {
		this.theFrame = theFrame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		switch (e.getActionCommand()) {
		case "createGameBtn":
			ServerMain.main(null);
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
		// Create game
		// Connect by IP
		// Find a game
		// Settings
		// Quit
	}
}
