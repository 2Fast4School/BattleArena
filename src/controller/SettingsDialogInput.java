package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GameFrame;
import view.SettingsDialog;

public class SettingsDialogInput implements ActionListener {
		private SettingsDialog settingsDialog;
		private GameFrame frame;
		private static final int numberOfPlayers = 2;
		public SettingsDialogInput(GameFrame frame, SettingsDialog settingsDialog) {
			this.settingsDialog = settingsDialog;
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getActionCommand()) {
			case "settingsSaveBtn":
				settingsDialog.setName(settingsDialog.getName());
				settingsDialog.dispose();
				break;
			case "settingsCloseBtn":
				settingsDialog.dispose();
				break;
			case "settingsToolTipsChk":
				System.out.println("settingsToolTipsChk");
				System.out.println(settingsDialog.getToolTipsEnabled());
				if (settingsDialog.getToolTipsEnabled()) {
					settingsDialog.setToolTipsEnabled(false);
				}
				else {
					settingsDialog.setToolTipsEnabled(true);
				}
				
				break;
			default:
				break;
			}
		}
	}
