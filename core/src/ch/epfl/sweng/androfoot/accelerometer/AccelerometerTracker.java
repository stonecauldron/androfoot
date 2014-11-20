package ch.epfl.sweng.androfoot.accelerometer;

import com.badlogic.gdx.Gdx;

public enum AccelerometerTracker {
	INSTANCE;
		
	private static final double SHAKE_TRIGGER = 1.7;
	private static float GRAVITY_EARTH = (float) 9.81;
	private float mXGrav = 1;
    private float mYGrav = 1;
    private float mZGrav = 1;
	
    public static AccelerometerTracker getInstance() {
    	return INSTANCE;
    }
    
	public void pollGrav() {
		mXGrav = Gdx.input.getAccelerometerX() / GRAVITY_EARTH;
	    mYGrav = Gdx.input.getAccelerometerY() / GRAVITY_EARTH;
	    mZGrav = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;
	}
	
	public boolean isShaking() {
		pollGrav();
		return Math.sqrt((mXGrav * mXGrav) + (mYGrav * mYGrav) + (mZGrav * mZGrav)) >= SHAKE_TRIGGER;
	}

	public float getmXGrav() {
		return mXGrav;
	}

	public float getmYGrav() {
		return mYGrav;
	}

	public float getmZGrav() {
		return mZGrav;
	}
	
	
	
}
