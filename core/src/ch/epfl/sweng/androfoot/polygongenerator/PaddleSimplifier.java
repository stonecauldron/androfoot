package ch.epfl.sweng.androfoot.polygongenerator;

/**
 * A class reduce the number of polygons of a {@link PaddleGenerator} but
 * preserve the shape and size The polygon itself and the subpolygons are
 * non-cyclic which means the last vertex is not the same as the first one
 * {@link PaddleSimplifier} and {@link PaddleGenerator} has the same amount of
 * subpolygons and names Their behavior are similar. This class cannot extends
 * {@link PaddleGenerator} because it has not the same properties (acyclic vs
 * cyclic)
 * 
 * @author Guillame Leclerc
 *
 */
public class PaddleSimplifier extends PolygonPack {

	private final static int MINIMUM_VERTEX_NUMBER = 7;
	private final static int NUMBER_VERTEX_RECTANGLE = 4;

	/**
	 * Creates a simplified version of the paddle (less vertexes) the number of
	 * vertexes must be greater than 6 because it must have the same shape
	 * 
	 * @param paddle
	 *            the paddle to simplify
	 * @param nbVertexes
	 *            the new number of vertexes.
	 */
	public PaddleSimplifier(PaddleGenerator paddle, int nbVertexes) {
		if (nbVertexes < MINIMUM_VERTEX_NUMBER) {
			throw new AssertionError("cannot be less than 7 vertex");
		}
		int nbVertexCircle = nbVertexes - NUMBER_VERTEX_RECTANGLE;

		PaddleGenerator outPadGen = new PaddleGenerator(paddle, nbVertexCircle);
		add(PaddleGenerator.CONTROL_BLOCK_KEY, new NonCyclicPolygon(outPadGen.get(PaddleGenerator.CONTROL_BLOCK_KEY)));
		add(PaddleGenerator.SHOOT_BLOCK_KEY, new NonCyclicPolygon(outPadGen.get(PaddleGenerator.SHOOT_BLOCK_KEY)));
	}

}
