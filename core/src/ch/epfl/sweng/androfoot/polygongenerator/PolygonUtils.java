package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * Provide some static utilitary methods and usefull constants
 * 
 * @author Guillaume
 *
 */
public final class PolygonUtils {
	static final float MIN_ANGLE = 0f;
	static final float MAX_ANGLE_RADIAN = (float) Math.PI * 2;
	static final float MAX_ANGLE_DEGREE = 360f;

	/**
	 * Converts an angle from degree to radians
	 * 
	 * @param angleDegree
	 *            the angle in degree
	 * @return the angle in radians
	 */
	public static float degreeToRadian(float angleDegree) {
		return angleDegree / MAX_ANGLE_DEGREE * MAX_ANGLE_RADIAN;
	}

	/**
	 * Simplify an angle in radians
	 * 
	 * @param angle
	 *            a simplified angle (between 0 and 2Pi)
	 * @return
	 */
	public static float simplifyRadianAngle(float angle) {
		double divided = Math.floor(angle / MAX_ANGLE_RADIAN);
		return (float) (angle - divided * MAX_ANGLE_RADIAN);
	}
}
