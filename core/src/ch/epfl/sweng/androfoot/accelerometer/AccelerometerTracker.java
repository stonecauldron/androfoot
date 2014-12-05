package ch.epfl.sweng.androfoot.accelerometer;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.AccelerometerObserver;
import ch.epfl.sweng.androfoot.interfaces.ObservableAccelerometer;

import com.badlogic.gdx.Gdx;

/**
 * @author Ahaeflig
 * 
 *         Singleton implementation of a class that provide useful way to deal
 *         with the accelerometer
 */
public enum AccelerometerTracker implements ObservableAccelerometer {
	INSTANCE;

	private static final double SHAKE_TRIGGER = 1.7;
	private static float GRAVITY_EARTH = (float) 9.81;
	private float mXGrav = 1;
	private float mYGrav = 1;
	private float mZGrav = 1;

	private List<AccelerometerObserver> observers = new ArrayList<AccelerometerObserver>();

	/**
	 * @return the singleton instance of the accelerometer
	 */
	public static AccelerometerTracker getInstance() {
		return INSTANCE;
	}

	private void pollGrav() {
		mXGrav = Gdx.input.getAccelerometerX() / GRAVITY_EARTH;
		mYGrav = Gdx.input.getAccelerometerY() / GRAVITY_EARTH;
		mZGrav = Gdx.input.getAccelerometerZ() / GRAVITY_EARTH;
	}

	/**
	 * This method is to be called in every step of the physics' calculation
	 * since polling is the only way to check if the device is being shaked
	 * 
	 * @return true if the device is shaking (enough to trigger the event) false
	 *         otherwise
	 */
	public boolean isShaking() {
		pollGrav();
		if (Math.sqrt((mXGrav * mXGrav) + (mYGrav * mYGrav) + (mZGrav * mZGrav)) >= SHAKE_TRIGGER
				&& Configuration.getInstance().getTilting()) {
			notifyIsShakingObserver();
			return true;
		}
		return false;
	}

	private void notifyIsShakingObserver() {
		for (AccelerometerObserver obs : observers) {
			obs.isShaking();
		}
	}

	/**
	 * @return the X axis force of the accelerometer
	 */
	public float getmXGrav() {
		return mXGrav;
	}

	/**
	 * @return the Y axis force of the accelerometer
	 */
	public float getmYGrav() {
		return mYGrav;
	}

	/**
	 * @return the Z axis force of the accelerometer
	 */
	public float getmZGrav() {
		return mZGrav;
	}

	@Override
	public void addObserverShaker(AccelerometerObserver obs) {
		observers.add(obs);
	}

	@Override
	public boolean removeObserverShaker(AccelerometerObserver obs) {
		return true;
	}

}
