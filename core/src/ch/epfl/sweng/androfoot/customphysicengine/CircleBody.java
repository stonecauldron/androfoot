package ch.epfl.sweng.androfoot.customphysicengine;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

import com.badlogic.gdx.math.Circle;

/**
 * A body in the world with a circle shape
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class CircleBody extends AbstractBody implements ShapeBodyVisitable {

    private Circle shape;
    
    public CircleBody(Circle circle) {
        super(0, 0);
        shape = circle;
    }
    
    public Circle getShape() {
        return shape;
    }

    @Override
    public float getX() {
        return shape.x;
    }

    @Override
    public float getY() {
        return shape.y;
    }

    @Override
    public void move(float x, float y) {
        shape.setX(getX() + x);
        shape.setY(getY() + y);
    }

    @Override
    public void accept(ShapeBodyVisitor overlap) {
        overlap.visit(this);
    }
}
