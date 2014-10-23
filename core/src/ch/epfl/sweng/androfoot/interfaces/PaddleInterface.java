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
    void setVelocity(float x, float y);
}
