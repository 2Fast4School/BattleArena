package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
	private static JTextField serverPort;
	private static JTextArea infoArea;
	private static JScrollPane infoScrollPane;
	private static JPanel buttonArea, serverInfoArea;
	private static JButton startGameBtn, endGameBtn, chooseMapBtn;
	
	private static int port = 1337;

	public ServerGUI() {
		mainWindow = new JFrame("Battle Arena Dedicated Server");
		mainWindow.setIconImage(Toolkit.getDefaultToolkit().getImage("res/testa.png"));
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize(500, 400);

		buttonArea = new JPanel();
		serverInfoArea = new JPanel();
		serverIp = new JComboBox<String>();
		serverPort = new JTextField(String.valueOf(port));
		infoArea = new JTextArea("");
		infoScrollPane = new JScrollPane(infoArea);
		startGameBtn = new JButton("Start Game");
		endGameBtn = new JButton("End Game");
		chooseMapBtn = new JButton("Choose Map");

		// Add components to mainFrame
		mainWindow.add(serverInfoArea, BorderLayout.NORTH);
		mainWindow.add(infoScrollPane);
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
	}
	
	public String getIpAddress()
	{
		return serverIp.getSelectedItem().toString();
	}
	
	public int getPort()
	{
		return Integer.parseInt(serverPort.getText());
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