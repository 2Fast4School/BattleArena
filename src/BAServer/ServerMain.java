package BAServer;

/**
 * <h1>ServerMain</h1>
 * Start class for a dedicated server. Creates a controller, starts a ServerGUI and connect them together.
 * 
 * @author Oscar Hall
 *
 */
public class ServerMain {
	private static ServerController sc;
	private static ServerGUI sg;

	
	public static void main(String[] args) {
		
		sg = new ServerGUI();
		sc = new ServerController();
		
		sc.addView(sg);
		sg.addController(sc);
	
	}
	
	
	

}
