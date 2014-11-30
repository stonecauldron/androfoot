package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;

public interface ClientObserver {

	void updateHostData(HostData data);
	void gameClientStart();
}
