package ch.epfl.sweng.androfoot.board;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.players.AbstractPlayer;

/**
 * Class to represent the board object.
 * It will contain all the elements necessary for a game.
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class Board {
	
	private static final String ERROR_MESSAGE = "Board was not created.";
	
	private static Board mInstance;
	
	private AbstractPlayer playerOne;
	private AbstractPlayer playerTwo;
	
	private int playerOneScore;
	private int playerTwoScore;
	
	private Ball ball;
	
	/**
	 * Board constructor
	 * @param p1 reference to the first player.
	 * @param p2 reference to the second player.
	 */
	Board (AbstractPlayer p1, AbstractPlayer p2) {
		playerOne = p1;
		playerTwo = p2;
		
		playerOneScore = 0;
		playerTwoScore = 0;
		
		ball = Ball.createBall();
	}
	
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
