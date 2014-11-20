package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represents a set of paddle which move together
 * 
 * Only the position x is settable, because the position y of 
 * the paddles are calculated among the world height
 * 
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface GroupPaddleInterface {
    
    /**
     * Set the velocity of all paddle inside the group
     * @param x composant of the velocity
     * @param y composant of the velocity
     */
    void setVelocity(float x, float y);
    
    void setXVelocity(float x);
    
    void setYVelocity(float y);
}
