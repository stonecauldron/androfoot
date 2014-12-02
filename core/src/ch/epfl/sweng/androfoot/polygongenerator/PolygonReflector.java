package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Wrap un {@link PolygonGenerator} and expose a reflected version of the polygon
 * @author Guillame Leclerc
 *
 */
public class PolygonReflector extends AbstractPolygonGenerator implements PolygonGenerator {

	/**
	 * Represent the different axis in two dimensions
	 * @author Guillame Leclerc
	 *
	 */
	public enum Axis2D {
		x, y
	}

	private final Axis2D axis;
	private final PolygonGenerator generator;

	/**
	 * Generate a {@link PolygonGenerator} reflected from another {@link PolygonGenerator}
	 * @param generatorArg the {@link PolygonGenerator}
	 * @param axisArg the axis to do the reflexion
	 */
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
			vertexes[2 * i + offset] *= -1;
		}
		return vertexes;
	}
}
