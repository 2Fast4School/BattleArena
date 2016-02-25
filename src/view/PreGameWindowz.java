package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class PreGameWindowz extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PreGameWindowz() {
		setBackground(Color.GRAY);
		setLayout(null);
		
		JButton btnCreateGame = new JButton("Create game");
		btnCreateGame.setBounds(311, 174, 192, 59);
		add(btnCreateGame);
		btnCreateGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		btnCreateGame.setForeground(Color.BLACK);
		
		JButton btnConnectByIp = new JButton("Connect by IP");
		btnConnectByIp.setForeground(Color.BLACK);
		btnConnectByIp.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		btnConnectByIp.setBounds(311, 244, 192, 59);
		add(btnConnectByIp);
		
		JButton btnFindGame = new JButton("Find game");
		btnFindGame.setForeground(Color.BLACK);
		btnFindGame.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		btnFindGame.setBounds(311, 314, 192, 59);
		add(btnFindGame);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setForeground(Color.BLACK);
		btnSettings.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		btnSettings.setBounds(311, 384, 192, 59);
		add(btnSettings);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setToolTipText("I know, why would you wanna quit?");
		btnQuit.setSize(192, 59);
		btnQuit.setLocation(311, 454);
		btnQuit.setForeground(Color.BLACK);
		
		add(btnQuit);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("255.255.255.255");
		textField.setBounds(528, 275, 111, 21);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(528, 249, 111, 21);
		add(btnNewButton);

	}
}
