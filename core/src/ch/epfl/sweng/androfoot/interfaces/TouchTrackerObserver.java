package ch.epfl.sweng.androfoot.interfaces;

/**
 * @author Ahaeflig
 * Implement this interface to start observing user touch inputs.
 */
public interface TouchTrackerObserver {
	
	/**
	 * @param playerId The pointer Id of the player 0 is for first player and 1 is for the 2nd player
	 * @param posX position X of the last touch by player playerId
	 * @param posY position Y of the last touch by player playerId
	 * @param touched if player is currently touching its area of the screen
	 */
	void update(int playerId, float posX, float posY, boolean touched);
}
