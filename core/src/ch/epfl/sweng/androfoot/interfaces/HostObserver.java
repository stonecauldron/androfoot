package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.ShakeData;

public interface HostObserver {

	void updateClientData(InputData data);
	void updateClientShakeData(ShakeData data);
	void updateClientGameInfoData(GameInfo data);
	void gameHostStart();
}
