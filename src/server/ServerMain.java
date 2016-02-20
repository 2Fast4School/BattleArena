package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ServerMain {

	private static JFrame mainWindow;
	private static JTextField serverIp, serverPort;
	private static JTextArea infoArea;
	private static JPanel buttonArea, serverInfoArea;
	private static JButton startGameBtn, chooseMapBtn;
	
	private static final int numberOfPlayers=2;
	private static String ip = "127.0.0.1";
	private static int port=7020;
	
	public static void main(String[] args) {
		
		mainWindow = new JFrame("Battle Arena Dedicated Server");
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setSize(500, 400);
		
		serverIp = new JTextField(ip);
		serverPort = new JTextField(String.valueOf(port));
		
		serverInfoArea = new JPanel();
		
		infoArea = new JTextArea("");
		infoArea.setBackground(Color.BLACK);
		infoArea.setForeground(Color.GREEN);
		
		buttonArea = new JPanel();
		
		startGameBtn = new JButton("Start Game");
		chooseMapBtn = new JButton("Choose Map");
		
		//Add components to mainFrame
		mainWindow.add(serverInfoArea, BorderLayout.NORTH);
		mainWindow.add(infoArea);
		mainWindow.add(buttonArea, BorderLayout.SOUTH);
		buttonArea.add(startGameBtn);
		buttonArea.add(chooseMapBtn);
		serverInfoArea.add(serverIp, BorderLayout.WEST);
		serverInfoArea.add(serverPort, BorderLayout.EAST);
		
		
		//Modifications to components
		//serverIp.setHorizontalAlignment(JTextField.CENTER);
		serverIp.setBackground(Color.DARK_GRAY);
		serverIp.setForeground(Color.GREEN);
		serverPort.setBackground(Color.DARK_GRAY);
		serverPort.setForeground(Color.GREEN);
		
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
		
		//add actionListners
		startGameBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoArea.append("Setting up connections.\n");
				
				ip = serverIp.getText();
				port = Integer.parseInt(serverPort.getText());
				Thread t = new Thread(new Runnable(){
					public void run(){
						try {
							Server s = new Server(numberOfPlayers, ip, port);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				t.start();
				infoArea.append("Server started on: "+serverIp.getText()+":"+serverPort.getText());
			}
			
		});
		
		chooseMapBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoArea.append("Open map\n");
				
			}
			
		});
		
		

	}
	
	
	public void updateServerLoad(String arg)
	{
		serverIp.setText(arg);
	}

}
