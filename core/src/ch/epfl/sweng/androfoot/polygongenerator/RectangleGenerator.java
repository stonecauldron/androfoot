package ch.epfl.sweng.androfoot.polygongenerator;

import com.badlogic.gdx.math.Rectangle;

/**
 * Generate a rectangle
 * @author Guillame Leclerc
 *
 */
public class RectangleGenerator extends AbstractPolygonGenerator {
	
	private final static int NB_VERTEX_COORDS_RECTANGLE = 8;
	
	private final Rectangle rectangle;
	private final float[] vertexes;
	private boolean isGenerated;
		
	
	/**
	 * Build a Rectangle generator from a Rectangle Object
	 * @param rectangleArg the rectangle to build the polygon for
	 */
	public RectangleGenerator(Rectangle rectangleArg) {
		rectangle = new Rectangle(rectangleArg);
		isGenerated = false;
		vertexes = new float[NB_VERTEX_COORDS_RECTANGLE + 2];
	}


	@Override
	protected float[] generate() {
		if(isGenerated) {
			return vertexes;
		}
		vertexes[0] = rectangle.x;
		vertexes[1] = rectangle.y;
		vertexes[2] = vertexes[0]+ rectangle.width;
		vertexes[3] = vertexes[1];
		vertexes[4] = vertexes[2];
		vertexes[5] = vertexes[3] - rectangle.height;
		vertexes[6] = vertexes[0];
		vertexes[7] = vertexes[5];
		
		vertexes[vertexes.length-2] = vertexes[0];
		vertexes[vertexes.length-1] = vertexes[1];
		
		isGenerated = true;
		return vertexes;
	}
}
