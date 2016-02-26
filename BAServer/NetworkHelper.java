

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <h1>NetworkHelper</h1>
 * Get information about network and network interfaces.
 * @author Oscar Hall
 *
 */
public class NetworkHelper {
	
	public ArrayList<String> getInterfaces() {
		Enumeration<NetworkInterface> e = null;
		ArrayList<String> interfaces = new ArrayList<String>();

		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
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
