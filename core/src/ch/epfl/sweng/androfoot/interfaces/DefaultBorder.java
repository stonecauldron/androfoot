package ch.epfl.sweng.androfoot.interfaces;

import com.badlogic.gdx.math.Vector2;

import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.ImmutableBorder;

/**
 * Represent a border in the world
 * @author Gaylor
 *
 */
public interface DefaultBorder extends DefaultWorldObject {
    
    /**
     * Change the width of the border
     * @param value
     */
    void setWidth(float value);
    
    /**
     * Change the height of the border
     * @param value
     */
    void setHeight(float value);
    
    /**
     * Change the position of the border
     * @param position
     */
    void setPosition(Vector2 position);
    
    /**
     * Return the width of the border
     * @return
     */
    float getWidth();
    
    /**
     * Return the height of the border
     * @return
     */
    float getHeight();
    
    /**
     * Return the team of the border
     * @return
     */
    BorderType getType();
    
    /**
     * Return the position of the border
     * @return
     */
    Vector2 getPosition();
    
    /**
     * Return the states in an immutable object
     * @return
     */
    ImmutableBorder getStates();
}
