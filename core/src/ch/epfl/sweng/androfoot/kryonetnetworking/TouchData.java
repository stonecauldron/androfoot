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

	public TouchData(float touchX, float touchY) {
		this.touchX = touchX;
		this.touchY = touchY;
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
