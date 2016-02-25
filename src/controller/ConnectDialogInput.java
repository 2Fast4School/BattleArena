package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import map.Map;
import map.MapGenerator;
import model.GameState;
import view.ConnectDialog;
import view.GameFrame;
import view.GameWindow;

public class ConnectDialogInput implements ActionListener {
	private ConnectDialog connectDialog;
	private static final int numberOfPlayers = 2;
	private static final int port=1337;
	private GameFrame frame;
	public ConnectDialogInput(GameFrame frame, ConnectDialog connectDialog) {
		this.connectDialog = connectDialog;
		this.frame = frame;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getActionCommand()) {
		case "ipConnectBtn":
			connect(connectDialog.getIp());
			break;

		default:

			break;
		}
	}

	private void connect(String ip) {

		for(int n=0;n<numberOfPlayers;n++){
			// I gamelobby n�r man connectat och f�tt information fr�n servern om vad f�r bana
			Map map = MapGenerator.generateMap("res/mapBackground.png", "grass");
			GameState state=new GameState(numberOfPlayers, map);
			GameInput gameInput = new GameInput();
			GameWindow gameWindow=new GameWindow(gameInput,map.getBackground());
			Client client = new Client(port, ip, state);
			Game game=new Game(state);
			// separera port, ip fr�n state.
			//Client startas n�r connectGame har gjorts och ip och port har valts.
			//state f�r initieras senare, n�r servern har skickat det.
			

			// samma som f�r state ovan
			
			state.setup();
			gameInput.setup(state.returnPlayer());
			
			frame.switchToGameWindow();
			
			state.addObserver(client);
			state.addObserver(gameWindow);
			// N�r spelet ska startas
			game.start();

			// N�r man klickat connect i connect by ip dialog och ip och port lagts till
			new Thread(client).start();
		}

	}
}
