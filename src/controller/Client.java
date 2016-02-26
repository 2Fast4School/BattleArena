package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.Enemy;
import model.GameState;
import model.Player;
/**
* <h1>Client</h1>
* Client is the network portion of each client-side application.
* It has access to a socket towards the Server, and the related
* DataOutput, and DataInput streams. Client implements the Observer 
* interface, and it's an observer to GameState from which it receives
* messages if there is information that needs to be sent to other clients.
* @author  William Bj�rklund
* @version 1.0
* @since   2016-02-17
*/
public class Client implements Runnable, Observer{
	private int srvport;
	private InetAddress srvip;
	private GameState state;
	private int id;
	private DatagramSocket socket;
	
	public Client(int srvport, String srvip, GameState state){
		this.srvport = srvport;
		this.state = state;
		id = -1;
		
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
			switch(code){
				case 1:
					//UNIMPLEMENTED
					break;
				case 2:	
					// UNIMPLEMENTED
					break;
					
				default:
					break;
			}
			
			for(Enemy n : state.getTheEnemies()){
				if(id == n.getID() || n.getID() == -1){
					
					if(n.getID() == -1){
						n.setID(id);
					}
					
					n.setX(newx); n.setY(newy); n.setRotVar(rot);
					
					//Funger inte just nu..
					if(attacking){
						n.doAttack();
					}
				}
			}
		}
	}
	
	
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
			state.setID(Integer.parseInt(d[0].trim()));
			
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