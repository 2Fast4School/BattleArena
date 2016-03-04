package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import controller.ByteRepresenter;
import model.Message;

/**
* <h1>Server</h1>
* Server is just that, a server for the game application.
* Server passively forwards packets received on the chosen port.<p>
*  <b><u>Known information:</u></b><p> 
*  <b>maxPlayers:int</b> The maximum number of players in the game<p>
*  <b>idToGiveClient:int</b> The id a joining client should receive<p>
*  <b>mapName:String</b> The path to the map selected to be played<p>
*  <b>type:String</b> The name of the texture chosen for the background<p>
*  <b>map:Map</b> The gamemap<p>
*  <b>clients:List(ClientInfo)</b> The clients communicating with the server
* @author  William Bjorklund / Victor Dahlberg
* @version 1.0 2016-02-26
*/

public class Server extends Observable implements Runnable{

	private DatagramSocket revSkt;
	private DatagramPacket packet;
	private byte[] receive;
	private int idToGiveClient=0;
	private int maxPlayers = 0;
	private List<ClientInfo> clients;
	private String mapName="DotaMap.png";;
	private String type="grass";
	private boolean safelyClosed=false;
	private ByteRepresenter byteRepresenter;
	
	private boolean running;
	private Thread thread;
	
	/**
	 * Creates a DatagramSocket bount to a specific port. The Server listens for packets on this port.
	 * @param port The port which the server should listen to.
	 * @throws IOException Socket could not be created on that port.
	 */
	public Server(int port) throws IOException{
		revSkt=new DatagramSocket(port);
		clients=Collections.synchronizedList(new ArrayList<ClientInfo>());
		byteRepresenter = new ByteRepresenter();
	}
	
	/**
	 * Sets the map name
	 * @param mapName
	 */
	public void setMapName(String mapName){
		this.mapName=mapName;
	}
	/**
	 *  Sets the map type
	 * @param type
	 */
	public void setMapType(String type){
		this.type=type;
	}
	/**
	 * Gets the map name
	 * @return the map name
	 */
	public String getMapName(){return mapName;}
	
	/**
	 * Sets number of players
	 * @param nrOfPlayers
	 */
	public void setMaxPlayers(int nrOfPlayers)
	{
		this.maxPlayers = nrOfPlayers;
	}

	/**
	 * Listens for packets and every time a packet is received it starts a new Thread of a new object of Packethandler.
	 * The only thing run does is listens for incoming packets and the distribute the work to Packethandler which actually does something with the received packet.
	 */
	public void run(){
		setChanged();
		notifyObservers(false);
		while(running){
			receive=new byte[1024];
			packet=new DatagramPacket(receive, receive.length);
			try {
				revSkt.receive(packet);
				new Thread(new PacketHandler(packet, receive)).start();
			} catch (IOException e) {
				if(!safelyClosed){
					e.printStackTrace();	
				}
			}
		}
	}

	/**
	* <h1>PacketHandler</h1>
	*  Handles a received packet by reading required information and then forwarding a message to connected clients
	*  as appropriate.<p>
	*  <b><u>Known Information:</u></b><p>
	*   <b>pkt:DatagramPacket</b> The received packet<p>
	*   <b>code:int</b> The code given by the received packet<p>
	*   <b>id</b> The id of the client that sent the packet<p>
	*   <b>receiveMessage:Message</b> The instance of Message sent in the packet<p>
	*   <b>alive:boolean</b> If the client is alive in the game<p>
	* @author  William Bjorklund / Victor Dahlberg
	* @version 1.0
	* @since   2016-02-26
	*/

	private class PacketHandler implements Runnable{
		DatagramPacket pkt = null;
		DatagramSocket skt = null;
		int code, id;
		Message receiveMessage;
		boolean alive;
		/**
		 * Initiates required fields and objects.
		 * @param pkt The packet to be handled.<p>
		 * bReceive:byte[] The byte field belonging to pkt
		 */
		public PacketHandler(DatagramPacket pkt, byte[] bReceive){
			this.pkt = pkt;
				
			try {
				receiveMessage=byteRepresenter.bytesToExternObject(bReceive);
				
				code = receiveMessage.getCode();
				id=receiveMessage.getID();
				alive=receiveMessage.getAlive();
				//Create a new datagramsocket on an open port.
				skt = new DatagramSocket();
			}catch(IOException e){e.printStackTrace();}
		}
		
