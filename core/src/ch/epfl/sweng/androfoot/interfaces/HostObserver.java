package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.ClientData;

public interface HostObserver {

	void updateClientData(ClientData data);
	void gameHostStart();
	
}
