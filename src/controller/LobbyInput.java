package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;

import model.GameState;
import view.Meny;

/**
 * The Class LobbyInput.
 * Exclusive actionlistener for lobby-submenu components.
 * Gets actionevent when the user is ready or wants to leave
 * the game lobby and go back to main-menu (not yet implemented)
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class LobbyInput implements ActionListener, Observer {

	/** The frame, application window*/
	private Meny frame;
	
	/** The state. */
	private GameState state;
	
	/**
	 * Instantiates the lobby input.
	 *
	 * @param frame the frame
	 * @param state the state
	 */
	public LobbyInput(Meny frame, GameState state) {
		this.frame = frame;
		this.state = state;
	}
	
	
	/**
	 * Takes care of any actionevent within the
	 * the lobby submenu.
	 * Sends user back to main-menu or to in-game mode
	 * Sends information to state that user is ready (no turning back)
	 */
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
	
	
	/**
	 * Once everyone is ready the game is ready to start
	 * Starts in-game mode
	 */
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState && ((GameState) arg0).isAlive()){
			frame.setView("GAME");
		}
		
	}

}
