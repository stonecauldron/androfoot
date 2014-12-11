package ch.epfl.sweng.androfoot.kryonetnetworking;


public class HostServer {

	static PlayerHost pHostServer;

	public static PlayerHost getHostServer() {
		if (pHostServer == null) {
			PlayerHost ph = new PlayerHost();
			pHostServer = ph;
		}
		return pHostServer;
	}
}
