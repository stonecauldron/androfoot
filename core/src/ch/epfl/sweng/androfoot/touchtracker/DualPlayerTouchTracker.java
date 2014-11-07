package ch.epfl.sweng.androfoot.touchtracker;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.ObservableTouchTracker;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Ahaeflig
 * Singleton implementation of two player touch input tracking.
 * Add yourself to the tracker's observer list to start observing the touch input
 * by implementing the TouchTrackerObserver interface 
 * In our project this list will probably contain only one Object.
 */
public enum DualPlayerTouchTracker implements InputProcessor, ObservableTouchTracker {
	INSTANCE;
	
	private final static int NO_POINTER = -1;
	private final static int PLAYER_ONE = 0;
	private final static int PLAYER_TWO = 1;
	
	private List<TouchTrackerObserver> observers = new ArrayList<TouchTrackerObserver>();

	private TouchInfo mPlayerOneTouch = new TouchInfo();
	private TouchInfo mPlayerTwoTouch = new TouchInfo();

	private int mScreenWidth = Gdx.graphics.getWidth();
	private int mPlayerOneCurrentPointer = NO_POINTER;
	private int mPlayerTwoCurrentPointer = NO_POINTER;

	private DualPlayerTouchTracker() {
		Gdx.input.setInputProcessor(this);
	}
	
	public static DualPlayerTouchTracker getInstance() {
		return INSTANCE;
	}
	
	private void updatePlayerOne() {
		for (TouchTrackerObserver obs: observers) {
			obs.update(PLAYER_ONE, mPlayerOneTouch.touchX, mPlayerOneTouch.touchY, mPlayerOneTouch.touched);
		}
	}
	
	private void updatePlayerTwo() {
		for (TouchTrackerObserver obs: observers) {
			obs.update(PLAYER_TWO, mPlayerTwoTouch.touchX, mPlayerTwoTouch.touchY, mPlayerTwoTouch.touched);
		}
	}
	
	public void setNewScreenWidth(int screenWidth) {
		this.mScreenWidth = screenWidth;
	}
	

	@Override
	public void addObserver(TouchTrackerObserver obs) {
		observers.add(obs);
	}
	
	@Override
	public boolean removeObserver(TouchTrackerObserver obs) {
		return observers.remove(obs);
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

		if (mPlayerOneCurrentPointer == pointer
				|| mPlayerOneCurrentPointer == NO_POINTER) {
			if (screenX <= mScreenWidth / 2) {
				mPlayerOneCurrentPointer = pointer;
				mPlayerOneTouch.touchX = screenX;
				mPlayerOneTouch.touchY = screenY;
				mPlayerOneTouch.touched = true;
				updatePlayerOne();
			} 
		}
		
		if (mPlayerTwoCurrentPointer == pointer
				|| mPlayerTwoCurrentPointer == NO_POINTER) {
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


}
