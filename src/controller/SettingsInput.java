package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import view.GameFrame;
import view.SettingsPanel;

public class SettingsInput implements ActionListener {
		private GameFrame theFrame;
		
		public SettingsInput(GameFrame theFrame) {
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
