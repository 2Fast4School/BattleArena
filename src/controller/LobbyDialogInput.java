package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import view.GameFrame;
import view.LobbyDialog;

public class LobbyDialogInput extends JDialog implements ActionListener {
	private LobbyDialog lobbyDialog;
	private GameFrame frame;
	public LobbyDialogInput(GameFrame frame, LobbyDialog lobbyDialog) {
		this.lobbyDialog = lobbyDialog;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "chckbxReady":
			System.out.println("Nu jävlar är jag redo(?)");
			break;
		default:

			break;
		}
	}

}
