package view;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ConnectDialogInput;

public class ConnectDialog extends JDialog {
	private ConnectDialogInput connectDialogInput;
	private JTextField ipTextField;
	private GameFrame frame;
	public ConnectDialog(GameFrame frame) {
		this.frame = frame;
		initDialog();
	}
	public void initDialog() {
		ConnectDialogInput connectDialogInput = new ConnectDialogInput(frame, this);
		setTitle("Connect by IP");
		setLayout(null);
		setAlwaysOnTop(true);
		setLocation(300, 300);
		setPreferredSize(new Dimension(200, 200));		
		pack();
		setVisible(true);
		
		// Connect button over the IP textfield
		JButton ipConnectBtn = new JButton("Connect");
		ipConnectBtn.setBounds(20, 20, 111, 21);
		ipConnectBtn.setActionCommand("ipConnectBtn");
		//ipConnectBtn.addActionListener(preGameInput);
		ipConnectBtn.addActionListener(connectDialogInput);
		ipConnectBtn.setVisible(true);

		ipTextField = new JTextField();
		ipTextField.setHorizontalAlignment(SwingConstants.CENTER);
		ipTextField.setText("127.0.0.1");
		ipTextField.setBounds(20, 45, 111, 21);
		ipTextField.setColumns(10);

		add(ipConnectBtn);
		add(ipTextField);
	}
	
	public String getIp() {
		return ipTextField.getText();
	}
}
