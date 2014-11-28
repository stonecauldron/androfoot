package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.net.InetAddress;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PlayerClient {

	private Client client;

	public PlayerClient() throws IOException {
		Client client = new Client();
		client.start();
		client.connect(5000, "192.168.0.4", NetworkUtils.TCP_PORT,
				NetworkUtils.UDP_PORT);

		TouchData request = new TouchData(10, 10);
		client.sendTCP(request);
	}

	public void listenToServer() {

		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof GameData) {
					GameData response = (GameData) object;
					sendNewData(response);
				}
			}
		});
	}

	protected void sendNewData(GameData response) {
		//TODO use the gamedata to display the game
		Gdx.app.log("NETWORK TEST", "x : " + response.getmPlayerOneX() );
	}

	public InetAddress DiscoverHost() {

		return client.discoverHost(54777, 5000);
	}
}
