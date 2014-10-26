package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class CircleGenerator implements PolygonGenerator {
	
	private static final float MIN_ANGLE = 0f;
	private static final float MAX_ANGLE_RADIAN = (float) Math.PI * 2;
	private static final float MAX_ANGLE_DEGREE = 360f;

	private final int segments;
	private final float from;
	private final float to;
	private final float radius;
	private final float[] vertexes;
	
	private boolean isGenerated;
	
	public CircleGenerator(int nbSegments) {
		this(nbSegments, MIN_ANGLE, MAX_ANGLE_RADIAN, AngleType.RADIAN);
	}

	public CircleGenerator(int nbSegments, float angleFrom, float angleTo, AngleType angleType) {
		this(nbSegments, angleFrom, angleTo, angleType, 1f);
	}
	
	public CircleGenerator(int nbSegments, float angleFrom, float angleTo, AngleType angleType, float radiusArg) {
		if(nbSegments < 1) {
			throw new IllegalArgumentException("the number of segments must be greater than 1");
		}

		isGenerated = false;

		segments = nbSegments;
		vertexes = new float[(segments + 1) * 2];
		
		radius = radiusArg;
		
		if(angleFrom > angleTo) {
			float temp = angleTo;
			angleTo = angleFrom;
			angleFrom = temp;
		}

		if (angleType == AngleType.DEGREE) {
			from = degreeToRadian(angleFrom);
			to = degreeToRadian(angleTo);
		} else {
			from = angleFrom;
			to = angleTo;
		}
	}
	
	public static float degreeToRadian(float angleDegree) {
		return angleDegree/MAX_ANGLE_DEGREE*MAX_ANGLE_RADIAN;
	}
	
	public static float simplifyRadianAngle(float angle) {
		angle = Math.abs(angle);
		double divided = Math.ceil(angle / MAX_ANGLE_RADIAN);
		return (float) (angle-divided);
	}
	
	private void generate() {
		if (isGenerated) {
			return;
		}
		
		float angleStep = (to-from)/(segments);
		
		float currentAngle = from;
		
		for (int i = 0 ; i <= segments ; i++,currentAngle+=angleStep) {
			vertexes[2*i] = (float)Math.cos(currentAngle)*radius;
			vertexes[2*i + 1] = (float)Math.sin(currentAngle)*radius;
		}
	}

	@Override
	public float[] generateVertexesFloat() {
		generate();
		return vertexes;
	}

	@Override
	public float[] generateVertexesFloatInZPlane(float z) {
		generate();
		float[] vertexes3D = new float[(segments + 1) * 3];
		for (int i = 0 ; i <= segments ; i++){
			vertexes3D[3*i] = vertexes[2*i];
			vertexes3D[3*i + 1] = vertexes[2*i] + 1;
			vertexes3D[3*i + 2] = z;
		}
		
		return vertexes3D;
	}

	@Override
	public Set<ImmutablePoint<Float>> generatePointsSet() {
		generate();
		HashSet<ImmutablePoint<Float>> result = new HashSet<ImmutablePoint<Float>>();
		for (int i = 0 ; i <= segments ; i ++) {
			result.add(new ImmutablePoint<Float>(vertexes[2*i],vertexes[2*i + 1]));
		}

		return result;
	}


}
