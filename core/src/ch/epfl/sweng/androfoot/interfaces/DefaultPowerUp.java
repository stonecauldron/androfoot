package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.ImmutablePowerUp;
import com.badlogic.gdx.graphics.Color;

/**
 * Interface that defines the default Power Up Object.
 * @author Matvey
 *
 */
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
	 * get the color of the poweup
	 * @return the color
	 */
	Color getColor();
	
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
