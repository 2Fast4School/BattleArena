package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;

import arenaFighter.Main;
import view.GameFrame;
import view.LobbyDialog;

public class LobbyDialogInput extends JDialog implements ActionListener {
	private LobbyDialog lobbyDialog;
	private GameFrame frame;
	private Client client;
	public LobbyDialogInput(Client client, GameFrame frame, LobbyDialog lobbyDialog) {
		this.lobbyDialog = lobbyDialog;
		this.frame = frame;
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
			Main.lobbyProtocol();
			break;
		default:

			break;
		}
	}

}
