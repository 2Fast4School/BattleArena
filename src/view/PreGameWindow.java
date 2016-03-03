package view;

import java.awt.Image;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.PreGameInput;

public class PreGameWindow extends JPanel{

	
	private Image preGameArt;
	private PreGameInput preGameInput;
	private JButton createGameBtn;
	private JButton connectGameBtn;
	private JButton findGameBtn;
	private JButton settingsBtn;
	private JButton quitBtn;
	
	public PreGameWindow(PreGameInput preGameInput, Image preGameArt) {
		setLayout(null);
		this.preGameInput = preGameInput;
		this.preGameArt = preGameArt;
		initPreGameWindow();
	}
	
	private void initPreGameWindow() {
		
		
		setBounds(0, 0, 800, 800);
		
		Font font = new Font("Comic Sans MS", Font.PLAIN, 21);
		
					// Create game
		JButton createGameBtn = new JButton("Create a game");
		createGameBtn.setBounds(300, 240, 200, 60);
		createGameBtn.setFont(font);
		createGameBtn.setActionCommand("createGameBtn");
		createGameBtn.addActionListener(preGameInput);
		add(createGameBtn);
					// Connect game (by IP)
		JButton connectGameBtn = new JButton("Connect by IP");
		connectGameBtn.setBounds(300, 320, 200, 60);
		connectGameBtn.setFont(font);
		connectGameBtn.setActionCommand("connectGameBtn");
		connectGameBtn.addActionListener(preGameInput);
		add(connectGameBtn);
					// Settings
		JButton settingsBtn = new JButton("Settings");
		settingsBtn.setBounds(300, 400, 200, 60);
		settingsBtn.setFont(font);
		settingsBtn.setActionCommand("settingsBtn");
		settingsBtn.addActionListener(preGameInput);
		add(settingsBtn);
					// Quit
		JButton quitBtn = new JButton("Quit");
		quitBtn.setBounds(300, 480, 200, 60);
		quitBtn.setFont(font);
		quitBtn.setActionCommand("quitBtn");
		quitBtn.addActionListener(preGameInput);
		add(quitBtn);
		quitBtn.repaint();
		
		// picLabel is added last because it will then be painted first in the JPanel
		if (preGameArt != null) {
			JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
			add(picLabel);
			picLabel.setBounds(0, 0, 800, 800);
		}
		
	}
	
}
