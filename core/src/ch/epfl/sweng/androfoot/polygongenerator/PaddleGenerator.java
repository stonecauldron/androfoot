package ch.epfl.sweng.androfoot.polygongenerator;

import com.badlogic.gdx.math.Rectangle;

import ch.epfl.sweng.androfoot.interfaces.PolygonGenerator;

/**
 * Generate a polygon that represent a paddle
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
	
	private final static int NB_SEGMENT_CIRCLE = 6;
	
	/**
	 * Create a generator for a paddle with basic configuration
	 * @param width the width of the paddle
	 * @param heightCircle the height of the circle part
	 * @param heightRectangle the height of the rectangle part
	 */
	public PaddleGenerator(float width, float heightCircle, float heightRectangle) {
		float displacementY = (heightCircle + heightRectangle)/2 - heightCircle;
		System.out.println(displacementY);
		//displacementY = 1;
		PolygonGenerator circleGenerator = new PolygonTranslater(
				new PolygonScaler(
						new CircleGenerator(NB_SEGMENT_CIRCLE, 0f, (float)Math.PI, AngleType.RADIAN),
						width/2, heightCircle),
				0f, displacementY);
		Rectangle rectangle = new Rectangle(-width/2,displacementY, width, heightRectangle);
		RectangleGenerator rectangleGenerator = new RectangleGenerator(rectangle);
		this.add(SHOOT_BLOCK_KEY, circleGenerator);
		this.add(CONTROL_BLOCK_KEY, rectangleGenerator);
	}
}
