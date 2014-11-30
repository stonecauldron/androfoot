package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class to abstract game information needed to display the game on a
 *         slave client
 */
public class HostData {
	/*
	 * BRUTE DATA SECTION
	 */
	private float touchX;
	private float touchY;
	private boolean touched;

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
	public HostData(float playerOneXPosition, float playerOneYPosition,
			boolean touched, float ballXPosition, float ballYPosition,
			int playerOneScore, boolean isShaking) {

		this.touchX = playerOneXPosition;
		this.touchY = playerOneYPosition;
		this.touched = touched;

		this.mBallX = ballXPosition;
		this.mBallY = ballYPosition;

		this.mIsShaking = isShaking;

	}

	public HostData() {
		
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

	public float getTouchX() {
		return touchX;
	}

	public float getTouchY() {
		return touchY;
	}

	public boolean isTouched() {
		return touched;
	}

	public float getmBallX() {
		return mBallX;
	}

	public float getmBallY() {
		return mBallY;
	}

}
