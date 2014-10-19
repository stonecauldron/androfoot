package ch.epfl.sweng.androfoot.customphysicengine;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

/**
 * A basic body which has to interact in the world of the physic engine
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface Body extends ShapeBodyVisitable {
    
    /**
     * Get the x coordinate
     * @return
     */
    float getX();
    
    /**
     * Get the y coordinate
     * @return
     */
    float getY();
    
    /**
     * Get the x composant of the velocity
     * @return
     */
    float getVelocityX();
    
    /**
     * Get the y composant of the velocity
     * @return
     */
    float getVelocityY();
    
    /**
     * Set the x composant of the velocity
     * @param velocity
     */
    void setVelocityX(float velocity);
    
    /**
     * Set the y composant of the velocity
     * @param velocity
     */
    void setVelocityY(float velocity);
    
    /**
     * Check if the body is static
     * @return true if it's static
     */
    boolean isStatic();
    
    /**
     * Check if the body is dynamic
     * @return true if it's dynamic
     */
    boolean isDynamic();
    
    /**
     * Check if the body is kynetic
     * @return true if it's kynetic
     */
    boolean isKynetic();
    
    /**
     * Move the polygon in the specified point
     * @param x 
     * @param y 
     */
    void move(float x, float y);
    
    /**
     * Accept a visitor (Visitor pattern)
     * @param overlap The visitor
     */
    void accept(ShapeBodyVisitor overlap);
}
