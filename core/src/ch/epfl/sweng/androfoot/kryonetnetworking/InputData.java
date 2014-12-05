package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Classed used to abstract input data sent between players
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

	/**
	 * Used by the kyro serial only, Do not use this useless constructor
	 */
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
