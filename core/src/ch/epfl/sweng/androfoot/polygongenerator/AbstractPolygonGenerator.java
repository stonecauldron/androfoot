package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Implements some generic methods of PolygonGenerator
 * @author Guillame Leclerc
 *
 */
public abstract class AbstractPolygonGenerator implements PolygonGenerator {

	/**
	 * generate the polygon and return the vertexes
	 * @return an array containing the vertexes in 2D (even indexes for X coords and odd for Y coords)
	 */
	protected abstract float[] generate();

	@Override
	public float[] generateVertexesFloat() {
		return generate();
	}

	@Override
	public float[] generateVertexesFloatInZPlane(float z) {
		float[] vertexes = generate();
		int segments = vertexes.length/2 - 1;
		float[] vertexes3D = new float[(segments + 1) * 3];
		for (int i = 0 ; i <= segments ; i++){
			vertexes3D[3*i] = vertexes[2*i];
			vertexes3D[3*i + 1] = vertexes[2*i + 1];
			vertexes3D[3*i + 2] = z;
		}
		
		return vertexes3D;
	}

	@Override
	public List<ImmutablePoint<Float>> generatePointsList() {
		float[] vertexes = generate();
		int segments = vertexes.length/2 - 1;
		ArrayList<ImmutablePoint<Float>> result = new ArrayList<ImmutablePoint<Float>>();
		for (int i = 0 ; i <= segments ; i ++) {
			result.add(new ImmutablePoint<Float>(vertexes[2*i],vertexes[2*i + 1]));
		}

		return result;
	}
}
