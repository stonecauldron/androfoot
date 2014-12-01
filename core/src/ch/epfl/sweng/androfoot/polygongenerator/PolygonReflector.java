package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class PolygonReflector extends AbstractPolygonGenerator implements
		PolygonGenerator {

	public enum Axis2D {
		x, y
	}

	private final Axis2D axis;
	private final PolygonGenerator generator;

	public PolygonReflector(PolygonGenerator generatorArg, Axis2D axisArg) {
		axis = axisArg;
		generator = generatorArg;
	}

	@Override
	protected float[] generate() {
		float[] vertexes = generator.generateVertexesFloat();
		int offset = 0;
		if (axis == Axis2D.y) {
			offset = 1;
		}
		for (int i = 0; i < vertexes.length / 2; i++) {
			vertexes[2*i + offset] *= -1;
		}
		return vertexes;
	}

}
