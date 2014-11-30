package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;

public interface ClientObservable {

	public void addClientObserver(ClientObserver obs);
	boolean removeClientObserver(ClientObserver obs);
	public void updateClientObserver(HostData data);
	
}
