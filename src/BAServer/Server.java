package BAServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import map.Map;
import model.Message;

/**
* <h1>Server</h1>
* Server is just that, a server for the game application.
* This Server doesn't hold any actual game information 
* of it's own, instead it listens for DatagramPackets, and forwards
* them. In other words it works by UDP <p>
* Every packet is handled by it's own thread in the private class PacketHandler.
* The server stores information about all the connected clients in an ArrayList of ClientInfo.
* ClientInfo is also a private class.
* @author  William Bjorklund / Victor Dahlberg
* @version 2.0
* @since   2016-02-26
*/

public class Server extends Observable implements Runnable{

	private DatagramSocket revSkt;
	private DatagramPacket packet;
	private byte[] receive;
	private int idToGiveClient=0;
	private int maxPlayers = 4;
	private List<ClientInfo> clients;
	private Map map = null;
	private String mapName="logicMap.png";;
	private String type="grass";
	private boolean safelyClosed=false;
	
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
	}
	
	public void setMap(Map map)
	{
		this.map=map;
	}
	
	public void setMapName(String mapName, String type){
		this.mapName=mapName;
		this.type=type;
	}
	public String getMapName(){return mapName;}
	
	public Map getMap()
	{
		return map;
	}
	
	public void setMaxPlayers(int nrOfPlayers)
	{
		this.maxPlayers = nrOfPlayers;
	}

	/**
	 * Listens for packets and every time a packet is received it starts a new Thread of a new object of Packethandler.
	 * The only thing run does is listens for incoming packets and the distribute the work to Packethandler which actually does something with the received packet.
	 */
	public void run(){
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
	* MiniServer is a private class to Server, and it's threaded.
	* It continually listens to the Client it is associated with,
	* and will forward any messages sent by that Client to all other
	* clients.
	* 
	* Each time a packet is received, a packethandler is created and started.
	* The packet handler then reads the OP-CODE of the packet and the ID of the pakcket, i.e who it came from.
	* It then depending on OP-CODE and ID responds or forward the packet.
	* 
	* Create only one PacketHandler per packet.
	* 
	* @author  William Bj�rklund / Victor Dahlberg
	* @version 1.0
	* @since   2016-02-26
	*/

	private class PacketHandler implements Runnable{
		DatagramPacket pkt = null;
		byte[] bReceive = null;
		DatagramSocket skt = null;
		String d[];
		int code, id, nrDead;
		Message receiveMessage;
		boolean alive;
		/**
		 * 
		 * @param pkt The DatagramPacket to be handled.
		 */
		public PacketHandler(DatagramPacket pkt, byte[] bReceive){
			this.pkt = pkt;
			this.bReceive=bReceive;
				
			try {
				ByteArrayInputStream bIn=new ByteArrayInputStream(bReceive);
				ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
				receiveMessage=new Message();
				receiveMessage.readExternal(oIn);
				
				code = receiveMessage.getCode();
				id=receiveMessage.getID();
				alive=receiveMessage.getAlive();
				//Create a new datagramsocket on an open port.
				skt = new DatagramSocket();
			}catch(IOException e){e.printStackTrace();}
			catch(ClassNotFoundException f){}
		}
		
		/**
		 * Depending on the OP-CODE. Responds or forwards the packet.
		 */
		public void run(){	
			System.out.println(mapName);
			if(code == 0){
				idToGiveClient += 1;
				Message sendMessage=new Message(idToGiveClient, -1, -1, -1, false);
				sendMessage.setMaxNrPlayers(maxPlayers);;
				sendMessage.setMapName(mapName);
				sendMessage.setMapType(type);
				System.out.println(type);
				System.out.println(mapName);
				try{
					ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
					ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
					oOut.flush();
					sendMessage.writeExternal(oOut);
					oOut.flush();
					byte[] buf=bOut.toByteArray();
					
					clients.add(new ClientInfo(pkt.getAddress(), pkt.getPort(), idToGiveClient));
					
					pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
					
					skt.send(pkt);
					
				}catch(IOException e){e.printStackTrace();}
				
			}
			else if(code==99){
				Message sendMessage=new Message();
				sendMessage.setCode(code);
				boolean tostart = true;
				for(ClientInfo c : clients){
					
					if(c.getID()==id){
						
						if(receiveMessage.getReady()){
							c.setReady(true);
							System.out.println("READY: "+id);
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
					ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
					ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
					oOut.flush();
					sendMessage.writeExternal(oOut);
					oOut.flush();
					byte[] buf=bOut.toByteArray();
					
					pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
					skt.send(pkt);
					
				}catch(IOException e){}
				
			}
			else{
				int nrDead=0;
				for(ClientInfo c : clients){
					if(c.getID()==id){
						c.setAlive(alive);
					}
					if(!c.getAlive()){
						nrDead++;
					}
				}
				if(maxPlayers-nrDead==1){
					Message gameOverMessage=new Message();
					gameOverMessage.setCode(4);
					try{
						ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
						ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
						oOut.flush();
						gameOverMessage.writeExternal(oOut);
						oOut.flush();
						byte[] buf=bOut.toByteArray();
						
						clients.add(new ClientInfo(pkt.getAddress(), pkt.getPort(), idToGiveClient));
						for(ClientInfo c : clients){
							pkt = new DatagramPacket(buf, buf.length, c.getIP(), c.getPort());
							skt.send(pkt);
						}
						setChanged();
						notifyObservers(true);
						stop();

					}catch(IOException e){e.printStackTrace();}
				}
				else if(code == 0){
					idToGiveClient += 1;
					Message sendMessage=new Message(idToGiveClient, -1, -1, -1, false);
					sendMessage.setMaxNrPlayers(maxPlayers);
					sendMessage.setMapName(mapName);
					sendMessage.setMapType(type);
					try{
						ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
						ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
						oOut.flush();
						sendMessage.writeExternal(oOut);
						oOut.flush();
						byte[] buf=bOut.toByteArray();
						
						clients.add(new ClientInfo(pkt.getAddress(), pkt.getPort(), idToGiveClient));
						
						pkt = new DatagramPacket(buf, buf.length, pkt.getAddress(), pkt.getPort());
						
						skt.send(pkt);

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
						ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
						ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
						oOut.flush();
						sendMessage.writeExternal(oOut);
						oOut.flush();
						byte[] buf=bOut.toByteArray();
						
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
	
	public void resetServer(){
		clients.clear();
		idToGiveClient=0;
	}
	
	public synchronized void start(){
		if(running){return;}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the main game thread.
	 */
	public synchronized void stop(){
		if(running)
			running = false;
			safelyClosed=true;
			revSkt.close();
	}
	
	/**
	 * A very simple class to store IP, Port and ID of a specific client.
	 * Every client will most likely listen on different ports.
	 * @author William Björklund / Victor Dahlberg
	 * @version 1.0
	 * @since 2016-02-26
	 *
	 */
	private class ClientInfo{
		private InetAddress ip;
		private int port;
		private int id;
		private boolean ready;
		private boolean alive;
		
		public ClientInfo(InetAddress ip, int port, int id){
			this.ip = ip;
			this.port = port;
			this.id = id;
			ready=false;
			alive=true;
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
		public boolean getReady(){return ready;}
		public void setReady(boolean state){ready=state;}
		public boolean getAlive(){return alive;}
		public void setAlive(boolean state){alive=state;}
	}
}


