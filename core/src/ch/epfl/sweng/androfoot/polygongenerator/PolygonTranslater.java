package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.Arrays;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * A Translated version of a {@link PolygonGenerator}
 * 
 * @author Guillame Leclerc
 *
 */
public class PolygonTranslater extends AbstractPolygonGenerator implements PolygonGenerator {

	private final PolygonGenerator generator;
	private final float[] vertexes;
	private final float dX;
	private final float dY;
	private boolean isGenerated;

	/**
	 * Return a new {@link PolygonGenerator} that is a translated version of the
	 * argument
	 * 
	 * @param generatorArg
	 * @param dx
	 *            displacement on X axis
	 * @param dy
	 *            displacement on Y axis
	 */
	public PolygonTranslater(PolygonGenerator generatorArg, float dx, float dy) {
		generator = generatorArg;
		int size = generator.generateVertexesFloat().length;
		vertexes = Arrays.copyOf(generator.generateVertexesFloat(), size);
		dX = dx;
		dY = dy;
	}

	@Override
	protected float[] generate() {
		if (isGenerated) {
			return vertexes;
		}

		for (int i = 0; i < vertexes.length / 2; i++) {
			vertexes[2 * i] += dX;
			vertexes[2 * i + 1] += dY;
		}

		isGenerated = true;
		return vertexes;
	}

}
