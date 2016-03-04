package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ConnectInput;

/**
 * The Class ConnectPanel.
 * ConnectPanel is the sub-menu (GUI) where the user inserts IP-address and port for the server.
 *  It holds information the user inserts (IP and port) and passes it on to ConnectInput
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class ConnectPanel extends JPanel {
	
	/** The connectinput, exclusive actionlistener for this class */
	private ConnectInput connectinput;
	
	/** The text field for entering ip. */
	private JTextField ipTextField;
	
	/** The text field for entering port. */
	private JTextField portTextField;
	
	/** The background image for menus */
	private Image preGameArt;
	
	/** The panel holding ip label and ip text field. */
	private JPanel ipPanel;
	
	/** The panel holding port label and port text field. */
	private JPanel portPanel;
	
	/** The port label. */
	private JLabel portLabel;
	
	/** The ip label. */
	private JLabel ipLabel;
	
	/**
	 * Instantiates the connect panel.
	 *
	 * @param connectinput the connectinput (actionlistener)
	 * @param preGameArt background image for menus
	 */
	public ConnectPanel(ConnectInput connectinput, Image preGameArt) {
		this.connectinput = connectinput;
		this.preGameArt = preGameArt;
		initPanel();
	}
	
	/**
	 * Inits the panel.
	 */
	private void initPanel() {
		setLayout(null);
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		JButton ipConnectBtn = new JButton("Connect");
		ipConnectBtn.setFont(font1);
		ipConnectBtn.setBounds(300, 240, 200, 60);
		ipConnectBtn.setActionCommand("ipConnectBtn");
		ipConnectBtn.addActionListener(connectinput);
		ipConnectBtn.setVisible(true);
		
		
		ipPanel = new JPanel();
		ipPanel.setLayout(null);
		ipPanel.setBounds(300, 310, 200, 30);
		
		ipLabel = new JLabel("IP:");
		ipLabel.setFont(font1);
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setBounds(5, 0, 55, 30);
		ipLabel.setBackground(Color.WHITE);
		
		ipTextField = new JTextField();
		ipTextField.setFont(font2);
		ipTextField.setHorizontalAlignment(SwingConstants.CENTER);
		ipTextField.setText("127.0.0.1");
		ipTextField.setBounds(60, 0, 140, 30);
		ipTextField.setColumns(10);
		ipTextField.setActionCommand("ipTextField");
		ipTextField.addActionListener(connectinput);
		
		portPanel = new JPanel();
		portPanel.setLayout(null);
		portPanel.setBounds(300, 360, 200, 30);
		
		
		portLabel = new JLabel("Port:");
		portLabel.setFont(font1);
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setBounds(5, 0, 55, 30);
		portLabel.setBackground(Color.WHITE);
		
		portTextField = new JTextField();
		portTextField.setFont(font2);
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);
		portTextField.setText("5050");
		portTextField.setBounds(60, 0, 140, 30);
		portTextField.setColumns(10);
		portTextField.setActionCommand("portTextField");
		portTextField.addActionListener(connectinput);
		
		JButton ipCancelBtn = new JButton("Back");
		ipCancelBtn.setFont(font1);
		ipCancelBtn.setBounds(300, 400, 200, 60);
		ipCancelBtn.setActionCommand("ipCancelBtn");
		ipCancelBtn.addActionListener(connectinput);
		ipCancelBtn.setVisible(true);
		
		add(portPanel);
		add(ipPanel);
		ipPanel.add(ipLabel);
		portPanel.add(portLabel);
		add(ipConnectBtn);
		ipPanel.add(ipTextField);
		portPanel.add(portTextField);
		add(ipCancelBtn);
		
		// Sets the background for the menu
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ipTextField.getText();
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		return portTextField.getText();
	}
}
