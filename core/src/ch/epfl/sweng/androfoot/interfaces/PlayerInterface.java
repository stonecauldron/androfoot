package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Represents an individual player on the field.
 * @author Matvey
 *
 */
public interface PlayerInterface extends Drawable {

	/**
	 * Recovers the x position of the player.
	 * @return
	 */
	float getPositionX();
	
	/**
	 * Recovers the y position of the player.
	 * @return
	 */
	float getPositionY();
	
	/**
	 * Recovers the world rotation angle of the player in radians.
	 * @return The world rotation angle of the player in radians.
	 */
	float getPlayerAngle();
	
	/**
	 * Gets the player's team. If true the player belongs to team 1, else team 2.
	 */
	boolean getTeam();
	
	/**
	 * Gets the physical body of the player
	 */
	Body getBody();
	
	/**
	 * Modifies the velocity of the player.
	 * @param x x component of the velocity vector.
	 * @param y y component of the velocity vector.
	 */
	void setPlayerVelocity(float x, float y);
}
