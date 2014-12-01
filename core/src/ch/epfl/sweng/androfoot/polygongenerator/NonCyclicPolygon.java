package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Represent a polygon which is not cyclic That means the last point is not
 * necessarily the same as the first one It is useful for optimization purpose
 * because box2d limits the number of vertexes in a shape
 * 
 * @author Guillame Leclerc
 *
 */
public class NonCyclicPolygon extends AbstractPolygonGenerator {

	private static final float DELTA = 0.0001f;

	private final float[] finalValues;

	/**
	 * builds a non cyclic polygon from a cyclic one if the last point and the
	 * first one are the same it generate a clone of the given
	 * {@link PolygonGenerator}
	 * 
	 * @param generator
	 *            a cyclic polygon
	 */
	public NonCyclicPolygon(PolygonGenerator generator) {
		float[] values = generator.generateVertexesFloat();
		if (values.length < 4) {
			finalValues = values;
		} else {

			// remove the last point only if it is the same as the first one
			if (Math.abs(values[0] - values[values.length - 2]) < DELTA
					&& Math.abs(values[1] - values[values.length - 1]) < DELTA) {
				finalValues = new float[values.length - 2];
			} else {
				finalValues = new float[values.length];
			}

			System.arraycopy(values, 0, finalValues, 0, finalValues.length);
		}
	}

	@Override
	protected float[] generate() {
		return finalValues;
	}

}
