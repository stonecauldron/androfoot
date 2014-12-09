package ch.epfl.sweng.androfoot.interfaces;

import ch.epfl.sweng.androfoot.box2dphysics.ImmutableBall;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents the ball object.
 * @author Matvey
 *
 */
public interface DefaultBall extends Visitable, DefaultWorldObject {

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
	
	void changeFixture(float newRadius, float newDensity, float newFriction, float newRestitution);
	void changeFixture(float newRadius);
	
	/**
	 * Get the velocity of the ball.
	 */
	Vector2 getLinearVelocity();
	
	/**
	 * Clone the actual state of the ball
	 * @return
	 */
	ImmutableBall getStates();
}
