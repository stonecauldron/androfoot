package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * Generate a polygon wich is an approximation of a circle
 * It can also generate subparts of circles 
 * This is a circular Polygon, that means the the first and the last point are the same
 * @author Guillame Leclerc
 *
 */
public class CircleGenerator extends AbstractPolygonGenerator {
	
	private static final float MIN_ANGLE = 0f;
	private static final float MAX_ANGLE_RADIAN = (float) Math.PI * 2;
	private static final float MAX_ANGLE_DEGREE = 360f;

	private final int segments;
	private final float from;
	private final float to;
	private final float radius;
	private final float[] vertexes;
	
	private boolean isGenerated;
	
	/**
	 * The generator built will return a polygon which is an approximation of a circle of radius 1
	 * @param nbSegments the number of segments in the polygon (number of vertexes -1)
	 */
	public CircleGenerator(int nbSegments) {
		this(nbSegments, MIN_ANGLE, MAX_ANGLE_RADIAN, AngleType.RADIAN);
	}

	/**
	 * the generator build will return a polygon which is a subset of a circle of radius 1
	 * @param nbSegments the number of segments in the polygon (number of vertexes -1)
	 * @param angleFrom the angle to start the sub-circle
	 * @param angleTo than angle to stop the sub-circle
	 * @param angleType the unit used for the last two arguments
	 */
	public CircleGenerator(int nbSegments, float angleFrom, float angleTo, AngleType angleType) {
		this(nbSegments, angleFrom, angleTo, angleType, 1f);
	}
	
	/**
	 * the generator build will return a polygon which is a subset of a circle
	 * @param nbSegments the number of segments in the polygon (number of vertexes -1)
	 * @param angleFrom the angle to start the sub-circle
	 * @param angleTo than angle to stop the sub-circle
	 * @param angleType the unit used for the last two arguments
	 * @param radiusArg the radius of the circle
	 */
	public CircleGenerator(int nbSegments, float angleFrom, float angleTo, AngleType angleType, float radiusArg) {
		if(nbSegments < 1) {
			throw new IllegalArgumentException("the number of segments must be greater than 1");
		}

		isGenerated = false;

		segments = nbSegments;
		vertexes = new float[(segments + 2) * 2];
		
		radius = radiusArg;
		
		if(angleFrom > angleTo) {
			float temp = angleTo;
			angleTo = angleFrom;
			angleFrom = temp;
		}

		if (angleType == AngleType.DEGREE) {
			angleFrom = degreeToRadian(angleFrom);
			angleTo = degreeToRadian(angleTo);
		} 
		from = simplifyRadianAngle(angleFrom);
		to = simplifyRadianAngle(angleTo);
	}
	
	/**
	 * Converts an angle from degree to radians
	 * @param angleDegree the angle in degree
	 * @return the angle in radians
	 */
	public static float degreeToRadian(float angleDegree) {
		return angleDegree/MAX_ANGLE_DEGREE*MAX_ANGLE_RADIAN;
	}
	
	/**
	 * Simplify an angle in radians
	 * @param angle a simplified angle (between 0 and 2Pi)
	 * @return
	 */
	public static float simplifyRadianAngle(float angle) {
		double divided = Math.floor(angle / MAX_ANGLE_RADIAN);
		return (float) (angle-divided*MAX_ANGLE_RADIAN);
	}
	
	@Override
	protected float[] generate() {
		if (isGenerated) {
			return vertexes;
		}
		
		float angleStep = (to-from)/(segments);
		if(from-to == 0) {
			angleStep = MAX_ANGLE_RADIAN*2/segments;
		}
		
		float currentAngle = from;
		
		for (int i = 0 ; i <= segments ; i++,currentAngle+=angleStep) {
			vertexes[2*i] = (float)Math.cos(currentAngle)*radius;
			vertexes[2*i + 1] = (float)Math.sin(currentAngle)*radius;
		}
		vertexes[vertexes.length-2] = vertexes[0];
		vertexes[vertexes.length-1] = vertexes[1];
		isGenerated = true;
		return vertexes;
	}
}
