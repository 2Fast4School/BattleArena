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

* Client is responsible for sending packets to the server,
* aswell as listening for incoming packets from the Server.<p>
* <b><u>Known Information:</u></b><p>
* <b>srvport:int</b> The port the server is listening on<p>
* <b>srvip:InetAddress</b> The server's adddress<p>
* <b>state:GameState</b> The game's current state, with all data regarding the current game<p>
* <b>map:Map</b> The selected map<p>
* @author  William Bjorklund / Victor Dahlberg
* @version 1.0
* @since   2016-03-03
*/
public class Client implements Runnable, Observer{
	private int srvport;
	private InetAddress srvip;
	private GameState state;
	private DatagramSocket socket;
	private Map map;
	private ByteRepresenter byteRepresenter;
	
	private boolean running;
	private Thread thread;
	
	/** The constructor initiates the necessary variables and objects of Client.<p>
	 * @param srvport The port which the server listens on.
	 * @param srvip The server's ip in form of a String.
	 * @param state The GameState which should be updated when a packet is received.
	 */
	public Client(int srvport, String srvip, GameState state){
		this.srvport = srvport;
		this.state = state;
		byteRepresenter=new ByteRepresenter();
		
		try {
			this.srvip = InetAddress.getByName(srvip);
			socket = new DatagramSocket();
		} 
		
		catch (UnknownHostException e1) {} 
		catch (SocketException e) {}
	}

	/**
	 * The main loop that listens for packets from the server, and calls for changes in Gamestate
	 * depending on the contents of an incoming packet.
	 */
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
	 * The method by which Client makes the first communication with the Server,
	 * preparing both for further, structured communication.
	 */
	public void requestConnection(){
		//OPCODE 0 is initpacket.
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
	
	/**
	* Client is notified when there has been a change in the local
	* GameState which needs to be sent to the server.<p>
	* The method also sends the change to the server
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
	
	/**
	 * Starts the Client's thread.
	 */
	public synchronized void start(){
		if(running){return;}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the Client's thread.
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
}				