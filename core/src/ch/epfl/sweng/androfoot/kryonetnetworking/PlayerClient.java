package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.interfaces.ClientObservable;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class PlayerClient implements ClientObservable {

	public static Client client;
	private static boolean gameStarted;

	private ArrayList<ClientObserver> mClientObserver = new ArrayList<ClientObserver>();
	private String address;

	public PlayerClient() {
	}

	public static void sendClientData(InputData data) {
		if (gameStarted) {
			client.sendTCP(data);
		}
	}

	public void listenToServer() throws IOException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		DiscoverServerTest();
		
		client = new Client();
		client.start();

		try {
			client.connect(5000, address, NetworkUtils.TCP_PORT);
		} catch (IOException e) {
			GuiManager.getInstance().executeCommand(GuiCommand.goToMainMenu);
		}

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
		System.out.println("REACHED IN PC");
		updateClientObserver(data);
	}

	protected void updateGameState(HostData response) {
		updateClientObserver(response);
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

	public void DiscoverServerTest() {
		
		Client broadcastClient = new Client();

		InetAddress host = broadcastClient.discoverHost(NetworkUtils.UDP_PORT,
				3000);
		try {
			this.address = host.getHostAddress();
		} catch (NullPointerException exp) {
			System.out.println("aSD");
		}
	}

	@Override
	public void updateClientObserver(GameInfo data) {
		// TODO Auto-generated method stub
		
	}
}
