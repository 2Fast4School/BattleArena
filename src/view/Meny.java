package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arenaFighter.Main;
import map.Map;
import map.MapGenerator;
import model.GameState;
import controller.Client;
import controller.ConnectInput;
import controller.Game;
import controller.GameInput;
import controller.LobbyInput;
import controller.PreGameInput;
import controller.SettingsInput;

public class Meny extends JFrame {

	private SettingsPanel settingspanel;
	private PreGameWindow pregamewindow;
	private PreGameInput pregameinput;
	private ConnectPanel connectpanel;
	private LobbyPanel lobbypanel;
	private SettingsInput settingsinput;
	private ConnectInput connectinput;
	private LobbyInput lobbyinput;
	private JPanel contentpane;
	private CardLayout cardlayout;
	

	private Client CLIENT;
	private GameState GAMESTATE;
	private GameWindow GAMEWINDOW;
	private GameInput GAMEINPUT;
	private Game GAME;
	private Map MAP;

	
	
	public Meny(String frameName) {
		super(frameName);
		setIconImage(Toolkit.getDefaultToolkit().getImage("res/testa.png"));
		setPreferredSize(new Dimension(800,800));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		setLayout(null);
		cardlayout = new CardLayout();
		contentpane = new JPanel(cardlayout);
		setContentPane(contentpane);
		
		makeOtherObjects();
		
		makeOtherPanels();

		setVisible(true);
	}
	
	private void makeOtherObjects(){

		GAMESTATE = new GameState();
		GAMEINPUT = new GameInput();
		
		BufferedImage logicMap;
		try {
			logicMap=ImageIO.read(Main.class.getResource("/logicMap.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		    logicMap = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		}
		
		MAP = MapGenerator.generateMap(logicMap, "lava", 16);
		
		
	}
	
	private void makeOtherPanels(){
		
		GAMEWINDOW = new GameWindow(null);
		
		
		//lobbypanel = new LobbyPanel();
		pregameinput = new PreGameInput(this);
		pregamewindow = new PreGameWindow(pregameinput);
		
		connectinput = new ConnectInput(this);
		connectpanel = new ConnectPanel(connectinput);
		
		settingsinput = new SettingsInput(this);
		settingspanel = new SettingsPanel(settingsinput);
		
		lobbyinput = new LobbyInput(this, GAMESTATE);
		lobbypanel = new LobbyPanel(lobbyinput);
		
		//contentpane.add(pregamewindow, "LOBBY");
		contentpane.add(connectpanel, "CONNECT");
		contentpane.add(settingspanel, "SETTINGS");
		contentpane.add(pregamewindow, "MENY");
		contentpane.add(GAMEWINDOW, "GAME");
		contentpane.add(lobbypanel, "LOBBY");
		
	}
	
	public void setView(String v){

		switch(v){
		
			case "LOBBY":
				//
				int port = connectinput.getPort();
				String ip = connectinput.getIP();
				GAME = new Game(GAMESTATE);
				CLIENT = new Client(port, ip, GAMESTATE, MAP);
				CLIENT.requestConnection();

				GAMEINPUT.setup(GAMESTATE.returnPlayer());
				GAMESTATE.addObserver(CLIENT);
				GAMESTATE.addObserver(GAMEWINDOW);
				GAMESTATE.addObserver(lobbyinput);
				
				GAME.start();
				new Thread(CLIENT).start();
				
				cardlayout.show(contentpane, "LOBBY");
				//
				break;
	
			case "CONNECT":
				cardlayout.show(contentpane, "CONNECT");
				break;
				
			case "SETTINGS":

				cardlayout.show(contentpane, "SETTINGS");
				break;
				
			case "GAME":
				GAMEWINDOW.addKeyListener(GAMEINPUT);
				GAMEWINDOW.addMouseListener(GAMEINPUT);
				GAMEWINDOW.addMouseMotionListener(GAMEINPUT);
				
				cardlayout.show(contentpane, "GAME");
				break;
				
			case "BACK":
			case "MENY":

				cardlayout.show(contentpane, "MENY");
				break;
				
			default:
				break;
		}
	}
	
}
