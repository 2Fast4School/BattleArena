package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.GameState;
/**
* <h1>Client</h1>
* Client is the network portion of each client-side application.
* It has access to a socket towards the Server, and the related
* DataOutput, and DataInput streams. Client implements the Observer 
* interface, and it's an observer to GameState from which it receives
* messages if there is information that needs to be sent to other clients.
* @author  William Bjï¿½rklund
* @version 1.0
* @since   2016-02-17
*/
public class Client implements Runnable, Observer{
	private int port;
	private String ip;
	private GameState state;
	private int id;
	private DatagramSocket clientSocket;
	
	public Client(int port, String ip, GameState state){
		this.port=port;
		this.ip=ip;
		this.state=state;
		id=-1;
		try{
			clientSocket=new DatagramSocket(port);
		}catch(SocketException e){}
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
		DatagramSocket sendSocket;
		while(true){
			if(id==-1){	// Skicka Initialize request en gång.
				try{
					sendSocket=new DatagramSocket();
					byte[] sendMessage=new String(0+","+InetAddress.getLocalHost().getHostAddress()+","+port).getBytes();
					InetAddress iAddress=InetAddress.getByName(ip);
					DatagramPacket packet=new DatagramPacket(sendMessage, sendMessage.length, iAddress, 7020);
					sendSocket.send(packet);
					sendSocket.close();
				}catch(UnknownHostException e){e.printStackTrace();}
				catch(IOException e){e.printStackTrace();}
				id--;
			}
			else{	// Annars lyssna efter medelanden, som hanteras i separata trådar.
				byte[] receiveMessage=new byte[1024];
				DatagramPacket receivePacket=new DatagramPacket(receiveMessage, receiveMessage.length);
				if(receivePacket!=null){
					try{
						clientSocket.receive(receivePacket);
					}catch(IOException e){}
					new Thread(new PacketHandler(receiveMessage)).start();
				}
			}
		}
	}
	
	private class PacketHandler extends Thread{
		private byte[] bMessage; 
		public PacketHandler(byte[] bMessage){
			this.bMessage=bMessage;
		}
		public void run(){
			String[] sMessage=new String(bMessage).trim().split(",");
			int code=Integer.parseInt(sMessage[0]);
			int identity=Integer.parseInt(sMessage[1]);
			if(code==0){
				id=identity;
				state.setID(identity);
			}
			else if(code==1){
				System.out.println("move");
			}
			else if(code==2){
				System.out.println("HP");
			}
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
		String message;
		byte[] toSend;
		/*if(arg1 instanceof Player){
			Player player=(Player)arg1;
			if(out!=null){
				try{	
					Enemy enemy=state.gotHit();
					if(enemy!=null){// 2 = HP-change code
						int enID=state.gotHit().getID();
						message=2+","+id+","+enID+",Filler";
						toSend=message.getBytes();
						//enemy=null;
					}
					else{// 1 = move code.
						message ="1,"+state.getID()+","+player.getX()+","+player.getY()+","+player.getRotVar()+","+player.getWeapon().isAttacking()+",Filler";
						toSend = message.getBytes();
					}
					out.write(toSend, 0, toSend.length);
					out.flush();
				}catch(IOException e){e.printStackTrace();}
			}
		}*/
	}				
}