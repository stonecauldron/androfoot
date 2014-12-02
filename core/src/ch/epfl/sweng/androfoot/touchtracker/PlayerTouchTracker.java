package ch.epfl.sweng.androfoot.touchtracker;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.interfaces.ObservableTouchTracker;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Ahaeflig
 * 
 *         Singleton class that deal with two player's input. Add yourself to
 *         the tracker's observer list to start observing the touch input by
 *         implementing the TouchTrackerObserver interface
 * 
 */
public enum PlayerTouchTracker implements InputProcessor,
		ObservableTouchTracker, ClientObserver, HostObserver {
	INSTANCE;

	private final static int NO_POINTER = -1;

	private List<TouchTrackerObserver> observersPlayerOne;
	private List<TouchTrackerObserver> observersPlayerTwo;

	private TouchInfo mPlayerOneTouch;
	private TouchInfo mPlayerTwoTouch;

	private int mScreenWidth;

	private int mPlayerOneCurrentPointer = NO_POINTER;
	private int mPlayerTwoCurrentPointer = NO_POINTER;

	private boolean mClientMode = false;
	private boolean mHostMode = false;
	
	/**
	 * 
	 * @return the singeleton instance of the touch tracker
	 */
	public static PlayerTouchTracker getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @return the current pointer of the first player
	 */
	public int getmPlayerOneCurrentPointer() {
		return mPlayerOneCurrentPointer;
	}

	/**
	 * 
	 * @return the current pointer of the second player
	 */
	public int getmPlayerTwoCurrentPointer() {
		return mPlayerTwoCurrentPointer;
	}

	private PlayerTouchTracker() {
		Gdx.input.setInputProcessor(this);
		setNewScreenWidth(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

		observersPlayerOne = new ArrayList<TouchTrackerObserver>();
		observersPlayerTwo = new ArrayList<TouchTrackerObserver>();
		mPlayerOneTouch = new TouchInfo();
		mPlayerTwoTouch = new TouchInfo();
	}

	private void updatePlayerOne() {
		for (TouchTrackerObserver obs : observersPlayerOne) {
			obs.updatePlayerOne(1, mPlayerOneTouch.touchX,
					mPlayerOneTouch.touchY, mPlayerOneTouch.touched);
		}
		
		if (mHostMode) {
			PlayerHost.sendHostData(new InputData(mPlayerOneTouch.touchX, mPlayerOneTouch.touchY, mPlayerOneTouch.touched, false));
		}
	}

	private void updatePlayerTwo() {
		for (TouchTrackerObserver obs : observersPlayerTwo) {
			obs.updatePlayerTwo(2, mPlayerTwoTouch.touchX,
					mPlayerTwoTouch.touchY, mPlayerTwoTouch.touched);
		}
		
		if (mClientMode) {
			PlayerClient.sendClientData(new InputData(mPlayerTwoTouch.touchX, mPlayerTwoTouch.touchY, mPlayerTwoTouch.touched, false));
		}
		
	}

	/**
	 * @goal Call this method everytime the screen is resized
	 * @param screenWidth
	 *            = the size of the new screen
	 */
	public void setNewScreenWidth(int screenWidth, int screenHeight) {
		this.mScreenWidth = screenWidth;
	}

	@Override
	public void addObserverPlayerOne(TouchTrackerObserver obs) {
		observersPlayerOne.add(obs);
	}

	@Override
	public boolean removeObserverPlayerOne(TouchTrackerObserver obs) {
		return observersPlayerOne.remove(obs);
	}

	@Override
	public void addObserverPlayerTwo(TouchTrackerObserver obs) {
		observersPlayerTwo.add(obs);
	}

	@Override
	public boolean removeObserverPlayerTwo(TouchTrackerObserver obs) {
		return observersPlayerTwo.remove(obs);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (mPlayerOneCurrentPointer == NO_POINTER) {
			if (screenX <= mScreenWidth / 2) {
				mPlayerOneCurrentPointer = pointer;
				mPlayerOneTouch.touchX = screenX;
				mPlayerOneTouch.touchY = screenY;
				mPlayerOneTouch.touched = true;
				updatePlayerOne();
			}
		}

		if (mPlayerTwoCurrentPointer == NO_POINTER) {
			if (screenX > mScreenWidth / 2) {
				mPlayerTwoCurrentPointer = pointer;
				mPlayerTwoTouch.touchX = screenX;
				mPlayerTwoTouch.touchY = screenY;
				mPlayerTwoTouch.touched = true;
				updatePlayerTwo();
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (mPlayerOneCurrentPointer == pointer) {
			mPlayerOneCurrentPointer = NO_POINTER;
			mPlayerOneTouch.touched = false;
			mPlayerOneTouch.touchX = 0;
			mPlayerOneTouch.touchY = 0;
			updatePlayerOne();
		} else if (mPlayerTwoCurrentPointer == pointer) {
			mPlayerTwoCurrentPointer = NO_POINTER;
			mPlayerTwoTouch.touched = false;
			mPlayerTwoTouch.touchX = 0;
			mPlayerTwoTouch.touchY = 0;
			updatePlayerTwo();
		}

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		if (mPlayerOneCurrentPointer == pointer) {
			if (screenX <= mScreenWidth / 2) {
				mPlayerOneCurrentPointer = pointer;
				mPlayerOneTouch.touchX = screenX;
				mPlayerOneTouch.touchY = screenY;
				mPlayerOneTouch.touched = true;
				updatePlayerOne();
			} else {
				mPlayerOneTouch.touched = false;
				updatePlayerOne();
			}
		}

		if (mPlayerTwoCurrentPointer == pointer) {
			if (screenX > mScreenWidth / 2) {
				mPlayerTwoCurrentPointer = pointer;
				mPlayerTwoTouch.touchX = screenX;
				mPlayerTwoTouch.touchY = screenY;
				mPlayerTwoTouch.touched = true;
				updatePlayerTwo();
			} else {
				mPlayerTwoTouch.touched = false;
				updatePlayerTwo();
			}
		}
		return true;

	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void gameClientStart() {
		mClientMode = true;
	}

	@Override
	public void gameHostStart() {
		mHostMode = true;
	}

	@Override
	public void updateClientData(InputData data) {
	}

	@Override
	public void updateHostData(HostData data) {
	}

	@Override
	public void updateHostTouchData(InputData data) {
		
	}
}