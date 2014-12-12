package ch.epfl.sweng.androfoot.kryonetnetworking;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Ahaeflig
 * 
 *         Classed used to abstract input data sent between players
 */
public class InputData {

	private float touchX;
	private float touchY;
	private List<Vector2> paddlePosition;

	public InputData(float touchX, float touchY, List<Vector2> paddlePosition) {
		this.touchX = touchX;
		this.touchY = touchY;
		this.paddlePosition = paddlePosition;
	}

	/**
	 * Used by the kyro serial only, Do not use this useless constructor
	 */
	public InputData() {

	}

	public List<Vector2> getPaddlePosition() {
		return paddlePosition;
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