package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import arenaFighter.Main;
import view.GameFrame;
import view.LobbyDialog;

public class LobbyDialogInput extends JDialog implements ActionListener {
	private LobbyDialog lobbyDialog;
	private GameFrame frame;
	private boolean alreadyPressed;
	private Client client;
	public LobbyDialogInput(Client client, GameFrame frame, LobbyDialog lobbyDialog) {
		this.lobbyDialog = lobbyDialog;
		this.frame = frame;
		this.client=client;
		alreadyPressed=false;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "chckbxReady":
			if(!alreadyPressed){
				alreadyPressed=true;
				lobbyDialog.readyUp(1);
				
				if(lobbyDialog.getIsReady()==1){
					lobbyDialog.countDown(0);
				}
			}
			else{
				alreadyPressed=false;
				lobbyDialog.readyUp(-1);
			}
			client.setReady(alreadyPressed);
			break;
		default:

			break;
		}
	}

}
