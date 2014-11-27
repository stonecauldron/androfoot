package ch.epfl.sweng.androfoot.interfaces;

/**
 * @author Ahaeflig
 *
 *         Interface used to create a observable accelerometer which is trigger
 *         events when the device is shaking
 */
public interface ObservableAccelerometer {

	/**
	 * @param obs the class that wants to observe the accelerometer.
	 */
	void addObserverShaker(AccelerometerObserver obs);

	/**
	 * @param obs the class that wants to be removed from the observer list
	 * @return true if correctly removed
	 */
	boolean removeObserverShaker(AccelerometerObserver obs);
}
