package ch.epfl.sweng.androfoot.interfaces;

/**
 * Defines what actions a player can do.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public interface Controllable {
	
	/**
	 * Move horizontally in function of deltaX
	 * @param deltaX variation of position on the x axis
	 */
	void moveHorizontally(float deltaX);
	
	/**
	 * Move vertically in function of deltaY
	 * @param deltaY variation of position on the y axis
	 */
	void moveVertically(float deltaY);
	

	/**
	 * Move vertically in function of deltaY
	 * @param deltaX variation of position on the x axis
	 * @param deltaY variation of position on the y axis
	 */
	void move(float deltaX, float deltaY);
}
