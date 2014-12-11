package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;

public interface ClientObserver {

	void updateHostData(HostData data);
	void updateHostTouchData(InputData data);
	void updateHostGameInfoData(GameInfo data);
	void gameClientStart();
}
