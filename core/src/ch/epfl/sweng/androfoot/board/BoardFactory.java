package ch.epfl.sweng.androfoot.board;

import ch.epfl.sweng.androfoot.players.PlayerFactory;
import ch.epfl.sweng.androfoot.players.PlayerType;

/**
 * Factory class to instantiate the Board object.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class BoardFactory {

	/**
	 * Creates a board containing all the game elements
	 * @param playerOne the type of the first player (IA, Remote, Local)
	 * @param playerTwo the type of the second player.
	 */
	public static void setupBoard(PlayerType playerOne, PlayerType playerTwo) {
		Board board = new Board(PlayerFactory.createPlayer(playerOne),
				PlayerFactory.createPlayer(playerTwo));
		Board.setInstance(board);
	}
}
