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
	public LobbyDialogInput(GameFrame frame, LobbyDialog lobbyDialog) {
		this.lobbyDialog = lobbyDialog;
		this.frame = frame;
		alreadyPressed=false;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "chckbxReady":
			if(!alreadyPressed){
				alreadyPressed=true;
				//System.out.println("Nu jävlar är jag redo(?)");
				lobbyDialog.readyUp(1);
				/*
				 * Denna if statement måste bytas ut. Igentligen ska server/client komminucera, och spelet startas
				 * om nrReady=MaxPlayers, eller liknande!!
				 * */
				if(lobbyDialog.getIsReady()==1){
					lobbyDialog.countDown(0);
				}
			}
			else{
				lobbyDialog.readyUp(-1);
			}
			break;
		default:

			break;
		}
	}

}
