package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Classed used to abstract data sent by a client (slave) to the server
 *         therefore it contains only the touch info
 */
public class InputData {

	private float touchX;
	private float touchY;
	private boolean touched;

	private boolean isShaking;

	public InputData(float touchX, float touchY, boolean touched, boolean shake) {
		this.touchX = touchX;
		this.touchY = touchY;
		this.touched = touched;
		this.isShaking = shake;
	}
	
	public InputData() {
		
	}

	public boolean isTouched() {
		return touched;
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
