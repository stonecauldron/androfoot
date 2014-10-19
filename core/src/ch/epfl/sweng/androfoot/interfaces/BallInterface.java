package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represents the ball object.
 * @author Matvey
 *
 */
public interface BallInterface extends Visitable{

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
