package ch.epfl.sweng.androfoot.customphysicengine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.epfl.sweng.androfoot.customphysicengine.Body;
import ch.epfl.sweng.androfoot.customphysicengine.OverlapVisitor;
import ch.epfl.sweng.androfoot.customphysicengine.RectangleBody;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;

import com.badlogic.gdx.math.Rectangle;

public class OverlapVisitorTest {

    @Test
    public void overlapsTwoRectangleTest() {
        Rectangle rectUn = new Rectangle(0, 0, 50, 50);
        Body bodyUn = new RectangleBody(rectUn);
        
        Rectangle rectDeux = new Rectangle(45, 45, 50, 50);
        Body bodyDeux = new RectangleBody(rectDeux);
        
        List<ShapeBodyVisitable> bodies = new ArrayList<ShapeBodyVisitable>();
        bodies.add(bodyUn);
        bodies.add(bodyDeux);
        
        OverlapVisitor overlap = new OverlapVisitor();
        overlap.visit(bodies);
        assertTrue(overlap.isOverlapsed());
    }
    
    @Test
    public void notOverlapsTwoRectangleTest() {
        Rectangle rectUn = new Rectangle(0, 0, 50, 50);
        Body bodyUn = new RectangleBody(rectUn);
        
        Rectangle rectDeux = new Rectangle(55, 55, 50, 50);
        Body bodyDeux = new RectangleBody(rectDeux);
        
        List<ShapeBodyVisitable> bodies = new ArrayList<ShapeBodyVisitable>();
        bodies.add(bodyUn);
        bodies.add(bodyDeux);
        
        OverlapVisitor overlap = new OverlapVisitor();
        overlap.visit(bodies);
        assertFalse(overlap.isOverlapsed());
    }

}
