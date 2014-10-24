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
	
	
}
