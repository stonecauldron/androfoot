package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.Arrays;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Scale a PolygonGenerator
 * @author Guillame Leclerc
 *
 * @param <T> the type of Generator to scale
 */
public class PolygonScaler<T extends PolygonGenerator> extends AbstractPolygonGenerator implements
		PolygonGenerator {
	
	private final T generator;
	private final float[] vertexes;
	private final float scaleX;
	private final float scaleY;
	private boolean isGenerated;
	
	/**
	 * Return a new {@link PolygonGenerator} that is a scaled version of the argument
	 * @param generatorArg
	 * @param scale the scale (same on X and Y axis)
	 */
	public PolygonScaler(T generatorArg, float scale) {
		this(generatorArg, scale, scale);
	}
	
	/**
	 * Return a new {@link PolygonGenerator} that is a scaled version of the argument
	 * @param generatorArg
	 * @param scaleXArg the scale on X axis
	 * @param scaleYArg the scale on Y axis
	 */
	public PolygonScaler(T generatorArg, float scaleXArg, float scaleYArg) {
		generator = generatorArg;
		int size = generator.generateVertexesFloat().length;
		vertexes = Arrays.copyOf(generator.generateVertexesFloat(), size);
		scaleX = scaleXArg;
		scaleY = scaleYArg;
	}

	@Override
	protected float[] generate() {
		if(isGenerated) {
			return vertexes;
		}
		
		for(int i = 0 ; i < vertexes.length /2 ; i++) {
			vertexes[2*i] *= scaleX;
			vertexes[2*i + 1] *= scaleY;
		}

		isGenerated = true;
		return vertexes;
	}

}
