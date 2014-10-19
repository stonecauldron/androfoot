package ch.epfl.sweng.androfoot.customphysicengine;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

import com.badlogic.gdx.math.Rectangle;

/**
 * A body in the world with a rectangle shape
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class RectangleBody extends AbstractBody {
    
    private Rectangle shape;
    
    public RectangleBody(Rectangle rectangle) {
        super(0, 0);
        shape = rectangle;
    }
    
    public RectangleBody(Rectangle rectangle, float velocityX, float velocityY) {
        super(velocityX, velocityY);
        shape = rectangle;
    }
    
    public Rectangle getShape() {
        return shape;
    }

    @Override
    public float getX() {
        return shape.getX();
    }

    @Override
    public float getY() {
        return shape.getY();
    }
    
    @Override
    public void move(float x, float y) {
        shape.setX(shape.getX() + x);
        shape.setY(shape.getY() + y);
    }

    @Override
    public void accept(ShapeBodyVisitor overlap) {
        overlap.visit(this);
    }
}
