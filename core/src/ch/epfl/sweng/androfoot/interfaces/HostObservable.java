package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.ShakeData;

public interface HostObservable {

	public void addHostObserver(HostObserver obs);
	boolean removeHostObserver(HostObserver obs);
	public void updateHostObserver(InputData data);
	public void updateHostObserver(ShakeData data);
	public void updateHostObserver(GameInfo data);
	
}
