package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.util.ArrayList;

import ch.epfl.sweng.androfoot.gui.GuiCommand;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.interfaces.HostObservable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class PlayerHost implements HostObservable {

	public static Server server;
	private boolean gameStarted;
	private static Connection mConnection;

	private ArrayList<HostObserver> mHostObserver = new ArrayList<HostObserver>();

	public void listenToClient() throws IOException {

		DiscoverServerTest();

		server = new Server();
		server.start();
		server.bind(NetworkUtils.TCP_PORT);

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
					server.sendToTCP(mConnection.getID(), 0);
					
				}
			}

			public void disconnected(Connection c) {
				System.out.println("Connection lost, server waiting for reconnection");
				mConnection.close();
				// This let another client reconnect
				gameStarted = false;
				mHostObserver.clear();
			}

			public void connected(Connection c) {
				System.out.println("Host: Server established");
			}

		});
	}

	/**
	 * @param data
	 *            the speed of the ball and position to send to the client
	 */
	static public void sendHostData(HostData data) {
		if (mConnection.isConnected()) {
			server.sendToTCP(mConnection.getID(), data);
		}
	}

	/**
	 * @param inputData
	 *            the speed of paddle to send to the other player
	 */
	public static void sendHostData(InputData inputData) {
		if (mConnection.isConnected()) {
			server.sendToTCP(mConnection.getID(), inputData);
		}
	}

	public void DiscoverServerTest() {
		final Server broadcastServer = new Server();
		try {
			broadcastServer.bind(0, NetworkUtils.UDP_PORT);
			broadcastServer.start();
		} catch (IOException e) {
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

	@Override
	public void updateHostObserver(ShakeData data) {
		if (gameStarted) {
			for (HostObserver obs : mHostObserver) {
				obs.updateClientShakeData(data);
			}
		}
	}

	@Override
	public void updateHostObserver(GameInfo data) {
		if (gameStarted) {
			for (HostObserver obs : mHostObserver) {
				obs.updateClientGameInfoData(data);
			}
		}
	}
}
