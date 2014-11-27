package ch.epfl.sweng.androfoot.interfaces;

public interface DefaultPowerUp extends DefaultWorldObject {

	/**
	 * Returns the x position of the Power Up object.
	 * @return
	 */
	float getPositionX();
	
	/**
	 * Returns the x position of the Power Up object.
	 * @return
	 */
	float getPositionY();
	
	/**
	 * Sets the position of the Power Up.
	 */
	void setPowerUpPosition();
	
	/**
	 * Clone the current state of the Power Up object.
	 * @return
	 */
	DefaultPowerUp clone();
}
