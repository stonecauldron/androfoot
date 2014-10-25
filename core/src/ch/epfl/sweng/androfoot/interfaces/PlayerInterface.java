package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represents an individual player on the field.
 * @author Matvey
 *
 */
public interface PlayerInterface extends Visitable {

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
	 * Modifies the velocity of the player.
	 * @param x x component of the velocity vector.
	 * @param y y component of the velocity vector.
	 */
	void setPlayerVelocity(float x, float y);
}