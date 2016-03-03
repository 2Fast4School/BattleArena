package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Message;

public class ByteRepresenter {
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
	public Message bytesToExternObject(byte[] byteRepresentation){
		try{
			ByteArrayInputStream bIn=new ByteArrayInputStream(byteRepresentation);
			ObjectInputStream oIn=new ObjectInputStream(new BufferedInputStream(bIn));
			Message message=new Message();
			message.readExternal(oIn);
			oIn.close();
			bIn.close();
			return message;
		}catch(IOException e){e.printStackTrace();}
		catch(ClassNotFoundException f){}
		return null;
	}
}
