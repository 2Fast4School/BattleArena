package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import arenaFighter.Main;
import controller.LobbyInput;

public class LobbyPanel extends JPanel {
	private LobbyInput lobbyinput;
	private BufferedImage mapImage;
	private Image preGameArt;
	private String mapName = "";
	private int scaledWidth, scaledHeight;
	
	public LobbyPanel(LobbyInput lobbyinput, Image preGameArt) {
		this.lobbyinput = lobbyinput;
		this.preGameArt = preGameArt;
		initDialog();
	}
	private void initDialog() {
		
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		JTextField txtMapAlexanderspahitt = new JTextField();
		txtMapAlexanderspahitt.setBackground(new Color(255, 255, 255));
		txtMapAlexanderspahitt.setEditable(false);
		txtMapAlexanderspahitt.setHorizontalAlignment(SwingConstants.CENTER);
		txtMapAlexanderspahitt.setFont(font1);
		txtMapAlexanderspahitt.setText("Map: " + mapName);
		txtMapAlexanderspahitt.setBounds(60, 200, 400, 40);
		add(txtMapAlexanderspahitt);
		txtMapAlexanderspahitt.setColumns(10);
		
		JTextField txtPlayer = new JTextField();
		txtPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlayer.setEditable(false);
		txtPlayer.setText("Player 1");
		txtPlayer.setBounds(600, 250, 140, 300);
		add(txtPlayer);
		txtPlayer.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Ready");
		chckbxNewCheckBox.setFont(font1);
		chckbxNewCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxNewCheckBox.setBounds(600, 550, 140, 40);
		chckbxNewCheckBox.setActionCommand("chckbxReady");
		chckbxNewCheckBox.addActionListener(lobbyinput);
		add(chckbxNewCheckBox);
		
		JButton btnNewButton_1 = new JButton("Leave");
		btnNewButton_1.setActionCommand("leaveBtn");
		btnNewButton_1.addActionListener(lobbyinput);
		btnNewButton_1.setFont(font1);
		btnNewButton_1.setBounds(600, 600, 140, 50);
		add(btnNewButton_1);
		
		JTextField txtXyPlayersJoined_1 = new JTextField();
		txtXyPlayersJoined_1.setFont(font2);
		txtXyPlayersJoined_1.setEditable(false);
		txtXyPlayersJoined_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtXyPlayersJoined_1.setText("xx/yy players joined");
		txtXyPlayersJoined_1.setBounds(600, 200, 140, 40);
		add(txtXyPlayersJoined_1);
		txtXyPlayersJoined_1.setColumns(10);
		
		try {
			mapImage = ImageIO.read(Main.class.getResource("/pregameart.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if (preGameArt == null) {
			try {
				mapImage = ImageIO.read(new File("res/pregameart.png"));
			}
			catch(Exception e) {
				e.printStackTrace();
				mapImage = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
			}
		}

		// Scales the map to a minimap
		if (mapImage != null) {
			if (mapImage.getHeight()<=mapImage.getWidth()){
				int scaledWidth = 400;
				int scaledHeight = mapImage.getHeight()*(scaledWidth/mapImage.getWidth());
			}
			else {
				int scaledHeight = 400;
				int scaledWidth = mapImage.getWidth()*(scaledHeight/mapImage.getHeight());
			}
			if (scaledWidth <= 0 || scaledHeight <= 0) {
				scaledWidth = 400;
				scaledHeight = 400;
			}
			JLabel miniMapLabel = new JLabel(new ImageIcon(mapImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT)));
			miniMapLabel.setBounds(60, 250, 400, 400);
			add(miniMapLabel);

			if (preGameArt != null) {
				JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
				add(picLabel);
				picLabel.setBounds(0, 0, 800, 800);
			}
		}

	}
}
