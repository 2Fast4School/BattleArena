package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

import model.GameState;
import view.Meny;

public class LobbyInput implements ActionListener, Observer {

	private Meny frame;
	private GameState state;
	
	public LobbyInput(Meny frame, GameState state) {
		this.frame = frame;
		this.state = state;
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "chckbxReady":
			JCheckBox box = (JCheckBox)arg0.getSource();
			box.setEnabled(false);
			state.setToReady();
			break;
		case "leaveBtn":
			System.out.println("I really hoped you wouldn't press that button");
		default:

			break;
		}
	}
	
	
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState && ((GameState) arg0).isAlive()){
			frame.setView("GAME");
		}
		
	}

}
