package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represents the set of players which interact together.
 * 
 * A paddle has a certain number of players which are aligned along the y axis with an x position defined in parameters
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
    public void setVelocity(float x, float y);
}
