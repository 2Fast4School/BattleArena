package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.ConnectPanel;
import view.Meny;

public class ConnectInput implements ActionListener {
	private Meny frame;
	private String ip, port;
	
	public ConnectInput(Meny frame) {
		this.frame = frame;
	}
	
	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return Integer.parseInt(port);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		
		case "ipConnectBtn":
			JButton ipConnectBtn = (JButton)(arg0.getSource());
			ConnectPanel connectPanel = (ConnectPanel)(ipConnectBtn.getParent());
			ip = connectPanel.getIp();
			port = connectPanel.getPort();
			frame.setView("LOBBY");
			break;	
		case "ipCancelBtn":
			frame.setView("MENY");
			break;
		default:
			break;
		}
	}
}
