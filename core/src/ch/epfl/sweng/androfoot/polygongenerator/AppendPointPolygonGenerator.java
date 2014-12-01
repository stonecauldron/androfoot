package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class AppendPointPolygonGenerator extends AbstractPolygonGenerator
		implements PolygonGenerator {

	ImmutablePoint<Float> point;
	PolygonGenerator generator;
	
	public AppendPointPolygonGenerator(PolygonGenerator generatorArg, ImmutablePoint<Float> pointArg) {
		point = pointArg;
		generator=generatorArg;
	}
	
	@Override
	protected float[] generate() {
		float[] originalVertexes = generator.generateVertexesFloat();
		float[] vertexes = new float[originalVertexes.length+2];
		vertexes[vertexes.length-2] = point.x;
		vertexes[vertexes.length-1] = point.y;
		System.arraycopy(originalVertexes, 0, vertexes, 0, originalVertexes.length);
		return vertexes;
	}

}
