package ch.epfl.sweng.androfoot.board;

/**
 * Class to represent the board object.
 * It will contain all the elements necessary for a game.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class Board {
	
	private static final String ERROR_MESSAGE = "Board was not created.";
	
	private static Board mInstance;
	
	/**
	 * Get the board that is currently active.
	 * @return the active board
	 */
	public static Board getInstance() {
		if (mInstance == null) {
			throw new UnsupportedOperationException(ERROR_MESSAGE);
		}
		return mInstance;
	}
	
	static void setInstance(Board instance) {
		mInstance = instance;
	}
}
