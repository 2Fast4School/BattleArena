package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Container;

import controller.PreGameInput;

public class PreGameWindow extends JPanel{
	private GameFrame frame;
	private Image preGameArt;
	private PreGameInput preGameInput;
	
	private JPanel northPanel;
	
	private JButton createGameBtn;
	private JButton connectGameBtn;
	private JButton findGameBtn;
	private JButton settingsBtn;
	private JButton quitBtn;
	
	public PreGameWindow(GameFrame frame) {
		this.frame = frame;
		initPreGameWindow();
	}
	
	private void initPreGameWindow() {
		//Image of gameArt~
		try {
			preGameArt = ImageIO.read(new File("res/pregameart.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
			preGameArt = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
		}
		// imagePanel.clearImage(); to make it disappear.
		
		PreGameInput preGameInput = new PreGameInput(this);
				//Overlapping the image with the following buttons:
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel(new GridLayout());
		add(northPanel);
		
					// Create game
		JButton createGameBtn = new JButton("Create a game");
		createGameBtn.setActionCommand("createGameBtn");
		createGameBtn.addActionListener(preGameInput);
		northPanel.add(createGameBtn);
		
						// In the server terminal that is started there should be a "Make public" button.
					// Connect game (by IP)
		JButton connectGameBtn = new JButton("Connect by IP");
		connectGameBtn.setActionCommand("connectGameBtn");
		connectGameBtn.addActionListener(preGameInput);
		northPanel.add(connectGameBtn);
					// Find game (checks RSS feed)
		JButton findGameBtn = new JButton("Find a game");
		findGameBtn.setActionCommand("findGameBtn");
		findGameBtn.addActionListener(preGameInput);
		northPanel.add(findGameBtn);
					// Settings
		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setActionCommand("settingsBtn");
		settingsBtn.addActionListener(preGameInput);
		northPanel.add(settingsBtn);
					// Quit
		JButton quitBtn = new JButton("Quit");
		quitBtn.setActionCommand("quitBtn");
		quitBtn.addActionListener(preGameInput);
		northPanel.add(quitBtn);
		
	}
	
		//
		
		// method - Create game
		
		// method - Connect by IP
	public void showConnectDialogue() {
		JOptionPane dialog = new JOptionPane();
		dialog.addAncestorListener(preGameInput);
		dialog.showInputDialog("Insert IP");
		add(dialog);
		JButton dialogConnectBtn = new JButton("Connect");
		dialogConnectBtn.setActionCommand("dialogConnectBtn");
		dialogConnectBtn.addActionListener(preGameInput);
		dialog.add(dialogConnectBtn);
	}
		
		// method - Find game
		
		// 
		
		// Method - 
		// Starting the game will send the JFrame and 
		

}
