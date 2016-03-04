package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
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

/**
 * The Class Meny.
 * Creates the window for the whole game (both pregame and gameplay)
 * Holds information about all the submenus with their associated actionlisteners
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 * 
 */
public class Meny extends JFrame {

	/** The settingspanel. Submenu panel for settings*/
	private SettingsPanel settingspanel;
	
	/** The pregamewindow. Main-menu panel*/
	private PreGameWindow pregamewindow;
	
	/** The pregameinput. Exclusive actionlistener for pregamewindow */
	private PreGameInput pregameinput;
	
	/** The connectpanel. Submenu for connecting to a server*/
	private ConnectPanel connectpanel;
	
	/** The lobbypanel. Lobby-mode panel when already connected to a server, but not yet in-game*/
	private LobbyPanel lobbypanel;
	
	/** The settingsinput. Exclusive actionlistener for settingspanel*/
	private SettingsInput settingsinput;
	
	/** The connectinput. Exclusive actionlistener for connectpanel */
	private ConnectInput connectinput;
	
	/** The lobbyinput. Exclusive actionlistener for lobbypanel*/
	private LobbyInput lobbyinput;
	
	/** The contentpane of the frame*/
	private JPanel contentpane;
	
	/** The cardlayout. To select which view to show. See setView method*/
	private CardLayout cardlayout;
	
	/** The background image for menus. */
	private Image preGameArt;

	/** The client. Networking guy. Seen some stuff out there in the world */
	private Client CLIENT;
	
	/** The gamestate. Holds information about objects/players in-game */
	private GameState GAMESTATE;
	
	/** The gamewindow. In-game canvas which takes care of in-game GUI */
	private GameWindow GAMEWINDOW;
	
	/** The gameinput. Exclusive actionlistener for gamewindow */
	private GameInput GAMEINPUT;	
	
	/** The game. Makes the game run/tick/update */
	private Game GAME;

		
	
	/**
	 * Instantiates a new meny.
	 *
	 * @param frameName the title of the window
	 */
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
	
	/**
	 * Make other objects.
	 * Creates all non-panel related objects
	 */
	private void makeOtherObjects(){
		cardlayout = new CardLayout();
		contentpane = new JPanel(cardlayout);
		setContentPane(contentpane);
		
		GAMESTATE = new GameState();
		GAMEINPUT = new GameInput();
	}
	
	/**
	 * Creates the panels (all menus and the in-game window)
	 * Everything is created when application starts and then using
	 * cardlayouts card-switching functionality chosen when needed.
	 */
	private void makeOtherPanels(){
		
		GAMEWINDOW = new GameWindow(null);
		
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
	
	/**
	 * Sets the view.
	 * To change the panel currently showing.
	 *
	 * @param v the new view
	 */
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