		/**
		 * Selectes a response, or just forwards the packet, depending on it's contents.
		 */
		public void run(){	
			if(code == 0){
				idToGiveClient += 1;
				Message sendMessage=new Message(idToGiveClient, -1, -1, -1, 0);
				sendMessage.setMaxNrPlayers(maxPlayers);;
				sendMessage.setMapName(mapName);
				sendMessage.setMapType(type);
				
				try{
					byte[] buf=byteRepresenter.externByteRepresentation(sendMessage);
					
					clients.add(new ClientInfo(pkt.getAddress(), pkt.getPort(), idToGiveClient,
							receiveMessage.getPlayerName()));
					
					pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
					
					skt.send(pkt);
					
				}catch(IOException e){e.printStackTrace();}
			}
			else if(code==99){
				Message sendMessage=new Message();
				sendMessage.setCode(code);
				sendMessage.setNrPlayers(idToGiveClient);
				boolean tostart = true;
				for(ClientInfo c : clients){
					if(c.getID()==id){
						if(receiveMessage.getReady()){
							c.setReady(true);
						} else {
							c.setReady(false);
						}
					}
					if(!c.getReady()){
						tostart = false;
					}
				}
				
				if(clients.size() == maxPlayers){
					sendMessage.setToStart(tostart);
				} else {
					sendMessage.setToStart(false);
				}

				try{
					byte[] buf=byteRepresenter.externByteRepresentation(sendMessage);
					
					pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
					skt.send(pkt);
					
				}catch(IOException e){}
				
			}
			else{
				int nrDead=0;
				String winnerName=null;
				for(ClientInfo c : clients){
					if(c.getID()==id){
						c.setAlive(alive);
					}
					if(!c.getAlive()){
						nrDead++;
					}
					else{
						winnerName=c.getName();
					}
				}
				if(maxPlayers-nrDead==1){
					Message gameOverMessage=new Message();
					gameOverMessage.setCode(4);
					gameOverMessage.setPlayerName(winnerName);
					try{
						byte[] buf=byteRepresenter.externByteRepresentation(gameOverMessage);
						
						for(ClientInfo c : clients){
							pkt = new DatagramPacket(buf, buf.length, c.getIP(), c.getPort());
							skt.send(pkt);
						}
						setChanged();
						notifyObservers(true);
						stop();

					}catch(IOException e){e.printStackTrace();}
				}
				else if(code==3){
					Message sendMessage=new Message();
					sendMessage.setCode(code);sendMessage.setReady(false);
					int nrReady=0;
					for(ClientInfo c : clients){
						if(c.getID()==id && receiveMessage.getReady()){
							c.setReady(true);
						}
						if(c.getReady()){
							nrReady+=1;
						}
					}
					if(nrReady==maxPlayers){
						sendMessage.setReady(true);
					}
					try{
						byte[] buf=byteRepresenter.externByteRepresentation(sendMessage);
						
						for(ClientInfo c : clients){
							DatagramPacket sendPacket=new DatagramPacket(buf, buf.length, c.getIP(), c.getPort());
							skt.send(sendPacket);
						}
					}catch(IOException e){}
				}
				else {
					int idToSkip = id;
					
					for(ClientInfo c : clients){
						
						if(c.getID() != idToSkip){
							try {
								pkt = new DatagramPacket(pkt.getData(), pkt.getLength(), c.getIP(), c.getPort());
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
	}
	
	/**
	 * Resets the server's known clients to zero to let the server be reused.
	 */
	public void resetServer(){
		clients.clear();
		idToGiveClient=0;
	}
	/**
	 * Start the server's thread.
	 */
	public synchronized void start(){
		if(running){return;}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the server's thread.
	 */
	public synchronized void stop(){
		if(running)
			running = false;
			safelyClosed=true;
			revSkt.close();
	}
	
	/**
	 * ClientInfo
	 * Holds selected information regarding a single client communicating with the server.<p>
	 * <b><u>Known information:</u></b><p> 
	 * <b>ip:InetAddress</b> - the InetAddress of the client<p>
	 * <b>port:int</b> - the client's port<p>
	 * <b>id:int</b> - the client's id<p>
	 * <b>ready:boolean</b> - if the client is ready to start a game<p>
	 * <b>alive:boolean</b> - if the client has died in the game<p>
	 * <b>name:String</b> - the client's name
	 * 
	 * @author William Bjorklund / Victor Dahlberg
	 * @version 1.0
	 * @since 2016-03-03
	 *
	 */
	private class ClientInfo{
		private InetAddress ip;
		private int port;
		private int id;
		private boolean ready;
		private boolean alive;
		private String name;
		
		/**
		 * Instanciates the ClientInfo
		 * @param ip IP address
		 * @param port Port address
		 * @param id Player ID
		 * @param name Player name
		 */
		public ClientInfo(InetAddress ip, int port, int id, String name){
			this.ip = ip;
			this.port = port;
			this.id = id;
			this.name=name;
			ready=false;
			alive=true;
		}
		
		/**
		 * Gets the IP address (InetAddress)
		 * @return InetAddress
		 */
		public InetAddress getIP(){
			return ip;
		}
		/**
		 * Gets the port
		 * @return port 
		 */
		public int getPort(){
			return port;
		}
		/**
		 * Get player ID
		 * @return
		 */
		public int getID(){
			return id;
		}
		/**
		 * Returns whether the client is ready to start the game.
		 * @return ready:boolean
		 */
		public boolean getReady(){return ready;}
		
		/**
		 * Sets whether the client is ready to start the game.
		 * @param state
		 */
		public void setReady(boolean state){ready=state;}
		/**
		 * Checks whether the client is alive in the game.
		 * @return alive:boolean
		 */
		public boolean getAlive(){return alive;}
		/**
		 * Sets whether the client is alive in the game.
		 * @param state
		 */
		public void setAlive(boolean state){alive=state;}
		/**
		 * Returns the client's name.
		 * @return name:String
		 */
		public String getName(){return name;}
	}
}


