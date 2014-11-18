package ch.epfl.sweng.androfoot.polygongenerator.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import ch.epfl.sweng.androfoot.polygongenerator.RawPolygonGenerator;

public class RawPolygonGeneratorTest {
	private final static int NB_VERTEXES = 100;
	private final static float DELTA = 0.0001f;

	@Test
	public void test() {
		float[] vertexes = new float[NB_VERTEXES * 2];
		Random randomizer = new Random();

		for (int i = 0; i < NB_VERTEXES * 2; i++) {
			vertexes[i] = randomizer.nextFloat();
		}

		assertArrayEquals("the output vertexes are not the same", vertexes,
				new RawPolygonGenerator(vertexes).generateVertexesFloat(), DELTA);

	}

}
