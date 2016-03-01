package controller;

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
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import arenaFighter.Main;
import map.Map;
import model.Enemy;
import model.GameState;
import model.Message;
import model.Player;
/**
* <h1>Client</h1>

* Client is the class which is responsible for sending packets including player information to the server and listen for incoming packets from the Server.
* @author  William Bjorklund / Victor Dahlberg
* @version 2.0
* @since   2016-02-26
*/
public class Client implements Runnable, Observer{
	private int srvport;
	private InetAddress srvip;
	private GameState state;
	private DatagramSocket socket;
	private Map map;
	private boolean ready;
	
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
		ready=false;
		
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
				
			try{
				ByteArrayInputStream bIn=new ByteArrayInputStream(data);
				ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
				Message receiveMessage=new Message();
				receiveMessage.readExternal(oIn);
				
				int code=receiveMessage.getCode();int enemyID=receiveMessage.getEnemeyID();
				id=receiveMessage.getID();newx=receiveMessage.getXPos();newy=receiveMessage.getYPos();
				rot=receiveMessage.getRotVar();attacking=receiveMessage.getAttacking();
				int playerHP=receiveMessage.getPlayerHP();
				
				for(Enemy n : state.getTheEnemies()){
					if(id == n.getID()){
						n.setX(newx); n.setY(newy); n.setRotVar(rot);
						
						if(playerHP!=-1){
							n.setHP(playerHP);
						}
						//Funger inte just nu..
						if(attacking && !n.getHasAttacked()){
							n.setHasAttacked(true);
							n.doAttack();
						}
						else if(!attacking){
							n.setHasAttacked(false);
						}
					}
					else if(enemyID==n.getID()){
						n.setHP(receiveMessage.getEnemyHP());
					}
				}
				if(enemyID==state.getID()){
					state.returnPlayer().setHP(receiveMessage.getEnemyHP());
				}
			}catch(IOException e){}
			catch(ClassNotFoundException f){}
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
		Message message=new Message();
		message.setCode(0);
		
		try{
			ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
			ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
			oOut.flush();
			message.writeExternal(oOut);
			oOut.flush();
			byte[] data=bOut.toByteArray();

			DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
			
			socket.send(pkt);
			byte []buf = new byte[1024];
			pkt = new DatagramPacket(buf, buf.length);
			
			socket.setSoTimeout(10000);
			socket.receive(pkt);
			
			ByteArrayInputStream bIn=new ByteArrayInputStream(buf);
			ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
			Message receiveMessage=new Message();
			receiveMessage.readExternal(oIn);
			
			state.setup(receiveMessage.getID(), receiveMessage.getMaxNrPlayers(), map);
		}catch(IOException e){System.out.println("couldnt connect");}	
		catch(ClassNotFoundException f){}
	}
	
	public void startLobbyProtocol(){
		new Thread(new LobbyProtocol()).start();
	}
	
	private class LobbyProtocol implements Runnable{
		public LobbyProtocol(){
			
		}
		@Override
		public void run() {
			while(true){
				DatagramSocket sendSocket;
				try{
					sendSocket=new DatagramSocket();
					
					Message message=new Message(state.getID(), -1, -1, -1, false);
					message.setCode(3);message.setReady(ready);
					ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
					ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
					oOut.flush();
					message.writeExternal(oOut);
					oOut.flush();
					byte[] bSend=bOut.toByteArray();
					
					DatagramPacket sendPacket=new DatagramPacket(bSend, bSend.length, srvip, srvport);
					sendSocket.send(sendPacket);
				}catch(IOException e){}
				
				byte[] bReceive=new byte[1024];
				DatagramPacket receivePacket=new DatagramPacket(bReceive, bReceive.length);
				try{
					socket.receive(receivePacket);
				}catch(IOException e){}
				
				try{
					ByteArrayInputStream bIn=new ByteArrayInputStream(bReceive);
					ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
					Message receiveMessage=new Message();
					receiveMessage.readExternal(oIn);
					boolean startGame=receiveMessage.getReady();
					//System.out.println(""+startGame);
					if(startGame){
						Main.startGame();
						Main.runClient();
						break;
					}
				}catch(IOException e){}
				catch(ClassNotFoundException e){}
			}			
		}
	}	
	
	public void setReady(boolean state){ready=state;}
	
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
			byte[] data;
			Message message=new Message(state.getID(),player.getX(),player.getY(),player.getRotVar(),
					player.getWeapon().isAttacking());

			message.setPlayerHP(player.getHP());

			//hp-change OPCODE:2
			if(enemy != null) {
				message.setCode(2);
				message.setEnemyID(enemy.getID());
				message.setEnemyHP(enemy.getHP());
			} else {
				//Regular / move OPCODE:1
				message.setCode(1);
			}
			
			try{
				ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
				ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
				oOut.flush();
				message.writeExternal(oOut);
				oOut.flush();
				data=bOut.toByteArray();
				
				DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
				socket.send(pkt);
			}catch(IOException e){}
		}
	}
}				