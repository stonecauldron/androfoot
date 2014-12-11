package ch.epfl.sweng.androfoot.kryonetnetworking;

/**
 * @author Ahaeflig
 * 
 *         Classed used to abstract input data sent between players
 */
public class InputData {

	private float touchX;
	private float touchY;


	public InputData(float touchX, float touchY) {
		this.touchX = touchX;
		this.touchY = touchY;
	}

	/**
	 * Used by the kyro serial only, Do not use this useless constructor
	 */
	public InputData() {

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
