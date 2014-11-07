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
	float getPositionX();
	
	/**
	 * Recovers the y position of the ball.
	 */
	float getPositionY();
	
	/**
	 * Recovers the radius of the ball.
	 */
	float getRadius();
	
	/**
	 * Sets the position of the ball.
	 */
	void setBallPosition(float x, float y);
	
	/**
	 * Set the velocity of the ball
	 */
	void setLinearVelocity(float x, float y);
}
