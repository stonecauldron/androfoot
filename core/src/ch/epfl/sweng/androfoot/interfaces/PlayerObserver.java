package ch.epfl.sweng.androfoot.interfaces;

/**
 * Event that occurs when the ball hits the back of the player.
 * @author Matvey
 *
 */
public interface PlayerObserver {

	/**
	 * Triggered when the ball hits the ball hits a player in the back.
	 * Sets the ball in fron of the player for easier precision shooting.
	 * @param Indicates the team of the player. 
	 */
	void setBall(boolean teamFlag);
}
