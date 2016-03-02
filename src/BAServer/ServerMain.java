package BAServer;

/**
 * <h1>ServerMain</h1>
 * Start class for a dedicated server. Creates a controller, starts a ServerGUI and connect them together.
 * 
 * @author Oscar Hall
 *
 */
public class ServerMain {
	private ServerController sc;
	private ServerGUI sg;
	private static ServerMain instance = null;
	
	private ServerMain(){	}
	
	public static ServerMain getInstance()
	{
		if(instance == null)
			instance = new ServerMain();
		
		return instance;
	}
	
	public void startServerUI()
	{
		
		sg = new ServerGUI();
		sc = new ServerController();
		
		sc.addView(sg);
		sg.addController(sc);
	
	}

}
