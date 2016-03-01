package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ConnectInput;

public class ConnectPanel extends JPanel {
	private ConnectInput connectinput;
	private JTextField ipTextField;
	private JTextField portTextField;
	private GameFrame frame;
	
	public ConnectPanel(ConnectInput connectinput) {
		this.connectinput = connectinput;
		initDialog();
	}
	public void initDialog() {
		
		// Connect button over the IP textfield
		JButton ipConnectBtn = new JButton("Connect");
		ipConnectBtn.setBounds(20, 20, 111, 21);
		ipConnectBtn.setActionCommand("ipConnectBtn");
		ipConnectBtn.addActionListener(connectinput);
		ipConnectBtn.setVisible(true);
		
		

		ipTextField = new JTextField();
		ipTextField.setHorizontalAlignment(SwingConstants.CENTER);
		ipTextField.setText("127.0.0.1");
		ipTextField.setBounds(20, 45, 111, 21);
		ipTextField.setColumns(10);
		ipTextField.setActionCommand("ipTextField");
		ipTextField.addActionListener(connectinput);
		
		portTextField = new JTextField();
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);
		portTextField.setText("1337");
		portTextField.setBounds(135, 45, 40, 21);
		portTextField.setColumns(10);
		portTextField.setActionCommand("portTextField");
		portTextField.addActionListener(connectinput);
		
		// Cancel button under the IP textfield
		JButton ipCancelBtn = new JButton("Cancel");
		ipCancelBtn.setBounds(20, 70, 111, 21);
		ipCancelBtn.setActionCommand("ipCancelBtn");
		ipCancelBtn.addActionListener(connectinput);
		ipCancelBtn.setVisible(true);

		add(ipConnectBtn);
		add(ipTextField);
		add(portTextField);
		add(ipCancelBtn);
	}
	
	public String getIp() {
		return ipTextField.getText();
	}
	
	public int getPort() {
		return Integer.parseInt(portTextField.getText());
	}
}
