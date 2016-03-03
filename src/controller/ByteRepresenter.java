package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Message;

/**
 *  <h1>ByteRepresenter</h1>
 *  ByteRepresenter serves an interpreter to translate objects that implement
 *  the externializable interface into a byte array and vice versa.<p>
 *  <b>Known Information</b><p>
 *  None
 * @author William
 * @version 1.0
 * @since 2016-03-03
 */
public class ByteRepresenter {
	/**
	 * Retreives the byte representation of what's received from the writeExternal method
	 * of an object implementing the externializable interface, and returns it.<p>
	 * Currently only implemented for Class Message
	 * @param externializable
	 * @return byte[] bOut 
	 */
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
	/**
	 * Recreates an object implementing the externializable interface using
	 * the supplied byte array.<p>
	 * Currently only implemented for class Message
	 * @param byteRepresentation
	 * @return Message message
	 */
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
