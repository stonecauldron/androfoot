package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.graphics.Color;

/**
 * Represent a powerup
 * 
 * @author Guillaume
 *
 */
public interface PowerUp extends Drawable {

	/**
	 * get the radius of the powerup
	 * 
	 * @return the radius
	 */
	float getRadius();

	/**
	 * get the duration of the powerup
	 * 
	 * @return the duration
	 */
	float getEffectDuration();

	/**
	 * A runnable that apply the effect of the powerup
	 * 
	 * @return a {@link Runnable}
	 */
	Runnable getEffectStart();

	/**
	 * A runnable that revert the effect of the powerup
	 * 
	 * @return a {@link Runnable}
	 */
	Runnable getEffectStop();

	/**
	 * Get the color of the power up
	 * @return the color
	 */
	Color getColor();
}
