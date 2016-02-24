package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import view.PreGameWindow;

public class PreGameInput implements ActionListener, AncestorListener {
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
			preGameWindow.showConnectDialogue();
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
			
			break;
		}
		// Create game
		// Connect by IP
		// Find a game
		// Settings
		// Quit
	}

	@Override
	public void ancestorAdded(AncestorEvent e) {
		System.out.println(e.toString());
	}

	@Override
	public void ancestorMoved(AncestorEvent e) {
		System.out.println(e.toString());
	}

	@Override
	public void ancestorRemoved(AncestorEvent e) {
		System.out.println(e.toString());
	}

}
