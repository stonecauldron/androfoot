package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;

public interface HostObserver {

	void updateClientData(InputData data);
	void gameHostStart();
	
}
