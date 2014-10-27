package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * A Polygon from an array of vertexes
 * There are not any properties on this polygon
 * @author Guillame Leclerc
 *
 */
public class RawPolygonGenerator extends AbstractPolygonGenerator {
	
	private final float[] vertexes;
	
	/**
	 * Create the {@link RawPolygonGenerator}
	 * @param vertexesArg the list of vertexes coordinates (x coords with even indexes and Y with odd indexes)
	 */
	public RawPolygonGenerator(float[] vertexesArg) {
		vertexes = vertexesArg;
	}

	@Override
	protected float[] generate() {
		return vertexes;
	}

}
