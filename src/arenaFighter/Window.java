package arenaFighter;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Client;
import controller.ConnectInput;
import controller.Game;
import controller.GameInput;
import controller.LobbyInput;
import controller.MenuInput;
import controller.SettingsInput;
import model.GameState;
import view.ConnectPanel;
import view.GameCanvas;
import view.LobbyPanel;
import view.MenuPanel;
import view.SettingsPanel;

/**
 * The Class Window.
 * Creates the window for the whole game (both pregame and gameplay)
 * Holds information about all the submenus with their associated actionlisteners,
 * From user inputs the class also starts gamestate
 * @author Fred Hedenberg
 * @version 1.0 2016-03-03
 * 
 */
@SuppressWarnings("serial")
public class Window extends JFrame {

	/** The settingspanel. Submenu panel for settings*/
	private SettingsPanel settingsPanel;
	
	/** The pregamewindow. Main-menu panel*/
	private MenuPanel menuPanel;
	
	/** The pregameinput. Exclusive actionlistener for pregamewindow */
	private MenuInput menuInput;
	
	/** The connectpanel. Submenu for connecting to a server*/
	private ConnectPanel connectPanel;
	
	/** The lobbypanel. Lobby-mode panel when already connected to a server, but not yet in-game*/
	private LobbyPanel lobbyPanel;
	
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
	
	/** The gamecanvas. In-game canvas which takes care of in-game GUI */
	private GameCanvas GAMECANVAS;
	
	/** The gameinput. Exclusive actionlistener for gamecanvas */
	private GameInput GAMEINPUT;	
	
	/** The game. Makes the game run/tick/update */
	private Game GAME;

		
	
	/**
	 * Instantiates the Window.
	 *
	 * @param frameName the title of the window
	 */
	public Window(String frameName) {
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
		setView("MENU");
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
	 * Creates the panels (all menus and the in-game canvas)
	 * Everything is created when application starts and then using
	 * cardlayouts card-switching functionality chosen when needed.
	 */
	private void makeOtherPanels(){
		
		GAMECANVAS = new GameCanvas(null);
		
		menuInput = new MenuInput(this);
		menuPanel = new MenuPanel(menuInput, preGameArt);
		
		connectinput = new ConnectInput(this);
		connectPanel = new ConnectPanel(connectinput, preGameArt);
		
		settingsinput = new SettingsInput(this);
		settingsPanel = new SettingsPanel(settingsinput, preGameArt);
		
		lobbyinput = new LobbyInput(this, GAMESTATE);
		lobbyPanel = new LobbyPanel(lobbyinput, preGameArt);
		
		//contentpane.add(pregamewindow, "LOBBY");
		contentpane.add(connectPanel, "CONNECT");
		contentpane.add(settingsPanel, "SETTINGS");
		contentpane.add(menuPanel, "MENU");
		contentpane.add(GAMECANVAS, "GAME");
		contentpane.add(lobbyPanel, "LOBBY");
		
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
				GAMESTATE.setName(settingsPanel.getName());
				int port = connectinput.getPort();
				String ip = connectinput.getIP();
				GAME = new Game(GAMESTATE, this);
				CLIENT = new Client(port, ip, GAMESTATE);
				CLIENT.requestConnection();

				GAMEINPUT.setup(GAMESTATE.returnPlayer());
				GAMESTATE.addObserver(lobbyPanel);
				GAMESTATE.addObserver(CLIENT);
				GAMESTATE.addObserver(GAMECANVAS);
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
				this.setSize(GAMESTATE.getBackground().getWidth()+10,GAMESTATE.getBackground().getHeight()+45);
				GAMECANVAS.addKeyListener(GAMEINPUT);
				GAMECANVAS.addMouseListener(GAMEINPUT);
				GAMECANVAS.addMouseMotionListener(GAMEINPUT);
				
				cardlayout.show(contentpane, "GAME");
				break;
				
			case "BACK":
				makeOtherObjects();
				makeOtherPanels();
				setView("MENU");
				setVisible(true);
			case "MENU":
				this.setSize(800,800);
				cardlayout.show(contentpane, "MENU");
				break;
				
			default:
				break;
		}
	}
	
}
