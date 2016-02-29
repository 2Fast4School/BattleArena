package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.PreGameInput;

public class PreGameWindow extends JPanel{
	private GameFrame frame;
	private Image preGameArt;
	private JLabel picLabel;
	private PreGameInput preGameInput;
	private JButton createGameBtn;
	private JButton connectGameBtn;
	private JButton findGameBtn;
	private JButton settingsBtn;
	private JButton quitBtn;
	
	private ConnectDialog connectDialog;
	private SettingsDialog settingsDialog;
	private LobbyDialog lobbyDialog;
	
	private boolean toolTipsEnabled = false;
	
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
		//setBackground(Color.GRAY);
		setBounds(0, 0, 800, 800);
		
					// Create game
		JButton createGameBtn = new JButton("Create a game");
		createGameBtn.setBounds(311, 174, 192, 59);
		createGameBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		createGameBtn.setActionCommand("createGameBtn");
		createGameBtn.addActionListener(preGameInput);
		add(createGameBtn);
		
						// In the server terminal that is started there should be a "Make public" button.
					// Connect game (by IP)
		JButton connectGameBtn = new JButton("Connect by IP");
		connectGameBtn.setBounds(311, 244, 192, 59);
		connectGameBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		connectGameBtn.setActionCommand("connectGameBtn");
		connectGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((connectDialog instanceof ConnectDialog) == false || connectDialog.isShowing() == false) {
					connectDialog = new ConnectDialog(frame);
				}
			}
		});
		add(connectGameBtn);
					// Find game (checks RSS feed)
		JButton findGameBtn = new JButton("Find a game");
		findGameBtn.setBounds(311, 314, 192, 59);
		findGameBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		findGameBtn.setActionCommand("findGameBtn");
		findGameBtn.addActionListener(preGameInput);
		add(findGameBtn);
					// Settings
		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setBounds(311, 384, 192, 59);
		settingsBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		settingsBtn.setActionCommand("settingsBtn");
		settingsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((settingsDialog instanceof SettingsDialog) == false || settingsDialog.isShowing() == false) {
					settingsDialog = new SettingsDialog(frame);
				}
			}
		});
		add(settingsBtn);
					// Quit
		JButton quitBtn = new JButton("Quit");
		quitBtn.setBounds(311, 454, 192, 59);
		quitBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 21));
		quitBtn.setActionCommand("quitBtn");
		quitBtn.addActionListener(preGameInput);
		add(quitBtn);
		quitBtn.repaint();
		
		// picLabel is added last because it will then be painted first in the JPanel
		JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
		add(picLabel);
		picLabel.setBounds(0, 0, 800, 800);
		
	}
	
		//
		
		// method - Create game
		
		// method - Connect by IP
	
	public void toggleToolTips(boolean toggleToolTips) {
		if (toggleToolTips) {
			createGameBtn.setToolTipText("Creates a server in a separate window and tries to connect to it");
			connectGameBtn.setToolTipText("Tries to connect to a server using an IPv4 address");
			findGameBtn.setToolTipText("Using black magic, it is possible to search for available public games");
			settingsBtn.setToolTipText("Change your displaying name and more");
			quitBtn.setToolTipText("Exits the BattleArena game (I know, this is a stupid button. Why would you wanna quit?)");
		}
		else {
			createGameBtn.setToolTipText(null);
			connectGameBtn.setToolTipText(null);
			findGameBtn.setToolTipText(null);
			settingsBtn.setToolTipText(null);
			quitBtn.setToolTipText(null);
		}
		toolTipsEnabled = toggleToolTips;
	}
	
	public boolean checkToolTipsEnabled() {
		return toolTipsEnabled;
	}
}
