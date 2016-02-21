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

/**
 * <h1>ServerMain</h1>
 * Start class for a dedicated server. Creates a server instance and starts a ServerGUI.
 * 
 * @author Oscar Hall
 *
 */
public class ServerMain {

	private static ServerGUI sg = new ServerGUI();
	private static Server s;
	
	public static void main(String[] args) {
		
		sg = new ServerGUI();
		//s = new Server();
	
	}
	
	public void startServer()
	{
		
	}
	

}
