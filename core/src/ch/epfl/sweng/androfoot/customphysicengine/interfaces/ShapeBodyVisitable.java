package ch.epfl.sweng.androfoot.customphysicengine.interfaces;

/**
 * Visitor pattern : visitable part
 * @author Gilthoniel (Gaylor Bosson)
 * @see ShapeBodyVisitor
 *
 */
public interface ShapeBodyVisitable {
    
    void accept(ShapeBodyVisitor overlap);
}
