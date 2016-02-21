package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.Enemy;
import model.Entity;
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
	private int port;
	private String ip;
	private DataOutputStream out;
	private DataInputStream in;
	private GameState state;
	private int id;
	
	public Client(int port, String ip, GameState state, int id){
		this.id=id;
		this.port=port;
		this.ip=ip;
		this.state=state;
		state.setID(id);
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
		if(out==null){
			try{
				Socket socket=new Socket(ip, port);
				out=new DataOutputStream(socket.getOutputStream());
				in=new DataInputStream(socket.getInputStream());
			}catch(UnknownHostException f){}
			catch(IOException e){e.printStackTrace();}
		}
		
		String message[];
		int code,id,xVal,yVal,rot;
		boolean at;
		
		byte[] receive=new byte[1024];
		while(true){
			if(in!=null){
				try{
					in.read(receive, 0, receive.length);
					message=new String(receive).trim().split(",");
					code=Integer.parseInt(message[0].trim());
					id=Integer.parseInt(message[1]);
					if(code==1){	// 1 = move code. For some reason a string wouldn't work out.
						xVal=Integer.parseInt(message[2]);
						yVal=Integer.parseInt(message[3]);
						rot=Integer.parseInt(message[4]);
						at=Boolean.parseBoolean(message[5]);
								
						for(Enemy e : state.getTheEnemies()){
									
							if(e.getID() == id || e.getID() == -1){
								
								e.setX(xVal);
								e.setY(yVal);
								e.setRotVar(rot);
								if(at){
									e.doAttack();
								}
								
								if(e.getID() == -1){
									e.setID(id);
								}
							}
								
						}
				}
					/*if(code==2){	// 2= attack code. (Also updates for move)
						xVal=Integer.parseInt(message[2]);
						yVal=Integer.parseInt(message[3]);
						rot=Integer.parseInt(message[4]);
						for(Entity e : state.getList()){
							if (e instanceof Player){
								e.setX(xVal);
								e.setY(yVal);
								e.setRotVar(rot);
								//e.setAttacking(true);
							}
						}
					}
					if(code==3){
						int attackedID;
						int numberOfIDs=Integer.parseInt(message[2]);
						for(int n=3;n<(numberOfIDs+3);n++){
							attackedID=Integer.parseInt(message[n]);
							for(Entity e : state.getList()){
								if (e.getID()==attackedID){
									e.setHP(e.getHP()-20);
								}
							}
						}
					}*/
				}catch(IOException e){e.printStackTrace();}
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
		if(arg1 instanceof Player){
			Player player=(Player)arg1;
			if(out!=null){
				try{	// 1 = move code. For some reason a string wouldn't work out.
					/*if(player.getHitByList().size()>0 && !player.hasSentHP()){
						player.setHasSentHP(true);
						message=3+","+player.getID()+","+player.getHitByList().size()+",";
						for(int n=0;n<player.getHitByList().size(); n++){
							message+=player.getHitByList().get(n);
							message+=",";
						}
						message+="Filler";
						toSend=message.getBytes();
					}
					else if(player.getAttacking()){
						toSend=new String(2+","+player.getID()+","+player.getX()+","+player.getY()+
						","+player.getRotVar()+",Filler").getBytes();
					}
					else{
						toSend=new String(1+","+player.getID()+","+player.getX()+","+player.getY()+
						","+player.getRotVar()+",Filler").getBytes();
					}*/
					
					message ="1,"+state.getID()+","+player.getX()+","+player.getY()+","+player.getRotVar()+","+player.getWeapon().isAttacking()+",Filler";
					toSend = message.getBytes();
	
					out.write(toSend, 0, toSend.length);
					out.flush();
				}catch(IOException e){e.printStackTrace();}
			}
		}
	}				
}