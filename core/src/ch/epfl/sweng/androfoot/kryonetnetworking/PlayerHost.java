package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.util.ArrayList;

import ch.epfl.sweng.androfoot.interfaces.HostObservable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class PlayerHost implements HostObservable {

	public static Server server;
	private boolean gameStarted;
	private static Connection mConnection;
	
	private ArrayList<HostObserver> mHostObserver = new ArrayList<HostObserver>();

	public PlayerHost() {
	}

	static public void sendHostData(HostData data) {
		server.sendToTCP(mConnection.getID(), data);
	}

	public void listenToClient() throws IOException {

		DiscoverServerTest();
		
		server = new Server();
		server.start();
		server.bind(NetworkUtils.TCP_PORT);

		addHostObserver(PlayerTouchTracker.getInstance());

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		NetworkUtils.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof InputData) {
					updateGameState((InputData) object);
				} else if (object instanceof Integer && !gameStarted) {
					mConnection = connection;
					gameStarted = true;
					updateGameStart();
					server.sendToTCP(mConnection.getID(),0);
				}
			}
		
		public void connected(Connection c) {
			System.out.println("Host: Connection established");
		}
		
		});
	}
	
	public void DiscoverServerTest()
	{
		final Server broadcastServer = new Server();
		try
		{
			broadcastServer.bind(NetworkUtils.UDP_PORT);
			broadcastServer.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	

	protected void updateGameState(InputData data) {
		updateHostObserver(data);
	}

	private void updateGameStart() {
		for (HostObserver obs : mHostObserver) {
			obs.gameHostStart();
		}
	}

	@Override
	public void addHostObserver(HostObserver obs) {
		mHostObserver.add(obs);
	}

	@Override
	public boolean removeHostObserver(HostObserver obs) {
		return mHostObserver.remove(obs);
	}

	@Override
	public void updateHostObserver(InputData data) {
		if (gameStarted) {
			for (HostObserver obs : mHostObserver) {
				obs.updateClientData(data);
			}
		}
	}

	public static void sendHostData(InputData inputData) {
		server.sendToTCP(mConnection.getID(), inputData);
	}
}
