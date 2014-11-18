/**
 * 
 */
package ch.epfl.sweng.androfoot.polygongenerator.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import ch.epfl.sweng.androfoot.polygongenerator.ImmutablePoint;
import ch.epfl.sweng.androfoot.polygongenerator.NonCyclicPolygon;
import ch.epfl.sweng.androfoot.polygongenerator.RawPolygonGenerator;

/**
 * Test the class {@link NonCyclicPolygon}
 * 
 * @author Guillame Leclerc
 *
 */
public class NotCyclicPolygonGeneratorTest {
	private final static int NB_VERTEXES = 100;

	@Test
	public void testConstructorWithCyclic() {
		float[] vertexes = new float[2 * NB_VERTEXES + 2];
		Random randomizer = new Random();
		for (int i = 0; i < NB_VERTEXES; i++) {
			vertexes[2 * i] = randomizer.nextFloat();
			vertexes[2 * i + 1] = randomizer.nextFloat();
		}
		vertexes[NB_VERTEXES * 2] = vertexes[0];
		vertexes[NB_VERTEXES * 2 + 1] = vertexes[1];

		NonCyclicPolygon generator = new NonCyclicPolygon(new RawPolygonGenerator(vertexes));
		List<ImmutablePoint<Float>> points = generator.generatePointsList();
		assertEquals("the number of vertex is not correct", NB_VERTEXES, points.size());
		assertEquals("The last point is no correct", points.get(points.size() - 1), new ImmutablePoint<Float>(
				vertexes[NB_VERTEXES * 2 - 2], vertexes[NB_VERTEXES * 2 - 1]));
	}

	@Test
	public void testConstructorWithNonCyclic() {
		float[] vertexes = new float[2 * NB_VERTEXES];
		Random randomizer = new Random();
		for (int i = 0; i < NB_VERTEXES; i++) {
			vertexes[2 * i] = randomizer.nextFloat();
			vertexes[2 * i + 1] = randomizer.nextFloat();
		}

		RawPolygonGenerator generator = new RawPolygonGenerator(vertexes);

		assertEquals("The content of the polygons are not the same", generator.generatePointsList(),
				new NonCyclicPolygon(generator).generatePointsList());

	}

}
