package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;

public class HostServer {

	static PlayerHost pHostServer;
	
	public static PlayerHost getHostServer() {
		if (pHostServer == null) {
			PlayerHost ph = new PlayerHost();
			try {
				ph.listenToClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pHostServer = ph;
		}
			return pHostServer;
	}
}
