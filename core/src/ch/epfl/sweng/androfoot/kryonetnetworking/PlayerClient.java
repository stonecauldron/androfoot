package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import ch.epfl.sweng.androfoot.interfaces.ClientObservable;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PlayerClient implements ClientObservable {

	public static Client client;
	private static boolean gameStarted;

	private ArrayList<ClientObserver> mClientObserver = new ArrayList<ClientObserver>();

	public PlayerClient() {
	}

	public static void sendClientData(InputData data) {
		if (gameStarted) {
			client.sendTCP(data);
		}
	}

	public void listenToServer() throws IOException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		client = new Client();
		// TODO Get Lan discovery to WORK T_T
		String address = discoverHostAddress();
		client.start();

		try {
			client.connect(5000, address, NetworkUtils.TCP_PORT);
		} catch (IOException e) {
			System.exit(1);
		}

		addClientObserver(PlayerTouchTracker.getInstance());

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		NetworkUtils.register(client);

		client.sendTCP(0);
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof HostData) {
					updateGameState((HostData) object);
				} else if (object instanceof InputData) {
					updateGameState((InputData) object);
				} else if (object instanceof Integer) {
					System.out
							.println("Client: Connection established confirmed");
					gameStarted = true;
					updateGameStart();
				}
			}

		});
	}

	private void updateGameStart() {
		for (ClientObserver obs : mClientObserver) {
			obs.gameClientStart();
		}
	}

	private void updateGameState(InputData data) {
		updateClientObserver(data);
	}

	protected void updateGameState(HostData response) {
		updateClientObserver(response);
	}

	public String discoverHostAddress() {
		String address;
		InetAddress host = client.discoverHost(NetworkUtils.UDP_PORT, 3000);
		try {
			address = host.getHostAddress();
		} catch (NullPointerException exp) {
			return "localhost";
		}

		return address;
	}

	@Override
	public void addClientObserver(ClientObserver obs) {
		mClientObserver.add(obs);
	}

	@Override
	public boolean removeClientObserver(ClientObserver obs) {
		return mClientObserver.remove(obs);
	}

	@Override
	public void updateClientObserver(HostData data) {
		if (gameStarted) {
			for (ClientObserver obs : mClientObserver) {
				obs.updateHostData(data);
			}
		}
	}

	@Override
	public void updateClientObserver(InputData data) {
		if (gameStarted) {
			for (ClientObserver obs : mClientObserver) {
				obs.updateHostTouchData(data);
			}
		}
	}
}