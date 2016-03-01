package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import arenaFighter.Main;
import view.ConnectPanel;
import view.GameFrame;

public class ConnectInput implements ActionListener {
	private ConnectPanel connectDialog;
	private GameFrame frame;
	

	private String ip, port;
	
	public ConnectInput(GameFrame frame) {

		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		
		case "ipConnectBtn":
			connect(ip, Integer.parseInt(port));
			frame.setView("LOBBY");
			break;
			
		case "ipCancelBtn":
			frame.setView("MENY");
			break;
			
		case "ipTextField":
			ip = ((JTextField)arg0.getSource()).getText();
			break;
			
		case "portTextField":
			port = ((JTextField)arg0.getSource()).getText();
			break;
			
		default:
			
			break;
		}
	}

	private void connect(String ip, int port) {
		Main.gameSetup(ip, port);
	}
}
