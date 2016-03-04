package BAServer;


import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <h1>ServerController</h1>
 * ServerController is the action handler for GUI interactions and 
 * starts a server instance for network communication 
 * @author Oscar Hall 
 * @version 2016-03-03
 */
public class ServerController implements ItemListener, ActionListener{
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

	/**
	 * set map tile type
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getItemSelectable() instanceof Choice){
			Choice choice=(Choice)arg0.getSource();
			model.setMapType(view.getMapType());
			switch(view.getMapType()){
			case "lava":
				choice.setForeground(Color.RED);
				break;
			case "grass":
				choice.setForeground(Color.GREEN);
				break;
			case "desert":
				choice.setForeground(Color.ORANGE);
				break;
			default:
				break;	
			}
		}
	}

	/**
	 * Add data connection
	 * @param m Server instance
	 */
	public void addModel(Server m){
		this.model = m;
	} //addModel()

	/**
	 * Add a GUI to control
	 * @param v ServerGUI 
	 */
	public void addView(ServerGUI v){
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
			e.printStackTrace();
		}
		
		view.toTerminal("Starting server on " + view.getIpAddress() + ":" + view.getPort() + "\n");
	}//startServer()
	
	/**
	 * Creates a file explorer to find maps which is of filetype .png
	 */
	public void chooseMap()
	{
		String className=this.getClass().getName().replace('.', '/');
		String classJar=this.getClass().getResource('/'+className+".class").toString();
		File toChoose;
		view.toTerminal(classJar);
		if(classJar.startsWith("rsrc:")){
			toChoose=new File(System.getProperty("user.dir")+"/maps");
		}
		else{
			toChoose=new File(System.getProperty("user.dir")+"/res/maps");
		}
		JFileChooser chooser = new JFileChooser(toChoose);
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
					e.printStackTrace();
				} 
		    String mapName=chooser.getSelectedFile().getName();
		    if(mapName==null){
		    	mapName="logicMap.png";
		    }
		    model.setMapName(chooser.getSelectedFile().getName());
	    }
	}
}
