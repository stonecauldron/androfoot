package ch.epfl.sweng.androfoot.polygongenerator;

import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * A {@link PolygonGenerator} that is the result of the union of multiple
 * {@link PolygonGenerator}
 * 
 * @author Guillaume
 *
 */
public class PolygonMerger extends AbstractPolygonGenerator implements
		PolygonGenerator {

	private final Set<PolygonGenerator> generators;

	/**
	 * Build a {@link PolygonGenerator} from multiple {@link PolygonGenerator}
	 * 
	 * @param generatorsArg
	 *            the {@link Set} of {@link PolygonGenerator}
	 */
	public PolygonMerger(Set<PolygonGenerator> generatorsArg) {
		generators = generatorsArg;
	}

	private int computeFinalSize() {
		int size = 0;
		for (PolygonGenerator generator : generators) {
			size += generator.generatePointsList().size();
		}
		return size;
	}

	@Override
	protected float[] generate() {
		float[] vertexes = new float[computeFinalSize() * 2];
		int globalIndex = 0;

		for (PolygonGenerator generator : generators) {
			List<ImmutablePoint<Float>> points = generator.generatePointsList();
			for (ImmutablePoint<Float> currentPoint : points) {
				vertexes[globalIndex * 2] = currentPoint.x;
				vertexes[globalIndex * 2 + 1] = currentPoint.y;
				globalIndex++;
			}
		}
		return vertexes;
	}

}
