package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * <h1>ServerGUI</h1>
 * ServerGUI creates a graphic interface for a dedicated game server with a terminal output,
 * a comboBox for option of network interfaces and buttons to start and end game.
 * @author Oscar Hall
 *
 */
public class ServerGUI implements Observer {

	private static JFrame mainWindow;
	private static JComboBox<String> serverIp;
	private static JTextField serverPort;
	private static JTextArea infoArea;
	private static JPanel buttonArea, serverInfoArea;
	private static JButton startGameBtn, endGameBtn, chooseMapBtn;
	
	private static String ip;
	private static int port = 7020;

	public ServerGUI() {
		mainWindow = new JFrame("Battle Arena Dedicated Server");
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize(500, 400);

		buttonArea = new JPanel();
		serverInfoArea = new JPanel();
		serverIp = new JComboBox<String>();
		serverPort = new JTextField(String.valueOf(port));
		infoArea = new JTextArea("");
		startGameBtn = new JButton("Start Game");
		endGameBtn = new JButton("End Game");
		chooseMapBtn = new JButton("Choose Map");

		// Add components to mainFrame
		mainWindow.add(serverInfoArea, BorderLayout.NORTH);
		mainWindow.add(infoArea);
		mainWindow.add(buttonArea, BorderLayout.SOUTH);
		buttonArea.add(startGameBtn);
		buttonArea.add(endGameBtn);
		buttonArea.add(chooseMapBtn);
		serverInfoArea.add(serverIp, BorderLayout.WEST);
		serverInfoArea.add(serverPort, BorderLayout.EAST);

		// Modifications to components
		serverIp.setBackground(Color.DARK_GRAY);
		serverIp.setForeground(Color.GREEN);
		serverPort.setBackground(Color.DARK_GRAY);
		serverPort.setForeground(Color.GREEN);
		infoArea.setBackground(Color.BLACK);
		infoArea.setForeground(Color.GREEN);
		endGameBtn.setEnabled(false);

		// add data to serverIp dropbux
		updateIpComboBox();

		// Make this bad boy main window show
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add actionListner for start Game button
		startGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoArea.append("Setting up connections.\n");

				ip = serverIp.getSelectedItem().toString();
				port = Integer.parseInt(serverPort.getText());
				Thread t = new Thread(new Runnable() {
					public void run() {
						// Start game server
						//Server s = new Server(numberOfPlayers, ip, port);
						
					}
				});
				t.start();
				infoArea.append("Server started on: " + serverIp.getSelectedItem().toString() + ":"
						+ serverPort.getText() + "\n");
				startGameBtn.setEnabled(false);
				endGameBtn.setEnabled(true);
			}

		});

		// add actionListner for start Game button
		endGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoArea.append("Kicking players\n");

				infoArea.append("Should close sockets here\n");

				// Change state of buttons
				startGameBtn.setEnabled(true);
				endGameBtn.setEnabled(false);
			}

		});

		// Add action listener for the choose map button
		chooseMapBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoArea.append("Open map\n");

			}

		});

	}

	/**
	 * update is used to send information to the terminal output from observed objects
	 */
	@Override
	public void update(Observable o, Object arg) {
		infoArea.append(String.valueOf(arg));
	}

	/**
	 * updateIpComboBox
	 * Update the network interface combobox with available interfaces.
	 */
	private void updateIpComboBox() {
		NetworkHelper n = new NetworkHelper();
		Iterator i = n.getInterfaces().iterator();
		while(i.hasNext())
			serverIp.addItem(i.next().toString());
	}
	
	public String getIpAddress()
	{
		return serverIp.getSelectedItem().toString();
	}
	
	public int getPort()
	{
		return Integer.parseInt(serverPort.getText());
	}

}