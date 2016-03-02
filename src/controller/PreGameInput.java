package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Meny;
import BAServer.ServerMain;

public class PreGameInput implements ActionListener {
	private Meny theFrame;
	
	public PreGameInput(Meny theFrame) {
		this.theFrame = theFrame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
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
		// Create game
		// Connect by IP
		// Find a game
		// Settings
		// Quit
	}
}
