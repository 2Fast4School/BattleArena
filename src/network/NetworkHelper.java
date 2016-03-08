package network;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * <h1>NetworkHelper</h1>
 * Get information about network interface addresses.
 * @author Oscar Hall
 * @version 1.0 2016-03-04
 */
public class NetworkHelper {
	/**
	 * Method to find the local network interfaces
	 * @return ArrayList<String> with interfaces found on the computer
	 */
	public static ArrayList<String> getInterfaces() {
		Enumeration<NetworkInterface> e = null;
		ArrayList<String> interfaces = new ArrayList<String>();

		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		while (e.hasMoreElements()) {
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration<InetAddress> ee = n.getInetAddresses();
			while (ee.hasMoreElements()) {
				InetAddress i = (InetAddress) ee.nextElement();
				//Add to String list
				interfaces.add(i.getHostAddress());
			}
		}
	return interfaces;
	}
}
