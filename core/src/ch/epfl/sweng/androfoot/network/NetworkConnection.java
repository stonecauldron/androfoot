/**
 * 
 */
package ch.epfl.sweng.androfoot.network;

import java.util.ArrayList;

/**
 * @author Sidney Barthe
 * 
 * Represents a network connection that could be platform specific (in our case Bluetooth),
 * and so has to be abstracted here in the core project and implemented concretely
 * for other platforms in their respectful project (android, desktop...) 
 */
public interface NetworkConnection {
	public void host();
	public ArrayList<String> getHosts();
	public void connect(int hostNumber);
	public int getState();
	public void send(String message); // Message objects will have to be converted to strings
	public String read(); // ...and vice versa
	
}
