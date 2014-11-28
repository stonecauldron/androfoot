package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PlayerClient extends Thread {

	private Client client;

	public PlayerClient() {

	}

	public void listenToServer() throws IOException {
		Client client = new Client();
		client.start();
		String address = "localhost";
		client.connect(5000, address, NetworkUtils.TCP_PORT,
				NetworkUtils.UDP_PORT);
		
	    // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
		NetworkUtils.register(client);

		client.sendTCP("asd");
		
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof GameData) {
					GameData response = (GameData) object;
					updateGfx(response);
				} else if (object instanceof String) {
					System.out.println(object);
				}
			}
		});
	}
	
	protected void updateGfx(GameData response) {
		//TODO use the gamedata to display the game
		Gdx.app.log("NETWORK TEST", "x : " + response.getmPlayerOneX() );
	}

	public InetAddress discoverHost() {
		return client.discoverHost(NetworkUtils.UDP_PORT, 10000);
	}
}
