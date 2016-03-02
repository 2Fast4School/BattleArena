package BAServer;


import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import map.Map;
import map.MapGenerator;

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
			view.switchButtonState(); //Inactivate Start Game and activate 
			break;
		case "Reset Server": 
			view.toTerminal("Server shut down\n");
			view.switchButtonState(); //Inactivate End Game and activate Start Game
			model.stop();
			model.resetServer();
			break;
		case "Choose Map": chooseMap();
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
		try {
			Server s = new Server(view.getPort());
			s.addObserver(view);
			addModel(s);
			s.setMaxPlayers(view.getNrOfPlayers());
			s.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		view.toTerminal("Starting server on " + view.getIpAddress() + ":" + view.getPort() + "\n");
	}//startServer()
	
	
	/**
	 * <h1>chooseMap</h1>
	 * Creates a file explorer to find maps which is of filetype .png
	 */
	public void chooseMap()
	{
		JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")+"/res"));
		BufferedImage logicMap = null;
		
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        ".PNG logic maps", "png");
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       try {
			logicMap = ImageIO.read(chooser.getSelectedFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    String mapName=chooser.getSelectedFile().getName();
	    if(mapName==null){
	    	mapName="logicMap.png";
	    }
	    model.setMapName(chooser.getSelectedFile().getName(), view.getMapType());
	}
}
