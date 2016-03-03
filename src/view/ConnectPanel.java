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

public class ConnectPanel extends JPanel {
	private ConnectInput connectinput;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JLabel info;
	private Image preGameArt;
	private JPanel ipPanel;
	private JPanel portPanel;
	private JLabel portLabel;
	private JLabel ipLabel;
	
	public ConnectPanel(ConnectInput connectinput, Image preGameArt) {
		this.connectinput = connectinput;
		this.preGameArt = preGameArt;
		initDialog();
	}
	private void initDialog() {
		setLayout(null);
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		// Connect
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
		
		// Cancel button under the IP textfield
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
		
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
	public String getIp() {
		return ipTextField.getText();
	}
	
	public String getPort() {
		return portTextField.getText();
	}
}
