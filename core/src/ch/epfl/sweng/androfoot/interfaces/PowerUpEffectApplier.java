package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a class which apply effect of powerups
 * 
 * @author Guillaume
 *
 */
public interface PowerUpEffectApplier extends PowerUpObserver {

	/**
	 * update the time intervals
	 * 
	 * @param delta
	 *            the time elapsed
	 */
	void update(float delta);

	/**
	 * Map a powerUp to its effect
	 * 
	 * @param power
	 *            the powerup
	 * @param effect
	 *            the effect of this powerup
	 */
	void setEffectForBody(DefaultPowerUp power, PowerUpEffect effect);

}
