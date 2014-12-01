package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.HashSet;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Generate the polygon for a powerup
 * 
 * @author Guillaume
 *
 */
public class PowerUpPolygonGenerator extends AbstractPolygonGenerator implements
		PolygonGenerator {

	private static final int NB_SEGMENTS = 20;

	private final int branchNumber;
	private final float branchWidth;
	private final int nbSegments;
	private final float radius;

	/**
	 * Build a new {@link PowerUpPolygonGenerator} with basic parameters and the
	 * default number of segments
	 * 
	 * @param branchNumberArg
	 *            the number of branches in the "star"
	 * @param branchWidhtArg
	 *            the width of each branch at the center of the powerup
	 */
	public PowerUpPolygonGenerator(float radiusArg, int branchNumberArg, float branchWidthArg) {
		this(radiusArg, branchNumberArg, branchWidthArg, NB_SEGMENTS);
	}

	/**
	 * Build a {@link PowerUpPolygonGenerator} with advanced parameters
	 * 
	 * @param branchNumberArg
	 *            the number of branches in the "star"
	 * @param branchWidthArg
	 *            the width of each branch at the center of the powerup
	 * @param nbSegmentsPerBranch
	 *            the number of segments in each branch of the "star"
	 */
	public PowerUpPolygonGenerator(float radiusArg, int branchNumberArg, float branchWidthArg,
			int nbSegmentsPerBranch) {
		branchNumber = branchNumberArg;
		branchWidth = branchWidthArg;
		nbSegments = nbSegmentsPerBranch;
		radius = radiusArg;
	}

	@Override
	protected float[] generate() {
		Set<PolygonGenerator> generators = new HashSet<PolygonGenerator>();
		float deltaAngle = PolygonUtils.MAX_ANGLE_RADIAN / (2 * branchNumber);
		float currentAngle = 0f;

		for (int i = 0; i < branchNumber; i++) {
			generators.add(new PolygonRotator(new PolygonScaler(
					new CircleGenerator(nbSegments),radius*branchWidth, radius),
					currentAngle));
			currentAngle += deltaAngle;
		}
		return new PolygonMerger(generators).generateVertexesFloat();
	}
}
