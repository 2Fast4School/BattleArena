package view;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.Client;
import controller.ConnectDialogInput;
import model.GameState;

public class ConnectDialog extends JDialog {
	private ConnectDialogInput connectDialogInput;
	private JTextField ipTextField;
	private JTextField portTextField;
	private GameFrame frame;
	
	public ConnectDialog(GameFrame frame) {
		this.frame = frame;
		initDialog();
	}
	public void initDialog() {
		ConnectDialogInput connectDialogInput = new ConnectDialogInput(frame, this);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Connect by IP");
		setLayout(null);
		setAlwaysOnTop(true);
		setLocation(300, 300);
		setPreferredSize(new Dimension(200, 200));		
		setResizable(false);
		pack();
		setVisible(true);
		
		// Connect button over the IP textfield
		JButton ipConnectBtn = new JButton("Connect");
		ipConnectBtn.setBounds(20, 20, 111, 21);
		ipConnectBtn.setActionCommand("ipConnectBtn");
		ipConnectBtn.addActionListener(connectDialogInput);
		ipConnectBtn.setVisible(true);
		
		

		ipTextField = new JTextField();
		ipTextField.setHorizontalAlignment(SwingConstants.CENTER);
		ipTextField.setText("127.0.0.1");
		ipTextField.setBounds(20, 45, 111, 21);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setHorizontalAlignment(SwingConstants.CENTER);
		portTextField.setText("1337");
		portTextField.setBounds(135, 45, 40, 21);
		portTextField.setColumns(10);
		
		// Cancel button under the IP textfield
		JButton ipCancelBtn = new JButton("Cancel");
		ipCancelBtn.setBounds(20, 70, 111, 21);
		ipCancelBtn.setActionCommand("ipCancelBtn");
		ipCancelBtn.addActionListener(connectDialogInput);
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
