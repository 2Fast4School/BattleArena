package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import model.Entity;
import model.GameState;
import model.Player;

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
		int code;
		int id;
		int xVal;
		int yVal;
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
						for(Entity e : state.getList()){
							if (e.getID()==id){
								e.setX(xVal);
								e.setY(yVal);
							}
						}
					}
				}catch(IOException e){e.printStackTrace();}
			}
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1){
		String message;
		byte[] toSend;
		if(arg1 instanceof Player){
			Player player=(Player)arg1;
			if(out!=null){
				try{	// 1 = move code. For some reason a string wouldn't work out.
					toSend=new String(1+","+player.getID()+","+player.getX()+","+player.getY()+",Filler").getBytes();
					out.write(toSend, 0, toSend.length);
					out.flush();
				}catch(IOException e){e.printStackTrace();}
			}
		}
	}				
}