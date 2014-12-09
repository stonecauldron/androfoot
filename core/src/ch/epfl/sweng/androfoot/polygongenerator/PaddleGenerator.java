package ch.epfl.sweng.androfoot.polygongenerator;

import com.badlogic.gdx.math.Rectangle;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Generate a polygon that represent a paddle all the subpolygons are cyclic,
 * that means the last vertex is the same as the first one the polygon istelf is
 * not cyclic
 * 
 * @author Guillame Leclerc
 *
 */
public class PaddleGenerator extends PolygonPack {
	/**
	 * The key to get the subpolygon which represent the control zone
	 */
	public static final String CONTROL_BLOCK_KEY = "controller";
	/**
	 * The key to get the subpolygon which represent the shooting zone
	 */
	public static final String SHOOT_BLOCK_KEY = "shoot";

	private final static int NB_SEGMENT_CIRCLE = 50;

	private final float currWidth;
	private final float currHeightCircle;
	private final float currHeightRectangle;

	/**
	 * Create a generator for a paddle with full configuration
	 * 
	 * @param width
	 *            the width of the paddle
	 * @param heightCircle
	 *            the height of the circle part
	 * @param heightRectangle
	 *            the height of the rectangle part
	 */
	protected PaddleGenerator(float width, float heightCircle, float heightRectangle, int nbCircleSegments) {
		float displacementY = (heightCircle + heightRectangle) / 2 - heightCircle;
		PolygonGenerator circleGenerator = new PolygonTranslater(new PolygonScaler(new CircleGenerator(
						nbCircleSegments, 0f, (float) Math.PI, AngleType.RADIAN), width / 2, heightCircle)
						, 0f, displacementY);
		Rectangle rectangle = new Rectangle(-width / 2, displacementY - heightRectangle, width, heightRectangle);
		RectangleGenerator rectangleGenerator = new RectangleGenerator(rectangle);
		this.add(SHOOT_BLOCK_KEY, circleGenerator);
		this.add(CONTROL_BLOCK_KEY, rectangleGenerator);
		currHeightCircle = heightCircle;
		currHeightRectangle = heightRectangle;
		currWidth = width;
	}

	/**
	 * Create a {@link PaddleGenerator} with default number of segment in the
	 * circle part
	 * 
	 * @param width
	 * @param heightCircle
	 * @param heightRectangle
	 */
	public PaddleGenerator(float width, float heightCircle, float heightRectangle) {
		this(width, heightCircle, heightRectangle, NB_SEGMENT_CIRCLE);
	}

	/**
	 * Create a copy of a {@link PaddleGenerator} but with a different number of
	 * segments in the circle part
	 * 
	 * @param paddleGen
	 *            the {@link PaddleGenerator} to copy
	 * @param nbCircleSegments
	 *            the number of segments in the circle part
	 */
	protected PaddleGenerator(PaddleGenerator paddleGen, int nbCircleSegments) {
		this(paddleGen.currWidth, paddleGen.currHeightCircle, paddleGen.currHeightRectangle, nbCircleSegments);
	}
}
