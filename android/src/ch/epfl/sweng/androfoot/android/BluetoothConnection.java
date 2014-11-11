package ch.epfl.sweng.androfoot.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import org.json.JSONObject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

// TODO : change this class so it implements a NetworkConnection (from the network package)
// in order to be able to use it in the core project

/**
 * @author Congelateur This class provides an interface for establishing a
 *         bluetooth connection between two android devices as well as providing
 *         methods to send and receive messages over the connection . Requires
 *         permissions BLUETOOTH and BLUETOOTH_ADMIN
 */
public class BluetoothConnection {
	private BluetoothAdapter mAdapter = null;
	private BluetoothSocket mBluetoothSocket = null;
	// buffers
	private String mMessageBuffer = null;
	// locks
	private Object writeLock = new Object();
	private Object bufferLock = new Object();

	// Unique UUID
	private UUID mUuid = UUID
			.fromString("431a597a-c91f-4a97-8137-99f1d89b7036");

	// Threads to handle blocking methods
	private AcceptingThread mAcceptingThread = null;
	private ConnectingThread mConnectingThread = null;
	private CommunicationThread mCommunicationThread = null;
	// Connection states constants
	public static final int STATE_EXISTING = 0;
	public static final int STATE_WAITING_FOR_CONNECTION = 1;
	public static final int STATE_ATTEMPTING_CONNECTION = 2;
	public static final int STATE_CONNECTED = 3;
	private int mCurrentState;
	private BluetoothDevice mConnectedDevice;

	//

	public BluetoothConnection() {
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mCurrentState = 0;
	}

	/**
	 * Checks whether device has bluetooth capability
	 * 
	 * @return true if device has bluetooth
	 */
	public boolean deviceHasBluetooth() {
		return (mAdapter == null) ? false : true;
	}

	/**
	 * 
	 * @return true if bluetooth is enabled
	 */
	public boolean bluetoothIsActivated() {
		return mAdapter.isEnabled() ? true : false;
	}

	/**
	 * Activates Bluetooth on the device without asking for user permission
	 */
	public void activateBluetooth() {
		mAdapter.enable();
	}

	/**
	 * Initiate a bluetooth connection as a server
	 */
	public void connect() {
		mAcceptingThread = new AcceptingThread();
		mCurrentState = STATE_WAITING_FOR_CONNECTION;
		mAcceptingThread.start();
	}

	/**
	 * Initiate a bluetooth connection as a client
	 * 
	 * @param device
	 *            The bluetooth device to connect to
	 */
	public void connect(BluetoothDevice device) {
		mConnectingThread = new ConnectingThread(device);
		mCurrentState = STATE_ATTEMPTING_CONNECTION;
		mConnectingThread.start();
	}

	/**
	 * 
	 * @return A set of BluetoothDevices bonded to the device
	 */
	public Set<BluetoothDevice> getPairedDevices() {
		return mAdapter.getBondedDevices();
	}

	/**
	 * Get the state of the connection
	 * 
	 * @return The current state of the connection
	 */
	public int getState() {
		return mCurrentState;
	}

	/**
	 * Read the string in the buffer
	 * 
	 * @return the string in the buffer
	 */
	public String read() {
		synchronized (bufferLock) {
			return mMessageBuffer;
		}
	}

	/**
	 * Sends a string over the connection
	 * 
	 * @param s
	 */
	public void send(String s) {
		synchronized (writeLock) {
			mCommunicationThread.writeBytes(s.getBytes());
		}
	}

	/**
	 * 
	 * Sends a JSONObject over the connection
	 * 
	 * @param j
	 */
	public void send(JSONObject j) {
		synchronized (writeLock) {
			mCommunicationThread.writeBytes(j.toString().getBytes());
		}
	}

	/**
	 * 
	 * @author Congelateur This thread handles the creation of a
	 *         BluetoothServerSocket listening for incoming connection requests
	 * @see BluetoothServerSocket, Thread
	 * 
	 */
	private class AcceptingThread extends Thread {
		private BluetoothServerSocket mmServerSocket = null;
		private boolean running;

		public AcceptingThread() {
			try {
				// initiate a server socket
				mmServerSocket = mAdapter.listenUsingRfcommWithServiceRecord(
						"Bluetooth Andro Foot", mUuid);
			} catch (IOException e) {
			}
		}

		public void run() {
			running = true;
			BluetoothSocket bSocket = null;
			while (running) {
				// listen for connections
				try {
					bSocket = mmServerSocket.accept();
				} catch (IOException e) {
					mCurrentState = STATE_EXISTING;
					running = false;
				}
				if (bSocket != null) {
					// if socket is valid close server socket and stop thread
					mBluetoothSocket = bSocket;
					running = false;
					mCurrentState = STATE_CONNECTED;
					mCommunicationThread = new CommunicationThread();
					mCommunicationThread.run();
					mAcceptingThread = null;
				}
			}

		}
	}

	/**
	 * 
	 * @author Congelateur This thread handles the connection request to another
	 *         device
	 * 
	 */
	private class ConnectingThread extends Thread {
		private BluetoothDevice mmDevice;
		private BluetoothSocket mmSocket = null;

		public ConnectingThread(BluetoothDevice device) {
			mmDevice = device;
			try {
				mmSocket = mmDevice.createRfcommSocketToServiceRecord(mUuid);
			} catch (IOException e) {
			}
		}

		public void run() {
			try {
				mmSocket.connect();
				mBluetoothSocket = mmSocket;
				mCurrentState = STATE_CONNECTED;
				mCommunicationThread = new CommunicationThread();
				mCommunicationThread.run();
				mConnectingThread = null;
				mConnectedDevice = mmDevice;
			} catch (IOException e) {
				mCurrentState = STATE_EXISTING;
				try {
					mmSocket.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 
	 * @author Congelateur This thread handles the reading and writing of
	 *         messages over the established connection
	 * @see Thread
	 */
	private class CommunicationThread extends Thread {
		private boolean running;
		private OutputStream mmOutputStream;
		private InputStream mmInputStream;

		public CommunicationThread() {
			try {
				mmOutputStream = mBluetoothSocket.getOutputStream();
				mmInputStream = mBluetoothSocket.getInputStream();

			} catch (IOException e) {
			}

		}

		/**
		 * Listen continously for incoming messages and passes them to the
		 * buffer
		 */
		public void run() {
			running = true;
			byte[] input = new byte[4096];
			while (running) {
				try {
					mmInputStream.read(input);
					synchronized (bufferLock) {
						mMessageBuffer = new String(input);
					}
				} catch (IOException e) {
					running = false;
					mCurrentState = STATE_EXISTING;
				}

			}
		}

		/**
		 * Writes a byte array asynchronously on the connection output stream
		 * 
		 * @param bytes
		 *            a byte array of the message to send
		 */
		public void writeBytes(byte[] bytes) {
			try {
				mmOutputStream.write(bytes);
			} catch (IOException e) {
			}
		}
	}
};
