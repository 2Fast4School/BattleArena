package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

/**
* <h1>Server</h1>
* Server is just that, a server for the game application.
* This Server doesn't hold any actual game information 
* of it's own, instead it listens to the clients, and forwards
* the messages they send to the other clients.<p>
* This is done by the use of multiple threaded instances of
* the private class MiniServer, which are stored in an ArrayList
* so that they can be iterated over.
* @author  William Bj�rklund
* @version 1.0
* @since   2016-02-17
*/

public class Server extends Observable{

	private int numberOfPlayers;
	private ServerSocket serverSocket;
	private Scanner scanner;
	private ArrayList<MiniServer> servers;
	
	public Server(int numberOfPlayers, String ip, int port) throws IOException{
		this.numberOfPlayers=numberOfPlayers;
		String serverIP=ip;
		int serverPort=port;
		
		servers=new ArrayList<MiniServer>();

		/*Wait for all the clients and connect them. They are handled by a separate thread object each(MiniServer)*/
		serverSocket=new ServerSocket(serverPort);
		for(int n=0;n<numberOfPlayers;n++){
			Socket clientSocket=serverSocket.accept();
			MiniServer mini=new MiniServer(clientSocket,n);
			servers.add(mini);
			mini.start();
		}
		serverSocket.close();
	}

	/**
	* <h1>MiniServer</h1>
	* MiniServer is a private class to Server, and it's threaded.
	* It continually listens to the Client it is associated with,
	* and will forward any messages sent by that Client to all other
	* clients.
	* @author  William Bj�rklund
	* @version 1.0
	* @since   2016-02-17
	*/
	private class MiniServer extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		int id;
		public MiniServer(Socket socket, int id){
			this.socket=socket;
			this.id=id;
			try{
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(IOException e){}
		}

		public void run(){
			int code;
			int id;
			int posX;
			int posY;
			byte[] receive=new byte[1024];
			String[] message;
			while(true){
				if(in!=null){
					try{
						in.read(receive, 0, receive.length);
						message=new String(receive).trim().split(",");
						id=Integer.parseInt(message[1]);
						sendToClient(receive, id);
						setChanged();
						notifyObservers();
					}catch(IOException e){}
				}
			}
		}
		public void sendToClient(byte[] byt, int id){
			//Send to all clients, except the one that sent the message. It already knows.
			// This is why the id is extracted from the message.
			for(MiniServer server : servers){
					if(id!=server.getID()){
						try{
							server.getOut().write(byt, 0, byt.length);
							server.getOut().flush();
						}catch(IOException e){}
					}
			}
		}
		
		public DataOutputStream getOut(){return out;}
		public int getID(){return id;}
	}
}


