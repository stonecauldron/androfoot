package ch.epfl.sweng.androfoot.customphysicengine.interfaces;

import java.util.List;

import ch.epfl.sweng.androfoot.customphysicengine.CircleBody;
import ch.epfl.sweng.androfoot.customphysicengine.RectangleBody;

/**
 * Visitor pattern to manage the different shape
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public interface ShapeBodyVisitor {

    void visit(RectangleBody body);
    
    void visit(CircleBody body);
    
    void visit(List<ShapeBodyVisitable> bodies);
}
