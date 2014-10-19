package ch.epfl.sweng.androfoot.customphysicengine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;

import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitor;

/**
 * Modifiy the vector velocity of the bodies
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class UpdateVelocityVisitor implements ShapeBodyVisitor {
    
    private RectangleBody rectangle;
    private CircleBody circle;
    
    private final static int RECTANGLE_SIDES = 4;
    
    public UpdateVelocityVisitor() {
        rectangle = null;
        circle = null;
    }

    @Override
    public void visit(RectangleBody body) {
        if (rectangle == null && circle == null) {
            rectangle = body;
        } else if (rectangle != null) {
            if (rectangle.isDynamic()) {
                Segment tangent = findTangent(rectangle, body);
                
                // Rectangle can't rotate so we just check if the tangent is horizontal or vertical
                if (tangent.a.x == tangent.b.x) {
                    rectangle.setVelocityY(rectangle.getVelocityY() * (-1));
                } else {
                    rectangle.setVelocityX(rectangle.getVelocityX() * (-1));
                }
            }
        }
    }

    @Override
    public void visit(CircleBody body) {
        // TODO Auto-generated method stub
        
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
    
    public float[] getNewVelocity() {
        float[] velocity = new float[2];
        
        if (rectangle != null) {
            velocity[0] = rectangle.getVelocityX();
            velocity[1] = rectangle.getVelocityY();
        } else {
            velocity[0] = circle.getVelocityX();
            velocity[1] = circle.getVelocityY();
        }
        
        return velocity;
    }
    
    private Segment findTangent(RectangleBody dynamicRectangle, RectangleBody staticRectangle) {
        // First stage : find the vertex of dynamicRectangle in staticRectangle
        Rectangle rect = dynamicRectangle.getShape();
        List<Vector2> verticesDynamic = getVerticesRectangle(rect);
        List<Vector2> verticesStatic = getVerticesRectangle(staticRectangle.getShape());
        
        for (Vector2 vertex : verticesDynamic) {
            if (staticRectangle.getShape().contains(vertex)) {
                // Second stage : find the segment of staticRectangle which intersect with the velocity vector
                Vector2 velocityStart = new Vector2(vertex.x, vertex.y);
                Vector2 velocityEnd = new Vector2(vertex.x - dynamicRectangle.getVelocityX(), 
                        vertex.y - dynamicRectangle.getVelocityY());
                
                for (int i = 0; i < RECTANGLE_SIDES; i++) {
                    Vector2 segmentStart = new Vector2(verticesStatic.get(i));
                    Vector2 segmentEnd = new Vector2(verticesStatic.get((i + 1) % (RECTANGLE_SIDES - 1)));
                    
                    if (Intersector.intersectSegments(velocityStart, velocityEnd, segmentStart, segmentEnd, null)) {
                        
                        return new Segment(segmentStart.x, segmentStart.y, 0, segmentEnd.x, segmentEnd.y, 0);
                    }
                }
            }
        }
        
        return null;
    }
    
    private List<Vector2> getVerticesRectangle(Rectangle rect) {
        List<Vector2> vertices = new ArrayList<Vector2>();
        vertices.add(new Vector2(rect.x, rect.y));
        vertices.add(new Vector2(rect.x, rect.y + rect.height));
        vertices.add(new Vector2(rect.x + rect.width, rect.y + rect.height));
        vertices.add(new Vector2(rect.x + rect.width, rect.y));
        
        return vertices;
    }

}
