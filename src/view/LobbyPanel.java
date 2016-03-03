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

/**
 * The Class LobbyPanel.
 * When the user connects to a server it ends up in Lobby-mode
 * to wait for all players to join and press ready.
 * Has information about the game-map and who's joined the game
 * 
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */
public class LobbyPanel extends JPanel {
	
	/** The lobbyinput. Exclusive actionlistener for this class */
	private LobbyInput lobbyinput;
	
	/** The map image. To be displayed as a minimap */
	private BufferedImage mapImage;
	
	/** The background image for menus. */
	private Image preGameArt;
	
	/** The map name. */
	private String mapName = "";
	
	/** The scaled (new) height and width of the minimap. */
	private int scaledWidth, scaledHeight;
	
	/**
	 * Instantiates a new lobby panel.
	 *
	 * @param lobbyinput the lobbyinput (actionlistener)
	 * @param preGameArt the background image for menus
	 */
	public LobbyPanel(LobbyInput lobbyinput, Image preGameArt) {
		this.lobbyinput = lobbyinput;
		this.preGameArt = preGameArt;
		initPanel();
	}
	
	/**
	 * Inits the panel.
	 */
	private void initPanel() {
		
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		setLayout(null);
		JTextField miniMapNameTF = new JTextField();
		miniMapNameTF.setBackground(new Color(255, 255, 255));
		miniMapNameTF.setEditable(false);
		miniMapNameTF.setHorizontalAlignment(SwingConstants.CENTER);
		miniMapNameTF.setFont(font1);
		miniMapNameTF.setText("Map: " + mapName);
		miniMapNameTF.setBounds(60, 200, 400, 40);
		add(miniMapNameTF);
		miniMapNameTF.setColumns(10);
		
		JTextField txtPlayer = new JTextField();
		txtPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtPlayer.setEditable(false);
		txtPlayer.setText("Player 1");
		txtPlayer.setBounds(600, 250, 140, 300);
		add(txtPlayer);
		txtPlayer.setColumns(10);
		
		JCheckBox readyCheckBox = new JCheckBox("Ready");
		readyCheckBox.setFont(font1);
		readyCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		readyCheckBox.setBounds(600, 550, 140, 40);
		readyCheckBox.setActionCommand("chckbxReady");
		readyCheckBox.addActionListener(lobbyinput);
		add(readyCheckBox);
		
		JButton leaveBtn = new JButton("Leave");
		leaveBtn.setActionCommand("leaveBtn");
		leaveBtn.addActionListener(lobbyinput);
		leaveBtn.setFont(font1);
		leaveBtn.setBounds(600, 600, 140, 50);
		add(leaveBtn);
		
		JTextField playersJoinedTF = new JTextField();
		playersJoinedTF.setFont(font2);
		playersJoinedTF.setEditable(false);
		playersJoinedTF.setHorizontalAlignment(SwingConstants.CENTER);
		playersJoinedTF.setText("xx/yy players joined");
		playersJoinedTF.setBounds(600, 200, 140, 40);
		add(playersJoinedTF);
		playersJoinedTF.setColumns(10);
		
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

		// Scales the game-map to a minimap
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

			// Sets the background for the menu
			if (preGameArt != null) {
				JLabel picLabel = new JLabel(new ImageIcon(preGameArt));
				add(picLabel);
				picLabel.setBounds(0, 0, 800, 800);
			}
		}

	}
}
