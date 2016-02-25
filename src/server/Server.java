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
* @author  William Bjï¿½rklund
* @version 1.0
* @since   2016-02-17
*/

public class Server extends Observable implements Runnable{

	private DatagramSocket serverSocket;
	private Scanner scanner;
	private DatagramPacket packet;
	private byte[] receive;
	private String[] message;
	private int idToGiveClient=0;
	private ArrayList<ClientInfo> clients;
	
	public Server(String ip, int port) throws IOException{
		String serverIP=ip;
		int serverPort=port;
		serverSocket=new DatagramSocket(7020);
		clients=new ArrayList<ClientInfo>();
	}
	/*Lyssnar alltid efter medelanden. Skapar ny tråd för att hantera mottaget medelande
	 *Detta medelanden kan vara Request to join server(Code==0)*/
	public void run(){
		while(true){
			receive=new byte[1024];
			packet=new DatagramPacket(receive, receive.length);
			try{
				serverSocket.receive(packet);
			}catch(IOException e){}
			new Thread(new packetHandler(receive)).start();
		}
	}

	/**
	* <h1>MiniServer</h1>
	* MiniServer is a private class to Server, and it's threaded.
	* It continually listens to the Client it is associated with,
	* and will forward any messages sent by that Client to all other
	* clients.
	* @author  William Bjï¿½rklund
	* @version 1.0
	* @since   2016-02-17
	*/

	private class packetHandler extends Thread{
		private byte[] bMessage;
		
		public packetHandler(byte[] bMessage){
			this.bMessage=bMessage;
		}
		
		/*Om code==0, skicka initialize medelande som ger ID till avsändaren.
		 *Annars, vidarebefodra medelandet till all -utom- avsändaren.*/
		public void run(){
			String[] sMessage;
			byte[] sendMessage;
			int code,port,id;
			String ip;
			InetAddress inetAddress=null;
			DatagramPacket sendPacket=null;
			DatagramSocket sendSocket=null;
			
			try{
				sendSocket=new DatagramSocket();
			}catch(SocketException e){e.printStackTrace();}
			
			sMessage=new String(bMessage).trim().split(",");
			code=Integer.parseInt(sMessage[0]);
			
			if(code==0){	// Initial connect. Store the IP and Port so we can itterate over the map to send to all later.
				ip=sMessage[1];	// Also send a return message to acknowledge the connection..and return an ID.
				port=Integer.parseInt(sMessage[2]);
				clients.add(new ClientInfo(idToGiveClient, port, ip));
				try{
					inetAddress = InetAddress.getByName(ip);
				}catch(UnknownHostException e){}
				sendMessage=new String(0+","+idToGiveClient+",Filler").getBytes();
				sendPacket = new DatagramPacket(sendMessage, sendMessage.length, inetAddress, port);
			}
			else{
				id=Integer.parseInt(sMessage[1]);
				for(ClientInfo client : clients){
					if(id!=client.getID()){
						try{
							sendPacket=new DatagramPacket(bMessage, bMessage.length, InetAddress.getByName(client.getIP()), client.getPort());
						}catch(IOException e){e.printStackTrace();}
					}
				}
			}
			try{
				sendSocket.send(sendPacket);
			}catch(IOException e){e.printStackTrace();}
		}
	}
	private class ClientInfo{
		private int id;
		private int port;
		private String ip;
		public ClientInfo(int id, int port, String ip){
			this.id=id;
			this.port=port;
			this.ip=ip;
		}
		public int getID(){return id;}
		public int getPort(){return port;}
		public String getIP(){return ip;}
	}
}


