package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

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
import map.Map;
import model.GameState;

/**
 * The Class LobbyPanel.
 * When the user connects to a server it ends up in Lobby-mode
 * to wait for all players to join and press ready.
 * Has information about the game-map and who's joined the game
 * 
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 */

public class LobbyPanel extends JPanel implements Observer{

	private LobbyInput lobbyinput;
	
	/** The map image. To be displayed as a minimap */
	private BufferedImage mapImage;
	
	/** The background image for menus. */
	private Image preGameArt;
	
	/** The map name. */
	private String mapName = "";

	private int scaledWidth, scaledHeight;
	private JTextField mapText;
	private JTextField txtPlayer;
	private JTextField playersJoinedTF;
	private int nrPlayers;
	
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
		
		setLayout(null);
		
		Font font1 = new Font("Comic Sans MS", Font.PLAIN, 21);
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 14);
		

		mapText = new JTextField();
		mapText.setBackground(new Color(255, 255, 255));
		mapText.setEditable(false);
		mapText.setHorizontalAlignment(SwingConstants.CENTER);
		mapText.setFont(font1);
		mapText.setText("Map: " + mapName);
		mapText.setBounds(60, 200, 400, 40);
		add(mapText);
		mapText.setColumns(10);
		
		txtPlayer = new JTextField();
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
		
		playersJoinedTF = new JTextField();
		playersJoinedTF.setFont(font2);
		playersJoinedTF.setEditable(false);
		playersJoinedTF.setHorizontalAlignment(SwingConstants.CENTER);
		playersJoinedTF.setText("xx/yy players joined");
		playersJoinedTF.setBounds(600, 200, 140, 40);
		add(playersJoinedTF);
		playersJoinedTF.setColumns(10);
		

		// Scales the game-map to a minimap
		int scaledWidth = 0;
		int scaledHeight = 0;

		// Scales the map to a minimap
	}
	public void setMiniMap(){
		if (mapImage != null) {
			if (mapImage.getHeight()<=mapImage.getWidth()){
				scaledWidth = 400;
				scaledHeight = mapImage.getHeight()*(scaledWidth/mapImage.getWidth());
			}
			else {
				scaledHeight = 400;
				scaledWidth = mapImage.getWidth()*(scaledHeight/mapImage.getHeight());
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
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GameState){
			GameState state=(GameState)arg0;
			if(nrPlayers!=state.getNrPlayers()){
				nrPlayers=state.getNrPlayers();
				playersJoinedTF.setText(nrPlayers+"/"+state.getMaxNrPlayers()+" players joined");
			}
			if(mapName==""){
				mapName=state.getMapName();
				System.out.println(mapName);
				Map map=state.getMap();
				mapImage=map.getBackground();
				txtPlayer.setText(state.getName());
				mapText.setText("Map: " + mapName);
				setMiniMap();
			}
		}
	}
}
