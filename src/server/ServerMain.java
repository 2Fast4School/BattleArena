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
 * Start class for a dedicated server. Creates a controller, starts a ServerGUI and connect them together.
 * 
 * @author Oscar Hall
 *
 */
public class ServerMain {
	private static ServerController sc;
	private static ServerGUI sg;

	
	public static void main(String[] args) {
		
		sg = new ServerGUI();
		sc = new ServerController();
		
		sc.addView(sg);
		sg.addController(sc);
	
	}
	
	
	

}
