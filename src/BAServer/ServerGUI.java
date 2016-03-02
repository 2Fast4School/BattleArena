package BAServer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private static JTextField serverPort, nrOfPlayers;
	private static JTextArea infoArea;
	private static JScrollPane infoScrollPane;
	private static JPanel buttonArea, serverInfoArea;
	private static JButton startGameBtn, endGameBtn, chooseMapBtn;
	
	private static int port = 5050;

	public ServerGUI() {
		mainWindow = new JFrame("Battle Arena Dedicated Server");
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize(700, 400);

		buttonArea = new JPanel();
		serverInfoArea = new JPanel();
		serverIp = new JComboBox<String>();
		serverPort = new JTextField(String.valueOf(port));
		nrOfPlayers = new JTextField(" 2 ");
		infoArea = new JTextArea("");
		infoScrollPane = new JScrollPane(infoArea);
		startGameBtn = new JButton("Start Game");
		endGameBtn = new JButton("End Game");
		chooseMapBtn = new JButton("Choose Map");
		JLabel IPLabel = new JLabel("IP adresses:");
		JLabel portLabel = new JLabel("Port:");
		JLabel nrOfPlayersLabel = new JLabel("Number of players:");

		// Add components to mainFrame
		mainWindow.add(serverInfoArea, BorderLayout.NORTH);
		mainWindow.add(infoScrollPane);
		mainWindow.add(buttonArea, BorderLayout.SOUTH);
		buttonArea.add(startGameBtn);
		buttonArea.add(endGameBtn);
		buttonArea.add(chooseMapBtn);
		serverInfoArea.add(IPLabel);
		serverInfoArea.add(serverIp);
		serverInfoArea.add(portLabel);
		serverInfoArea.add(serverPort);
		serverInfoArea.add(nrOfPlayersLabel);
		serverInfoArea.add(nrOfPlayers);
		

		// Modifications to components
		serverIp.setBackground(Color.DARK_GRAY);
		serverIp.setForeground(Color.GREEN);
		nrOfPlayers.setBackground(Color.DARK_GRAY);
		nrOfPlayers.setForeground(Color.GREEN);
		serverPort.setBackground(Color.DARK_GRAY);
		serverPort.setForeground(Color.GREEN);
		infoArea.setBackground(Color.BLACK);
		infoArea.setForeground(Color.GREEN);
		endGameBtn.setEnabled(false);

		// add data to serverIp ComboBox
		updateIpComboBox();

		// Make this bad boy main window show
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * update is used to send information to the terminal output from observed objects
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		String[] message = new String((byte[])arg).trim().split(",");
		int OPcode = Integer.parseInt(message[0]);
		String id = message[1];
	
		//Check if attack OP = 2
		if(OPcode == 2)
		{
			infoArea.append(id + " is attacking!\n");
			infoArea.setCaretPosition(infoArea.getDocument().getLength());
		}
	}
	
	public void toTerminal(String text)
	{
		infoArea.append(text);
	}

	/**
	 * updateIpComboBox
	 * Update the network interface combobox with available interfaces.
	 */
	private void updateIpComboBox() {
		NetworkHelper n = new NetworkHelper();
		Iterator<String> i = n.getInterfaces().iterator();
		while(i.hasNext())
			serverIp.addItem(i.next().toString());
		
		//Set Loopback as default adress
		serverIp.setSelectedIndex(serverIp.getItemCount()-1);
	}
	
	public String getIpAddress()
	{
		return serverIp.getSelectedItem().toString();
	}
	
	public int getPort()
	{
		return Integer.parseInt(serverPort.getText());
	}
	
	public int getNrOfPlayers()
	{
		return Integer.parseInt(nrOfPlayers.getText());
	}
	
	public void addController(ActionListener controller){
		System.out.println("View      : adding controller");
		startGameBtn.addActionListener(controller);	
		endGameBtn.addActionListener(controller);
		chooseMapBtn.addActionListener(controller);	
	} //addController()
	
	
	/**
	 * Switches enabled state on Start Game button and End Game Button
	 */
	public void switchButtonState()
	{
		if(startGameBtn.isEnabled())
		{
			startGameBtn.setEnabled(false);
			endGameBtn.setEnabled(true);
		}
		else
		{
			startGameBtn.setEnabled(true);	
			endGameBtn.setEnabled(false);
		}
	}
}