package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Classed used to abstract data sent by a client (slave) to the server
 *         therefore it contains only the touch info
 */
public class TouchData {

	private float touchX;
	private float touchY;
	
	private boolean isShaking;

	public TouchData(float touchX, float touchY, boolean shake) {
		this.touchX = touchX;
		this.touchY = touchY;
		this.isShaking = shake;
	}

	/**
	 * @return X coordinate
	 */
	public boolean getIsShaking() {
		return isShaking;
	}
	
	/**
	 * @return X coordinate
	 */
	public float getTouchX() {
		return touchX;
	}

	/**
	 * @return Y coordinate
	 */
	public float getTouchY() {
		return touchY;
	}
	
	
}
