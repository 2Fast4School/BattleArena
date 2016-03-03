package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.Meny;
import view.SettingsPanel;

public class SettingsInput implements ActionListener {
		private Meny theFrame;
		private String name;
		
		public SettingsInput(Meny theFrame) {
			this.theFrame = theFrame;
		}
		
		public String getName(){
			return name;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getActionCommand()) {
			case "settingsCloseBtn":
				JButton settingsCloseBtn = (JButton)(arg0.getSource());
				SettingsPanel settingsPanel = (SettingsPanel)(settingsCloseBtn.getParent());
				name = settingsPanel.getName();
				System.out.println(getName());
				theFrame.setView("MENY");
				break;
				
			default:
				break;
			}
		}
	}
