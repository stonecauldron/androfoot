package ch.epfl.sweng.androfoot.polygongenerator;

public class RawPolygonGenerator extends AbstractPolygonGenerator {
	
	private final float[] vertexes;
	
	public RawPolygonGenerator(float[] vertexesArg) {
		vertexes = vertexesArg;
	}

	@Override
	protected float[] generate() {
		return vertexes;
	}

}
