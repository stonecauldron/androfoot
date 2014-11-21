package ch.epfl.sweng.androfoot.touchtracker;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.ObservableTouchTracker;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.players.PlayerNumber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Ahaeflig
 * 
 *         Singleton implementation of two player touch input tracking. Add
 *         yourself to the tracker's observer list to start observing the touch
 *         input by implementing the TouchTrackerObserver interface In our
 *         project this list will probably contain only one Object.
 */
public enum PlayerTouchTracker implements InputProcessor,
		ObservableTouchTracker {
	INSTANCE;

	private final static int NO_POINTER = -1;
	private final static float CM_TRESHOLD = 0.015f;

	private List<TouchTrackerObserver> observersPlayerOne;
	private List<TouchTrackerObserver> observersPlayerTwo;

	private TouchInfo mPlayerOneTouch;
	private TouchInfo mPlayerTwoTouch;

	private int mScreenWidth;
	private int mScreenHeigth;

	private int mPlayerOneCurrentPointer = NO_POINTER;
	private int mPlayerTwoCurrentPointer = NO_POINTER;

	public int getmPlayerOneCurrentPointer() {
		return mPlayerOneCurrentPointer;
	}

	public int getmPlayerTwoCurrentPointer() {
		return mPlayerTwoCurrentPointer;
	}

	private float mMoveTresholdX;
	private float mMoveTresholdY;

	private PlayerTouchTracker() {
		Gdx.input.setInputProcessor(this);
		setNewScreenWidth(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());

		observersPlayerOne = new ArrayList<TouchTrackerObserver>();
		observersPlayerTwo = new ArrayList<TouchTrackerObserver>();
		mPlayerOneTouch = new TouchInfo();
		mPlayerTwoTouch = new TouchInfo();
	}

	public static PlayerTouchTracker getInstance() {
		return INSTANCE;
	}

	private void updatePlayerOne() {
		for (TouchTrackerObserver obs : observersPlayerOne) {
			obs.updatePlayerOne(1,
					mPlayerOneTouch.touchX, mPlayerOneTouch.touchY,
					mPlayerOneTouch.touched);
		}
	}

	private void updatePlayerTwo() {
		for (TouchTrackerObserver obs : observersPlayerTwo) {
			obs.updatePlayerTwo(2,
					mPlayerTwoTouch.touchX, mPlayerTwoTouch.touchY,
					mPlayerTwoTouch.touched);
		}
	}

	/**
	 * @goal Call this method everytime the screen is resized
	 * @param screenWidth
	 *            = the size of the new screen
	 */
	public void setNewScreenWidth(int screenWidth, int screenHeight) {
		this.mScreenWidth = screenWidth;
		this.mScreenHeigth = screenHeight;
		updateTreshold(screenWidth, screenHeight);
	}

	public float getmMoveTresholdX() {
		return mMoveTresholdX;
	}

	public float getmMoveTresholdY() {
		return mMoveTresholdY;
	}

	/**
	 * Call this method when the screen is resized to update new correct
	 * treshold value
	 * @param screenHeight 
	 * @param screenWidth 
	 */
	public void updateTreshold(int screenWidth, int screenHeight) {

		float xPixelPerCentimeter = Gdx.graphics.getPpcX();
		float yPixelPerCentimeter = Gdx.graphics.getPpcY();
		
		mMoveTresholdX = xPixelPerCentimeter * CM_TRESHOLD * (((float)screenWidth / xPixelPerCentimeter) / 10);
		mMoveTresholdY = yPixelPerCentimeter * CM_TRESHOLD * (((float)screenHeight / yPixelPerCentimeter) / 6);

		Gdx.app.log("TRESH HOlD VAL X", " " + mMoveTresholdX + "SCRREN WIDHT " + screenWidth);
		Gdx.app.log("TRESH HOlD VAL Y", " " + mMoveTresholdY);
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
		return observersPlayerOne.remove(obs);
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

		if ( mPlayerTwoCurrentPointer == NO_POINTER) {
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
