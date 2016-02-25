package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

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
			
			break;
		case "connectGameBtn":
			
			break;
		case "findGameBtn":
			
			break;
		case "settingsBtn":
			System.out.println(preGameWindow.ipTextField.getText());
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
