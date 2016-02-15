package ArenaFighter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class Client implements Runnable, Observer{
	private int port;
	private String ip;
	private DataOutputStream out;
	private DataInputStream in;
	private GameState state;
	
	public Client(int port, String ip, GameState state){
		
		this.port=port;
		this.ip=ip;
		this.state=state;
		try{
			Socket socket=new Socket(InetAddress.getByAddress(ip, null), port);
			out=new DataOutputStream(socket.getOutputStream());
			in=new DataInputStream(socket.getInputStream());
		}catch(UnknownHostException f){}
		catch(IOException e){e.printStackTrace();}
	}

	@Override
	public void run() {
		String message[];
		String code;
		int id;
		int xVal;
		int yVal;
		byte[] receive=new byte[1024];
		while(true){
			if(in!=null){
				try{
					in.read(receive, 0, receive.length);
					message=new String(receive).split(",");
					code=message[0];
					id=Integer.parseInt(message[1]);
					xVal=Integer.parseInt(message[2]);
					yVal=Integer.parseInt(message[3]);
					if(code=="move"){
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
	public void update(Observable arg0, Object arg1) {
		String message;
		byte[] toSend;
		if(arg1 instanceof Player){
			Player player=(Player)arg1;
			if(out!=null){
				message="move,"+player.getID()+","+player.getX()+","+player.getY()+",Filler";
				toSend=message.getBytes();
				try{
					out.write(toSend, 0, toSend.length);
				}catch(IOException e){e.printStackTrace();}
			}
		}
	}				
}