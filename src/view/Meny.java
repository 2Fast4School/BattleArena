package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arenaFighter.Main;
import controller.Client;
import controller.ConnectInput;
import controller.Game;
import controller.GameInput;
import controller.LobbyInput;
import controller.PreGameInput;
import controller.SettingsInput;
import model.GameState;

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
	private Image preGameArt;

	private Client CLIENT;
	private GameState GAMESTATE;
	private GameWindow GAMEWINDOW;
	private GameInput GAMEINPUT;	
	private Game GAME;

		
	
	public Meny(String frameName) {
		super(frameName);
		try {
			setIconImage(ImageIO.read(Main.class.getResource("/testa.png")));
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
				setIconImage(Toolkit.getDefaultToolkit().getImage("res/testa.png"));
			}
			catch(Exception e2) {
				e2.printStackTrace();
			}
		}
			
		
		setPreferredSize(new Dimension(800,800));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 800);
		this.setLocationRelativeTo(null);
		setLayout(null);

		try {
			preGameArt = ImageIO.read(Main.class.getResource("/pregameart.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if (preGameArt == null) {
			try {
				preGameArt = ImageIO.read(new File("res/pregameart.png"));
			}
			catch(Exception e) {
				e.printStackTrace();
				preGameArt = new BufferedImage(800,800, BufferedImage.TYPE_INT_ARGB);
			}
		}

		makeOtherObjects();
		makeOtherPanels();
		setView("MENY");
		setVisible(true);
	}
	
	private void makeOtherObjects(){
		cardlayout = new CardLayout();
		contentpane = new JPanel(cardlayout);
		setContentPane(contentpane);
		
		GAMESTATE = new GameState();
		GAMEINPUT = new GameInput();
	}
	
	private void makeOtherPanels(){
		
		GAMEWINDOW = new GameWindow(null);
		
		
		//lobbypanel = new LobbyPanel();
		pregameinput = new PreGameInput(this);
		pregamewindow = new PreGameWindow(pregameinput, preGameArt);
		
		connectinput = new ConnectInput(this);
		connectpanel = new ConnectPanel(connectinput, preGameArt);
		
		settingsinput = new SettingsInput(this);
		settingspanel = new SettingsPanel(settingsinput, preGameArt);
		
		lobbyinput = new LobbyInput(this, GAMESTATE);
		lobbypanel = new LobbyPanel(lobbyinput, preGameArt);
		
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
				GAMESTATE.setName(settingspanel.getName());
				int port = connectinput.getPort();
				String ip = connectinput.getIP();
				GAME = new Game(GAMESTATE, this);
				CLIENT = new Client(port, ip, GAMESTATE);
				CLIENT.requestConnection();

				GAMEINPUT.setup(GAMESTATE.returnPlayer());
				GAMESTATE.addObserver(CLIENT);
				GAMESTATE.addObserver(GAMEWINDOW);
				GAMESTATE.addObserver(lobbyinput);
				
				GAME.start();
				CLIENT.start();
				
				cardlayout.show(contentpane, "LOBBY");
				break;
	
			case "CONNECT":
				cardlayout.show(contentpane, "CONNECT");
				break;
				
			case "SETTINGS":

				cardlayout.show(contentpane, "SETTINGS");
				break;
				
			case "GAME":
				this.setSize(GAMESTATE.getBackground().getWidth(),GAMESTATE.getBackground().getHeight());
				GAMEWINDOW.addKeyListener(GAMEINPUT);
				GAMEWINDOW.addMouseListener(GAMEINPUT);
				GAMEWINDOW.addMouseMotionListener(GAMEINPUT);
				
				cardlayout.show(contentpane, "GAME");
				break;
				
			case "BACK":
				makeOtherObjects();
				makeOtherPanels();
				setView("MENY");
				setVisible(true);
			case "MENY":
				this.setSize(800,800);
				cardlayout.show(contentpane, "MENY");
				break;
				
			default:
				break;
		}
	}
	
}
