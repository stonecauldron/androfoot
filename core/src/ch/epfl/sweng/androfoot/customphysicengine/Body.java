package ch.epfl.sweng.androfoot.customphysicengine;

import com.badlogic.gdx.math.Polygon;

/**
 * A basic body which has to interact in the world of the physic engine
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface Body {
    
    /**
     * Get the x coordinate of the first vertice of the polygon
     * @return x coordinate
     */
    float getX();
    
    /**
     * Get the y coordinate of the first vertice of the polygon
     * @return y coordinate
     */
    float getY();
    
    /**
     * Get the shape of the body
     * @return the polygon
     */
    Polygon getPolygon();
    
    /**
     * Move the polygon in the specified point
     * @param x new coordinate of the first vertice of the polygon
     * @param y new coordinate of the first vertice of the polygon
     */
    void move(float x, float y);
}
