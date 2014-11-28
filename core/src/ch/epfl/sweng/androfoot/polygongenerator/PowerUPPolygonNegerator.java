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
public class PowerUPPolygonNegerator extends AbstractPolygonGenerator implements
		PolygonGenerator {

	private static final int NB_SEGMENTS = 20;

	private final int branchNumber;
	private final float branchWidth;
	private final int nbSegments;

	/**
	 * Build a new {@link PowerUPPolygonNegerator} with basic parameters and the
	 * default number of segments
	 * 
	 * @param branchNumberArg
	 *            the number of branches in the "star"
	 * @param branchWidhtArg
	 *            the width of each branch at the center of the powerup
	 */
	public PowerUPPolygonNegerator(int branchNumberArg, float branchWidthArg) {
		this(branchNumberArg, branchWidthArg, -1);
	}

	/**
	 * Build a {@link PowerUPPolygonNegerator} with advanced parameters
	 * 
	 * @param branchNumberArg
	 *            the number of branches in the "star"
	 * @param branchWidthArg
	 *            the width of each branch at the center of the powerup
	 * @param nbSegmentsPerBranch
	 *            the number of segments in each branch of the "star"
	 */
	public PowerUPPolygonNegerator(int branchNumberArg, float branchWidthArg,
			int nbSegmentsPerBranch) {
		branchNumber = branchNumberArg;
		branchWidth = branchWidthArg;
		nbSegments = nbSegmentsPerBranch;
	}

	@Override
	protected float[] generate() {
		Set<PolygonGenerator> generators = new HashSet<PolygonGenerator>();
		float deltaAngle = PolygonUtils.MAX_ANGLE_RADIAN / (2 * branchNumber);
		float currentAngle = 0f;

		for (int i = 0; i < branchNumber; i++) {
			generators.add(new PolygonRotator(new PolygonScaler(
					new CircleGenerator(NB_SEGMENTS), branchWidth),
					currentAngle));
			currentAngle += deltaAngle;
		}
		
		return new PolygonMerger(generators).generateVertexesFloat();

	}
}
