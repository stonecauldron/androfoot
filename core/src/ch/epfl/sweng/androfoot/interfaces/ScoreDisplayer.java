package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent an object able to display the score a versus game
 * @author Guillame Leclerc
 *
 */
public interface ScoreDisplayer {
	/**
	 * Set the current score of the game
	 * @param player1 The score for the player 1
	 * @param player2 The score for the player 2
	 */
	public void setScore(int player1, int player2);
}
