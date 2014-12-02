package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent an object which can be reset
 * @author Guillame Leclerc
 *
 */
public interface Resettable {

	/**
	 * Reset the world and all the state associated with it
	 */
	void reset();
}
