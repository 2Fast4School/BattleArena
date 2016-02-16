package ArenaFighter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
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
		/*Main loop. Listens to each individual client in a separate thread.
		  If anything is received, it sends it to all other clients*/
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
					}catch(IOException e){}
				}
			}
		}
		public void sendToClient(byte[] byt, int id){
			//Send to all clients, except the one that sent the message. It already knows.
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


