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
	 * Returns the radius of the hitbox.
	 * @return
	 */
	float getHitBoxRadius();
	
	/**
	 * Sets the position of the Power Up.
	 */
	void setPowerUpPosition(float x, float y);
	
	/**
	 * Clone the current state of the Power Up object.
	 * @return
	 */
	DefaultPowerUp clone();
}
