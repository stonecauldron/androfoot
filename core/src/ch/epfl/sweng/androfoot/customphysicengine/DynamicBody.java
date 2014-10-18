package ch.epfl.sweng.androfoot.customphysicengine;

import com.badlogic.gdx.math.Polygon;

/**
 * Those objects are movable, with a defined velocity
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class DynamicBody extends AbstractBody {
    
    private Velocity velocity;
    
    /* Constructors */

    public DynamicBody(Polygon polygon) {
        super(polygon);
        velocity = new Velocity(0, 0);
    }
    
     /* Getter */
    
    public Velocity getVelocity() {
        return velocity;
    }
    
    /* Setter */
    
    public void setVelocity(float velocityX, float velocityY) {
        velocity.setVelocityX(velocityX);
        velocity.setVelocityY(velocityY);
    }
    
    /* Inner class */
    
    /**
     * Represent the velocity of an object
     * @author Gaylor
     *
     */
    public class Velocity {
        private float x;
        private float y;
        
        public Velocity(float vx, float vy) {
            x = vx;
            y = vy;
        }
        
        public float getVelocityX() {
            return x;
        }
        
        public float getVelocityY() {
            return y;
        }
        
        public void setVelocityX(float value) {
            x = value;
        }
        
        public void setVelocityY(float value) {
            y = value;
        }
    }
}
