package ArenaFighter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
	private ArrayList<ClientInfo> clients;
	private int numberOfPlayers;
	public Server(int numberOfPlayers){
		this.numberOfPlayers=numberOfPlayers;
		try{
			ServerSocket serverSocket =new ServerSocket();
			for(int n=0;n<numberOfPlayers;n++){
				waitForClient(serverSocket, n);
			}
			serverSocket.close();
			
		}catch(IOException e){e.printStackTrace();}
		
	}
	private void waitForClient(ServerSocket serverSocket, int n){
		try{
			Socket socket = serverSocket.accept();
			clients.add(new ClientInfo(new DataOutputStream(socket.getOutputStream()), new DataInputStream(socket.getInputStream()),n));
		}catch(IOException e){e.printStackTrace();}	
	}
	

	private void sendToClients(byte[] data, int id){
		try{
				for(ClientInfo client : clients){
					if(id!=client.getID()){
						client.getOut().write(data, 0, data.length);
					}
				}
		}catch(IOException e){e.printStackTrace();}
	}
	

	@Override
	public void run() {
		int id;
		DataInputStream in;
		byte[] receive=new byte[1024];
		while(true){
			try{
				for(ClientInfo client : clients){
					in=client.getIn();
					if(in!=null){
						in.read(receive, 0, receive.length);
						if(receive.length>0){
							id=Integer.parseInt(new String(receive).split(",")[1]); // ID
							sendToClients(receive, id);
						}
					}
				}
			}catch(IOException e){e.printStackTrace();}
		}
	}
	
	private class ClientInfo{
		private int id;
		private DataOutputStream out;
		private DataInputStream in;
		public ClientInfo(DataOutputStream out, DataInputStream in, int id){
			this.out=out;
			this.in=in;
			this.id=id;
		}
		public DataOutputStream getOut(){return out;}
		public DataInputStream getIn(){return in;}
		public int getID(){return id;}
	}
}