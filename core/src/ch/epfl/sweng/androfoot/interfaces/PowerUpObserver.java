package ch.epfl.sweng.androfoot.interfaces;

/**
 * Event that occurs when ball touches a power up.
 * @author Matvey
 *
 */
public interface PowerUpObserver {

	/**
	 * Triggered when the ball hits a power up.
	 * Applies the power up to the players of the team and eliminates
	 * the power up object from the world.
	 * @param powerUp The power up touched.
	 */
	void applyPowerUp(DefaultPowerUp powerUp);
}
