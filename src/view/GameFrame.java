package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Client;
import controller.ConnectInput;
import controller.GameInput;
import controller.PreGameInput;
import controller.SettingsInput;

public class GameFrame extends JFrame {
	private JPanel contentPane;
	private PreGameWindow preGameWindow;
	private GameWindow gameWindow;
	private PreGameInput preGameInput;
	private GameInput gameInput;
	private Client client;
	private SettingsPanel settingspanel;
	private PreGameWindow pregamewindow;
	private PreGameInput pregameinput;
	private ConnectPanel connectpanel;
	//private LobbyPanel lobbypanel;
	private SettingsInput settingsinput;
	private ConnectInput connectinput;
	//private LobbyInput lobbyinput;
	private JPanel contentpane;
	private CardLayout cardlayout;
	

	

	
	
	public GameFrame(String frameName) {
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
		makeOtherPanels();
		setVisible(true);
	}
	
	private void makeOtherPanels(){
		
		//lobbypanel = new LobbyPanel();
		pregameinput = new PreGameInput(this);
		pregamewindow = new PreGameWindow(pregameinput);
		
		connectinput = new ConnectInput(this);
		connectpanel = new ConnectPanel(connectinput);
		
		settingsinput = new SettingsInput(this);
		settingspanel = new SettingsPanel(settingsinput);
		
		//contentpane.add(pregamewindow, "LOBBY");
		contentpane.add(connectpanel, "CONNECT");
		contentpane.add(settingspanel, "SETTINGS");
		contentpane.add(pregamewindow, "MENY");
		
	}
	
	public void setView(String v){

		switch(v){
		
			case "LOBBY":
				break;
	
			case "CONNECT":
				cardlayout.show(contentpane, "CONNECT");
				break;
				
			case "SETTINGS":

				cardlayout.show(contentpane, "SETTINGS");
				break;
				
			case "GAME":
				break;
				
			case "BACK":
			case "MENY":

				cardlayout.show(contentpane, "MENY");
				break;
				
			default:
				break;
		}
	}
	

	public boolean getToolTipsBool() {
		return preGameWindow.checkToolTipsEnabled();
	}
	
	public void toolsTipsEnable(boolean enable) {
		preGameWindow.toggleToolTips(enable);
	}
	public void setClient(Client client){this.client=client;}
}
