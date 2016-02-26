package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

public class Server extends Observable implements Runnable{

	private DatagramSocket revSkt;
	private DatagramPacket packet;
	private byte[] receive;
	private int idToGiveClient=0;
	private ArrayList<ClientInfo> clients;
	private Thread t;
	
	public Server(int port) throws IOException{
		revSkt=new DatagramSocket(port);
		clients=new ArrayList<ClientInfo>();
	}
	/*Lyssnar alltid efter medelanden. Skapar ny tr�d f�r att hantera mottaget medelande
	 *Detta medelanden kan vara Request to join server(Code==0)*/
	public void run(){
		System.out.println("runnig");
		while(true){
			receive=new byte[1024];
			packet=new DatagramPacket(receive, receive.length);
			try {
				revSkt.receive(packet);
				new Thread(new PacketHandler(packet)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

	private class PacketHandler implements Runnable{
		DatagramPacket pkt = null;
		DatagramSocket skt = null;
		String d[];
		int code, id;
		
		public PacketHandler(DatagramPacket pkt){
			this.pkt = pkt;
			
			String data = new String(pkt.getData());
			d = data.split(",");
			code = Integer.parseInt(d[0].trim());
			try {
				skt = new DatagramSocket();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run(){
			if(code == 0){
				
				idToGiveClient += 1;
				String temp = Integer.toString(idToGiveClient)+",FILL";
				byte[] buf = temp.getBytes();
				clients.add(new ClientInfo(pkt.getAddress(), pkt.getPort(), idToGiveClient));
				pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
				
				try {
					skt.send(pkt);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				
				int idToSkip = Integer.parseInt(d[1]);
				System.out.println(idToSkip);
				for(ClientInfo c : clients){
					
					if(c.getID() != idToSkip){
						
						try {
							skt.send(pkt);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			if(skt != null){
				skt.close();
			}
		}
	}
	
	private class ClientInfo{
		private InetAddress ip;
		private int port;
		private int id;

		
		public ClientInfo(InetAddress ip, int port, int id){
			this.ip = ip;
			this.port = port;
			this.id = id;
		}
		
		public InetAddress getIP(){
			return ip;
		}
		
		public int getPort(){
			return port;
		}
		
		public int getID(){
			return id;
		}
	}
}


