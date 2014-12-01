package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;

public interface HostObservable {

	public void addHostObserver(HostObserver obs);
	boolean removeHostObserver(HostObserver obs);
	public void updateHostObserver(InputData data);
	
}
