package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class to abstract game information needed to display the game on a
 *         slave client
 */
public class GameData {
	/*
	 * BRUTE DATA SECTION
	 */
	private float mPlayerOneX;
	private float mPlayerOneY;
	
	private float mPlayerTwoX;
	private float mPlayerTwoY;

	private float mBallX;
	private float mBallY;

	/*
	 * EVENT SECTION
	 */
	private boolean mPlayerOneHasScored;
	private boolean mPlayerTwoHasScored;

	private boolean mIsShaking;

	/**
	 * 
	 * @param playerOneXPosition
	 *            first player x position
	 * @param playerOneYPosition
	 *            first player y position
	 * @param playerTwoXPosition
	 *            second player x position (should be slave)
	 * @param playerTwoYPosition
	 *            second player y position (should be slave)
	 * @param ballXPosition
	 * @param ballYPosition
	 */
	public GameData(float playerOneXPosition, float playerOneYPosition,
			float playerTwoXPosition, float playerTwoYPosition,
			float ballXPosition, float ballYPosition, int playerOneScore,
			int playerTwoScore, boolean playerOneScored,
			boolean playerTwoScored, boolean ballOnPaddle, boolean ballOnWall,
			boolean isShaking) {
		this.mPlayerOneX = playerOneXPosition;
		this.mPlayerOneY = playerOneYPosition;
		this.mPlayerTwoX = playerTwoXPosition;
		this.mPlayerTwoY = playerTwoYPosition;

		this.mBallX = ballXPosition;
		this.mBallY = ballYPosition;


		/*
		 * EVENTS
		 */
		this.mPlayerOneHasScored = playerOneScored;
		this.mPlayerTwoHasScored = playerTwoScored;


		this.mIsShaking = isShaking;

	}

	public boolean ismPlayerOneHasScored() {
		return mPlayerOneHasScored;
	}

	public boolean ismPlayerTwoHasScored() {
		return mPlayerTwoHasScored;
	}


	public boolean ismIsShaking() {
		return mIsShaking;
	}

	public float getmPlayerOneX() {
		return mPlayerOneX;
	}

	public float getmPlayerOneY() {
		return mPlayerOneY;
	}

	public float getmPlayerTwoX() {
		return mPlayerTwoX;
	}

	public float getmPlayerTwoY() {
		return mPlayerTwoY;
	}

	public float getmBallX() {
		return mBallX;
	}

	public float getmBallY() {
		return mBallY;
	}

}
