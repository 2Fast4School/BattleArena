package controller;

import java.awt.image.BufferedImage;
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

import javax.imageio.ImageIO;

import arenaFighter.Main;
import map.Map;
import map.MapGenerator;
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
	private ByteRepresenter byteRepresenter;
	
	private boolean running;
	private Thread thread;
	
	/** The Constructor opens a DatagramSocket on an empty port.
	 * 
	 * @param srvport The port which the server listens on.
	 * @param srvip The server's ip in form of a String.
	 * @param state The GameState which should be updated when a packet is received.
	 */
	public Client(int srvport, String srvip, GameState state){
		this.srvport = srvport;
		this.state = state;
		ready=false;
		byteRepresenter=new ByteRepresenter();
		
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
		
		
		while(running){
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
				
				int code=receiveMessage.getCode();
				
				if(code == 99){ //LOBBY-CODE
					if(receiveMessage.toStart()){
						state.startGame();
					}
			
				} else {
					
					int enemyID=receiveMessage.getEnemeyID();
					id=receiveMessage.getID();
					newx=receiveMessage.getXPos();
					newy=receiveMessage.getYPos();
					rot=receiveMessage.getRotVar();
					attacking=receiveMessage.getAttacking();
					
					int playerHP=receiveMessage.getPlayerHP();
					
					if(code==4){
						state.setName(receiveMessage.getPlayerName());
						state.setGameOver(true);
						stop();
					}
					
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
		message.setPlayerName(state.getName());
		
		try{
			byte[] data=byteRepresenter.externByteRepresentation(message);
			DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
			socket.send(pkt);
			
			byte []buf = new byte[1024];
			pkt = new DatagramPacket(buf, buf.length);
			
			socket.setSoTimeout(10000);
			socket.receive(pkt);
			
			Message receiveMessage;
			receiveMessage=byteRepresenter.bytesToExternObject(buf);

			BufferedImage logicMap=ImageIO.read(Main.class.getResource("/maps/"+receiveMessage.getMapName()));
			map=MapGenerator.generateMap(logicMap, receiveMessage.getMapType(), 16);

			state.setup(receiveMessage.getID(), receiveMessage.getMaxNrPlayers(), map);
		}catch(IOException e){System.out.println("couldnt connect");e.printStackTrace();}	
		
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
		
		if(arg0 instanceof GameState){
			
			if(!state.isAlive()){
				byte[] data;
				Message message = new Message(); //LOBBYCODE
				message.setCode(99); 
				message.setReady(state.isReady());
				message.setID(state.getID());
				
				try{	
					data=byteRepresenter.externByteRepresentation(message);
					DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
					socket.send(pkt);	
				}catch(IOException e){}
			}
			
		}

		if(arg1 instanceof Player){
			Player player=(Player)arg1;
			Enemy enemy = state.gotHit();

			byte[] data;
			Message message=new Message(state.getID(),player.getX(),player.getY(),player.getRotVar(),
					player.getWeapon().isAttacking());

			message.setPlayerHP(player.getHP());
			message.setAlive(player.isAlive());
			
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
				data=byteRepresenter.externByteRepresentation(message);
				DatagramPacket pkt = new DatagramPacket(data, data.length, srvip, srvport);
				socket.send(pkt);	
			}catch(IOException e){}
		}
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
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] externByteRepresentation(Object externializable){
		try{
			ByteArrayOutputStream bOut=new ByteArrayOutputStream(5000);
			ObjectOutputStream oOut=new ObjectOutputStream(new BufferedOutputStream(bOut));
			oOut.flush();
			if(externializable instanceof Message){
				Message message=(Message)externializable;
				message.writeExternal(oOut);
			}
			oOut.flush();
			oOut.close();
			bOut.close();
			return bOut.toByteArray();
		}catch(IOException e){e.printStackTrace();return null;}
	}
	public void bytesToExternObject(byte[] byteRepresentation, Message message){
		try{
			ByteArrayInputStream bIn=new ByteArrayInputStream(byteRepresentation);
			ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
			message.readExternal(oIn);
			oIn.close();
			bIn.close();
		}catch(IOException e){e.printStackTrace();}
		catch(ClassNotFoundException f){}
	}
}				