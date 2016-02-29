package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import arenaFighter.Main;
import map.Map;
import map.MapGenerator;
import model.GameState;
import view.ConnectDialog;
import view.GameFrame;
import view.GameWindow;

public class ConnectDialogInput implements ActionListener {
	private ConnectDialog connectDialog;
	private GameFrame frame;
	private static final int numberOfPlayers = 2;
	
	private static Client client;
	private static GameState state;
	private static Game game;
	private static GameWindow window;
	private static GameInput input;
	public ConnectDialogInput(GameFrame frame, ConnectDialog connectDialog) {
		this.connectDialog = connectDialog;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "ipConnectBtn":
			connect(connectDialog.getIp(),connectDialog.getPort());
			connectDialog.dispose();
			break;
		case "ipCancelBtn":
			connectDialog.dispose();
			break;
		default:

			break;
		}
	}

	private void connect(String ip, int port) {
		Main.gameSetup(ip, port);
	}
}
