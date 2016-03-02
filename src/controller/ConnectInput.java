package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

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
		System.out.println(port);
		return Integer.parseInt(port);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		
		case "ipConnectBtn":
			if(port != null || ip != null){
				frame.setView("LOBBY");
			}
			break;
			
		case "ipCancelBtn":
			frame.setView("MENY");
			break;
			
		case "ipTextField":
			ip = ((JTextField)arg0.getSource()).getText();
			break;
			
		case "portTextField":
			port = ((JTextField)arg0.getSource()).getText();
			System.out.println(port);
			break;
			
		default:
			
			break;
		}
	}
}
