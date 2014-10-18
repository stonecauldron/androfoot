package ch.epfl.sweng.androfoot.customphysicengine;

import com.badlogic.gdx.math.Polygon;

/**
 * A body is a basic object in the physic engine.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public abstract class AbstractBody implements Body {
    private final Polygon shape;
    
    public AbstractBody(Polygon polygon) {
        shape = polygon;
    }
    
    /**
     * @see Body.getX
     */
    public float getX() {
        return shape.getX();
    }
    
    /**
     * @see Body.getY
     */
    public float getY() {
        return shape.getY();
    }
    
    /**
     * @see Body.getPolygon
     */
    public Polygon getPolygon() {
        return shape;
    }
    
    /**
     * @see Body.move
     */
    public void move(float x, float y) {
        shape.setPosition(shape.getX()+x, shape.getY()+y);
    }
}
