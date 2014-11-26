package ch.epfl.sweng.androfoot.interfaces;

/**
 * @author Ahaeflig
 *
 *         Interface used to observe the accelerometer and get notified when the
 *         accelerometer is shaking
 */

public interface AccelerometerObserver {

	/**
	 * Called when the accelerometer is shaking
	 */
	public void isShaking();

}
