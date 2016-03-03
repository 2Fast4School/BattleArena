package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Meny;

public class SettingsInput implements ActionListener {
		private Meny theFrame;
		
		public SettingsInput(Meny theFrame) {
			this.theFrame = theFrame;
		}
		

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getActionCommand()) {
			case "settingsSaveBtn":
				System.out.println("saved name");
				break;
				
			case "settingsCloseBtn":
				theFrame.setView("MENY");
				break;
				
			default:
				break;
			}
		}
	}
