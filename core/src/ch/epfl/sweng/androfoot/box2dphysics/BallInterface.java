package ch.epfl.sweng.androfoot.box2dphysics;

/**
 * Represents the ball object.
 * @author Matvey
 *
 */
public interface BallInterface {

	/**
	 * Recovers the x position of the ball.
	 */
	public float getPositionX();
	
	/**
	 * Recovers the y position of the ball.
	 */
	public float getPositionY();
	
	/**
	 * Recovers the radius of the ball.
	 */
	public float getRadius();
}
