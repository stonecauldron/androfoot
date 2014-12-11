package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.io.IOException;
import java.util.ArrayList;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.HostObservable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class PlayerHost implements HostObservable {

	public static Server server;
	public boolean gameStarted;
	private static Connection mConnection;

	private ArrayList<HostObserver> mHostObserver = new ArrayList<HostObserver>();
	private boolean serverStarted;
	private Server broadcastServer;

	public void listenToClient() throws IOException {

		if (!serverStarted) {
			serverStarted = true;
			DiscoverServerTest();

			server = new Server();
			server.start();
			server.bind(NetworkUtils.TCP_PORT);
		}

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
				} else if (object instanceof ShakeData) {
					updateGameState((ShakeData) object);
				}
			}

			public void disconnected(Connection c) {
				System.out
						.println("Connection lost, server waiting for reconnection");
				// This let another client reconnect
				mConnection.close();
				gameStarted = false;
				updateGameStart();
				PhysicsWorld.getPhysicsWorld().setHostMode(false);
			}

			public void connected(Connection c) {
				System.out.println("Host: Server established");
			}
		});
	}

	public void closeServers() {
		serverStarted = false;
		broadcastServer.stop();
		broadcastServer.close();
		server.stop();
		server.close();
		mHostObserver.clear();
		gameStarted = false;
		serverStarted = false;
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
		broadcastServer = new Server();
		try {
			broadcastServer.bind(0, NetworkUtils.UDP_PORT);
			broadcastServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateGameState(ShakeData shakeData) {
		updateHostObserver(shakeData);
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
