package ch.epfl.sweng.androfoot.customphysicengine;

import java.util.List;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

/**
 * Check if two bodies are overlapsed
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class OverlapVisitor implements ShapeBodyVisitor {
    
    private Rectangle rectangle;
    private Circle circle;
    private boolean isOverlapsed;
    
    public OverlapVisitor() {
        rectangle = null;
        circle = null;
        isOverlapsed = false;
    }
    
    public boolean isOverlapsed() {
        return isOverlapsed;
    }

    @Override
    public void visit(RectangleBody body) {
        if (rectangle == null && circle == null) {
            
            rectangle = body.getShape();
        } else if (rectangle != null) {
            
            isOverlapsed = Intersector.overlaps(rectangle, body.getShape());
        } else {
            
            isOverlapsed = Intersector.overlaps(body.getShape(), rectangle);
        }
    }

    @Override
    public void visit(CircleBody body) {
        if (rectangle == null && circle == null) {
            
            circle = body.getShape();
        } else if (rectangle != null) {
            
            isOverlapsed = Intersector.overlaps(body.getShape(), rectangle);
        } else {
            
            isOverlapsed = Intersector.overlaps(circle, body.getShape());
        }
    }

    @Override
    public void visit(List<ShapeBodyVisitable> bodies) {
        rectangle = null;
        circle = null;
        
        if (bodies.size() != 2) {
            throw new IllegalArgumentException("The list must have two elements");
        } else {
            bodies.get(0).accept(this);
            bodies.get(1).accept(this);
        }
    }

}
