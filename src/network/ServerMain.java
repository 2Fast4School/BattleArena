package network;

/**
 * <h1>ServerMain</h1>
 * Start a server instance for players to connect to. 
 * The class uses the Singleton design pattern.
 * 
 * @author Oscar Hall
 * @version 1.0
 */
public class ServerMain {
	private ServerController sc;
	private ServerGUI sg;
	private static ServerMain instance = null;
	
	
	/**
	 * The hidden constructor
	 */
	private ServerMain(){ startServerUI();	}
	
	
	/**
	 * Singleton intanciation of this class
	 * @return instance of ServerMain
	 */
	public static ServerMain getInstance()
	{
		if(instance == null)
			instance = new ServerMain();
		
		return instance;
	}
	
	
	/**
	 * For some reason you can't have code in the constructor, so we use this method to create a ServerGUI, a ServerController
	 * and in the darkness bind them.
	 */
	private void startServerUI()
	{
		
		sg = new ServerGUI();
		sc = new ServerController();
		
		sc.addView(sg);
		sg.addController(sc);
	
	}

}
