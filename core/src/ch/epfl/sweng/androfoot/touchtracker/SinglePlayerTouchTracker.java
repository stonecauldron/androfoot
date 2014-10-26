package ch.epfl.sweng.androfoot.touchtracker;


import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.ObservableTouchTracker;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Ahaeflig
 * Singleton implementation of one player touch input tracking.
 * Add yourself to the tracker's observer list to start observing the touch input
 * by implementing the TouchTrackerObserver interface 
 * In our project this list will probably contain only one Object.
 */
public final class SinglePlayerTouchTracker implements InputProcessor, ObservableTouchTracker	 {

	private static final SinglePlayerTouchTracker INSTANCE = new SinglePlayerTouchTracker();
	private TouchInfo touch = new TouchInfo();
	private List<TouchTrackerObserver> observers = new ArrayList<TouchTrackerObserver>();
	
	private SinglePlayerTouchTracker() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}
		Gdx.input.setInputProcessor(this);
	}

	public static SinglePlayerTouchTracker getInstance() {
		return INSTANCE;
	}
	
	private void update() {
		for (TouchTrackerObserver obs: observers) {
			obs.update(0, touch.touchX, touch.touchY, touch.touched);
		}
	}
	
	@Override
	public void addObserver(TouchTrackerObserver obs) {
		observers.add(obs);
	}

	@Override
	public boolean removeObserver(TouchTrackerObserver o) {
		return observers.remove(o);
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
		if (pointer < 1) {
			touch.touchX = screenX;
			touch.touchY = screenY;
			touch.touched = true;
			update();

		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == 1) {
			touch.touchX = 0;
			touch.touchY = 0;
			touch.touched = false;
			update();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (pointer < 1) {
			touch.touchX = screenX;
			touch.touchY = screenY;
			touch.touched = true;
			update();
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
