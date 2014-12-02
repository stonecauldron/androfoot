package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * A class that wrap a {@link PolygonGenerator} and append a point at the end
 * of the polygon
 * 
 * @author Guillame Leclerc
 *
 */
public class AppendPointPolygonGenerator extends AbstractPolygonGenerator implements PolygonGenerator {

	private ImmutablePoint<Float> point;
	private PolygonGenerator generator;

	/**
	 * Create a {@link AppendPointPolygonGenerator} from another {@link PolygonGenerator}
	 * @param generatorArg the {@link PolygonGenerator}
	 * @param pointArg the point to append at the end of the polygon
	 */
	public AppendPointPolygonGenerator(PolygonGenerator generatorArg, ImmutablePoint<Float> pointArg) {
		point = pointArg;
		generator = generatorArg;
	}

	@Override
	protected float[] generate() {
		float[] originalVertexes = generator.generateVertexesFloat();
		float[] vertexes = new float[originalVertexes.length + 2];
		vertexes[vertexes.length - 2] = point.x;
		vertexes[vertexes.length - 1] = point.y;
		System.arraycopy(originalVertexes, 0, vertexes, 0, originalVertexes.length);
		return vertexes;
	}

}
