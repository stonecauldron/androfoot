package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class NonCiclicPolygon extends AbstractPolygonGenerator {
	
	private final float[] finalValues;

	public NonCiclicPolygon(PolygonGenerator generator) {
		float[] values = generator.generateVertexesFloat();
		finalValues = new float[values.length - 2];
		System.arraycopy(values, 0, finalValues, 0, finalValues.length);
	}

	@Override
	protected float[] generate() {
		return finalValues;
	}

}
