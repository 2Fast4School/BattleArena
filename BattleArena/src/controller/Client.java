package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import map.Map;
import model.Enemy;
import model.GameState;
import model.Player;
/**
* <h1>Client</h1>
* Client is the class which is responsible for sending packets including player information to the server and listen for incoming packets from the Server.
* @author  William Bj�rklund / Victor Dahlberg
* @version 2.0
* @since   2016-02-26
*/
public class Client implements Runnable, Observer{
	private int srvport;
	private InetAddress srvip;
	private GameState state;
	private DatagramSocket socket;
	private Map map;
	
	/** The Constructor opens a DatagramSocket on an empty port.
	 * 
	 * @param srvport The port which the server listens on.
	 * @param srvip The server's ip in form of a String.
	 * @param state The GameState which should be updated when a packet is received.
	 */
	public Client(int srvport, String srvip, GameState state, Map map){
		this.srvport = srvport;
		this.state = state;
		this.map = map;
		
		try {
			this.srvip = InetAddress.getByName(srvip);
			socket = new DatagramSocket();
		} 
		
		catch (UnknownHostException e1) {} 
		catch (SocketException e) {}
	}

	/**
	* Each Client runs in it's own thread and listen for messages from
	* the server, which it will react to depending on the contents.
	* Messages are expected to come as byte[], and when translated into
	* a String they should follow the format:<p>
	* "OP-CODE, ID, [variable], [variable]....,Filler"<p>
	* The individual parts of the message can then be extracted, and a
	* proper reaction to the message can be done. An OP-CODE corresponds
	* to what sort of reaction is desired, and the ID which player sent
	* the message. These are always read. Further information might be
	* sent depending on which OP-CODE it is.*/
	@Override
	public void run() {
		String sdata;
		int id = 0, newx = 0, newy = 0, rot = 0;
		boolean attacking;
		byte[] data = new byte[1024];
		DatagramPacket pkt;
		
		
		while(true){
			pkt = new DatagramPacket(data, data.length);
			
			try {
				socket.receive(pkt);
			} catch (IOException e) {
				System.out.println("error");
				e.printStackTrace();
			}
			
			sdata = new String(pkt.getData());
			String[] d = sdata.split(",");
			
			
			int code = Integer.parseInt(d[0].trim());
			id = Integer.parseInt(d[1]);
			newx = Integer.parseInt(d[2]); newy = Integer.parseInt(d[3]);  rot = Integer.parseInt(d[4]); attacking = Boolean.parseBoolean(d[5]);
			
			for(Enemy n : state.getTheEnemies()){
				if(id == n.getID()){
					
					n.setX(newx); n.setY(newy); n.setRotVar(rot);
					
					//Funger inte just nu..
					if(attacking){
						n.doAttack();
					}
				}
			}
		}
	}
	
	
	/**
	 * This method sends an "init-packet" to the server and waits for a response.
	 * The Init-packet has OP-CODE: 0. Server knows this OP-code is a request and responds to the client which sent the packet with an id.
	 * This is the id given to client.
	 * This method will wait 10s for an init packet from the server.
	 */
	public void requestConnection(){
		//OPCODE 0 is initpacket. server responds with your id.
		String sdata = Integer.toString(0)+",FILL";
		byte[] data = sdata.getBytes();
		DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
		
		try {
			socket.send(pkt);
			byte []buf = new byte[1024];
			pkt = new DatagramPacket(buf, buf.length);
			
			socket.setSoTimeout(10000);
			socket.receive(pkt);
			sdata = new String(pkt.getData());
			String d[] = sdata.split(",");
			state.setup(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1]), map);
			
		} catch (IOException e) {
			System.out.println("couldnt connect");
		}
		
	}
	
		
	
	/**
	* Client is notified when there is information that needs
	* to be sent to other clients. Depending on the parameters
	* sent with this notification, a correct OP-CODE will be
	* chosen for the message.
	* @param arg0 : GameState 
	* @param arg1 : Player/null
	*/
	@Override
	public void update(Observable arg0, Object arg1){

		if(arg1 instanceof Player){
			Player player=(Player)arg1;
			Enemy enemy = state.gotHit();
			//Default-sträng som alltid ska med.
			String sdata = ","+state.getID()+","+player.getX()+","+player.getY()+","+player.getRotVar()+","+player.getWeapon().isAttacking();
			byte[] data;
			
			
			//hp-change OPCODE:2
			if(enemy != null) {
				sdata = 2+sdata;
				sdata += ","+enemy.getID();
			} else {
				//Regular / move OPCODE:1
				sdata = 1+sdata;
			}
			
			data = sdata.getBytes();
			
			DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
			try {
				//System.out.println("sending");
				socket.send(pkt);
			} catch (IOException e) {}
		}
	}
}				