package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Class used to abstract information about tilting to send over network
 *         between two players
 */
public class ShakeData {
	private float mBallSpeedXAfterTilt;
	private float mBallSpeedYAfterTilt;

	/**
	 * @param speedX the new speed of the ball in the x axis coordinate after tilting
	 * @param speedY the new speed of the ball in the Y axis coordinate after tilting
	 */
	public ShakeData(float speedX, float speedY) {
		this.mBallSpeedXAfterTilt = speedX;
		this.mBallSpeedYAfterTilt = speedY;
		
	}

	/**
	 * Used by the kyro serial only, Do not use this useless constructor
	 */
	public ShakeData() {
		
	}
	
	public float getmBallSpeedXAfterTilt() {
		return mBallSpeedXAfterTilt;
	}

	public float getmBallSpeedYAfterTilt() {
		return mBallSpeedYAfterTilt;
	}
}
