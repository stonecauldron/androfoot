package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.Player;

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
	void setBall(Player player, boolean teamFlag);
}
