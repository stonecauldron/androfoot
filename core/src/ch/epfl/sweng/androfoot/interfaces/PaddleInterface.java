package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represents an paddle alone.
 * @see GroupPaddleInterface for multiple paddles group
 * 
 * The position of a paddle is the bottom left vertex
 * 
 * UI gives it a velocity and the paddle doesn't go out of a certain area defined by a limit rectangle
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface PaddleInterface {
    
    /**
     * Give a velocity to the paddle
     * @param x composant of the velocity
     * @param y composant of the velocity
     */
    void setPlayerVelocity(float x, float y);
    
    /**
     * Sets the player velocity along the x axis only.
     * @param x New velocity along the x axis.
     */
    void setPlayerXVelocity(float x);
    
    /**
     * Sets the player velocity along the y axis only.
     * @param y New velocity along the y axis.
     */
    void setPlayerYVelocity(float y);
    
	/**
	 * Say if this paddle is in range to control the paddle
	 * @return
	 */
	boolean isAbleToControlBall();
}
