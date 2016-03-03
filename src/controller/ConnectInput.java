package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import view.ConnectPanel;
import view.Meny;

/**
 * The Class ConnectInput.
 * Exclusive actionlistener for connect-submenu components.
 * Holds information about IP-address and port chosen when players presses the connect-button
 * Can send user back to main-menu or into lobbymode
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class ConnectInput implements ActionListener {
	
	/** The frame. */
	private Meny frame;
	
	/** The ip and port. */
	private String ip, port;
	
	/**
	 * Instantiates the connect input.
	 *
	 * @param frame the application window
	 */
	public ConnectInput(Meny frame) {
		this.frame = frame;
	}
	
	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIP(){
		return ip;
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort(){
		return Integer.parseInt(port);
	}
	
	
	/**
	 * Takes care of any actionevent within the
	 * the connect submenu.
	 * Sends user back to main-menu or further into the lobby
	 * (as well as gathering ip and port from connect panel
	 */
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
