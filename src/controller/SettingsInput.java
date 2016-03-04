package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.Meny;
import view.SettingsPanel;

/**
 * The Class SettingsInput.
 * Handles actionevent for settings menu
 * Can go back to mainmenu
 * Holds information about which name is chosen
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class SettingsInput implements ActionListener {
		
		/** The frame, application window */
		private Meny theFrame;
		
		/** The player name chosen */
		private String name;
		
		/**
		 * Instantiates the settings input.
		 *
		 * @param theFrame the application window
		 */
		public SettingsInput(Meny theFrame) {
			this.theFrame = theFrame;
		}
		
		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName(){
			return name;
		}

		/**
		 * Takes care of any actionevent within the
		 * the settings menu
		 * Can only send user back to main-menu mode
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (arg0.getActionCommand()) {
			case "settingsCloseBtn":
				JButton settingsCloseBtn = (JButton)(arg0.getSource());
				SettingsPanel settingsPanel = (SettingsPanel)(settingsCloseBtn.getParent());
				name = settingsPanel.getName();
				theFrame.setView("MENY");
				break;
				
			default:
				break;
			}
		}
	}
