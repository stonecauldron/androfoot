package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class to abstract game information needed to display the game on a
 *         slave client about physics world status
 */
public class HostData {
	/*
	 * BRUTE DATA SECTION
	 */
	private float mBallPosX;
	private float mBallPosY;

	private float mBallSpeedX;
	private float mBallSpeedY;

	/*
	 * EVENT SECTION
	 */

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
	public HostData(float ballXPosition, float ballYPosition, float ballSpeedX,
			float ballSpeedY) {
		this.mBallPosX = ballXPosition;
		this.mBallPosY = ballYPosition;
		this.mBallSpeedX = ballSpeedX;
		this.mBallSpeedY = ballSpeedY;
	}

	public float getmBallSpeedX() {
		return mBallSpeedX;
	}

	public float getmBallSpeedY() {
		return mBallSpeedY;
	}

	/**
	 * Used by the kyro serial only, Do not use this useless constructor
	 */
	public HostData() {

	}

	public float getmBallX() {
		return mBallPosX;
	}

	public float getmBallY() {
		return mBallPosY;
	}

}
