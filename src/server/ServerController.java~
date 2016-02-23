package server;

import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * <h1>ServerController</h1>
 * ServerController is the action handler for GUI interactions and 
 * starts a server instance for network communication 
 * @author Oscar Hall 
 *
 */

public class ServerController implements ActionListener {
	Server model;
	ServerGUI view;

	//invoked when a button is pressed
	public void actionPerformed(java.awt.event.ActionEvent e){
		
		//System.out.println("Controller: acting on Model");
		switch (e.getActionCommand()){
		case "Start Game": startServer();
			view.switchButtonState(); //Inactivate Start Game and activate End Game
			break;
		case "End Game": view.toTerminal("Dummy shutting down game\n");
			view.switchButtonState(); //Inactivate End Game and activate Start Game
			break;
		case "Choose Map": view.toTerminal("Choose Map button pushed\n");
			break;
		}
		
	} //actionPerformed()

	public void addModel(Server m){
		System.out.println("Controller: adding model");
		this.model = m;
	} //addModel()

	public void addView(ServerGUI v){
		System.out.println("Controller: adding view");
		this.view = v;
	} //addView()

	/**
	 * Create a server instance and set this ServerController's  added view to observe.
	 */
	public void startServer()
	{
		Thread t = new Thread(new Runnable(){
			public void run(){
				try {
					Server s = new Server(3, view.getIpAddress(), view.getPort());
					s.addObserver(view);
					addModel(s);
			} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		view.toTerminal("Starting server on " + view.getIpAddress() + ":" + view.getPort() + "\n");
	}//startServer()
}
