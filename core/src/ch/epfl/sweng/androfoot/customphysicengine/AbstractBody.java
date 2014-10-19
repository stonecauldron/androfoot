package ch.epfl.sweng.androfoot.customphysicengine;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

/**
 * A body is a basic object in the physic engine.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public abstract class AbstractBody implements Body {
    
    /**
     * Describe how the body interact with the world
     * @author Gaylor
     *
     */
    public enum MovementType { Static, Dynamic, Kynetic };
    
    private float velocityX;
    private float velocityY;
    private MovementType type;
    
    public AbstractBody(float vx, float vy) {
        velocityX = vx;
        velocityY = vy;
        
        if (vx != 0 || vy != 0) {
            type = MovementType.Dynamic;
        } else {
            type = MovementType.Static;
        }
    }
    
    /**
     * @see Body.getX
     */
    public abstract float getX();
    
    /**
     * @see Body.getY
     */
    public abstract float getY();
    
    /**
     * @see Body.getVelocityX
     */
    public float getVelocityX() {
        return velocityX;
    }
    
    /**
     * @see Body.getVelocityY
     */
    public float getVelocityY() {
        return velocityY;
    }
    
    public void setVelocityX(float velocity) {
        velocityX = velocity;
    }
    
    public void setVelocityY(float velocity) {
        velocityY = velocity;
    }
    
    public boolean isStatic() {
        return type == MovementType.Static;
    }
    
    public boolean isDynamic() {
        return type == MovementType.Dynamic;
    }
    
    public boolean isKynetic() {
        return type == MovementType.Kynetic;
    }
    
    /**
     * @see Body.move
     */
    public abstract void move(float x, float y);
    
    /**
     * @see Body.accept
     */
    public abstract void accept(ShapeBodyVisitor overlap);
}
