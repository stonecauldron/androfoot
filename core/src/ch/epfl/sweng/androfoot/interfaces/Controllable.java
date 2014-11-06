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
	public void moveHorizontally(float deltaX);
	
	/**
	 * Move vertically in function of deltaY
	 * @param deltaY variation of position on the y axis
	 */
	public void moveVertically(float deltaY);
}
