package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.Arrays;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

public class PolygonTranslater<T extends PolygonGenerator> extends AbstractPolygonGenerator implements
	PolygonGenerator {

	private final T generator;
	private final float[] vertexes;
	private final float dX;
	private final float dY;
	private boolean isGenerated;
	
	
	public PolygonTranslater(T generatorArg, float dx, float dy) {
		generator = generatorArg;
		int size = generator.generateVertexesFloat().length;
		vertexes = Arrays.copyOf(generator.generateVertexesFloat(), size);
		dX = dx;
		dY = dy;
	}

	@Override
	protected float[] generate() {
		if(isGenerated) {
			return vertexes;
		}
		
		for(int i = 0 ; i < vertexes.length /2 ; i++) {
			vertexes[2*i] += dX;
			vertexes[2*i + 1] += dY;
		}

		isGenerated = true;
		return vertexes;
	}

}
