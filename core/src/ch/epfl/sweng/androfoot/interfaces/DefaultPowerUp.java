package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.ImmutablePowerUp;

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
	ImmutablePowerUp getStates();
}
