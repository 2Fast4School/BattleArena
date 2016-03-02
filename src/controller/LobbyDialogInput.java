package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;

import arenaFighter.Main;
import view.LobbyDialog;

public class LobbyDialogInput extends JDialog implements ActionListener {
	private LobbyDialog lobbyDialog;

	private Client client;
	public LobbyDialogInput(Client client, LobbyDialog lobbyDialog) {
		this.lobbyDialog = lobbyDialog;
		this.client=client;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "chckbxReady":
			JCheckBox box = (JCheckBox)arg0.getSource();
			box.setEnabled(false);
			client.setReady(true);
			break;
		default:

			break;
		}
	}

}
