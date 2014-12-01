package ch.epfl.sweng.androfoot.polygongenerator;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class PrependPointPolygonGenerator extends AbstractPolygonGenerator
		implements PolygonGenerator {
	
	ImmutablePoint<Float> point;
	PolygonGenerator generator;
	
	public PrependPointPolygonGenerator(PolygonGenerator generatorArg, ImmutablePoint<Float> pointArg) {
		point = pointArg;
		generator=generatorArg;
	}

	@Override
	protected float[] generate() {
		float[] originalVertexes = generator.generateVertexesFloat();
		float[] vertexes = new float[originalVertexes.length+2];
		vertexes[0] = point.x;
		vertexes[1] = point.y;
		System.arraycopy(originalVertexes, 0, vertexes, 2, originalVertexes.length);
		return vertexes;
	}

}