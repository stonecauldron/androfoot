package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class PlayerHost {

	private Server server;

	public PlayerHost() {
	}

	public void listenToClient() throws IOException {

		server = new Server();
		server.start();
		server.bind(NetworkUtils.TCP_PORT, NetworkUtils.UDP_PORT);

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		NetworkUtils.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof TouchData) {
					TouchData touchCoordinates = (TouchData) object;
					System.out.println("" + touchCoordinates);
					// TODO Take care of user received coordinates
				} else if (object instanceof String) {
					server.sendToTCP(1, "ASD");
				}
			}
		});
	}
}
