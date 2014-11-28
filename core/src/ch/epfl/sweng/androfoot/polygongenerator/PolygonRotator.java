/**
 * 
 */
package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Take a {@link PolygonGenerator} and rotates it
 * 
 * @author Guillaume
 *
 */
public class PolygonRotator extends AbstractPolygonGenerator implements
		PolygonGenerator {

	private final PolygonGenerator generator;
	private final double cosA;
	private final double sinA;

	public PolygonRotator(PolygonGenerator generatorArg, float angleArg) {
		generator = generatorArg;
		cosA = Math.cos(angleArg);
		sinA = Math.cos(angleArg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.epfl.sweng.androfoot.polygongenerator.AbstractPolygonGenerator#generate
	 * ()
	 */
	@Override
	protected float[] generate() {
		float[] originalPolygon = generator.generateVertexesFloat();
		int length = originalPolygon.length/2;
		float[] resultPolygon = new float[length * 2];
		for(int i = 0 ; i < length ; i++) {
			resultPolygon[2 * i] = (float) (cosA*originalPolygon[2 * i]);
			resultPolygon[2 * i + 1] = (float) (sinA*originalPolygon[2 * i + 1]);
		}
		return resultPolygon;
	}
}
