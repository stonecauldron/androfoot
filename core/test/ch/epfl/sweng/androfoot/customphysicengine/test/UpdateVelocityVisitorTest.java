package ch.epfl.sweng.androfoot.customphysicengine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.epfl.sweng.androfoot.customphysicengine.Body;
import ch.epfl.sweng.androfoot.customphysicengine.RectangleBody;
import ch.epfl.sweng.androfoot.customphysicengine.UpdateVelocityVisitor;
import ch.epfl.sweng.androfoot.customphysicengine.interfaces.ShapeBodyVisitable;

import com.badlogic.gdx.math.Rectangle;

public class UpdateVelocityVisitorTest {

    @Test
    public void updateVelocityRectangleToRectangleTest() {
        Rectangle rectUn = new Rectangle(0, 0, 50, 50);
        Body bodyUn = new RectangleBody(rectUn, 5, 10);
        
        Rectangle rectDeux = new Rectangle(0, 45, 50, 50);
        Body bodyDeux = new RectangleBody(rectDeux);
        
        List<ShapeBodyVisitable> bodies = new ArrayList<ShapeBodyVisitable>();
        bodies.add(bodyUn);
        bodies.add(bodyDeux);
        
        UpdateVelocityVisitor updator = new UpdateVelocityVisitor();
        updator.visit(bodies);
        
        assertEquals(-10, updator.getNewVelocity()[1], 0);
        assertEquals(5, updator.getNewVelocity()[0], 0);
    }
    
    @Test
    public void updateVelocityRectangleToRectangleDeuxTest() {
        Rectangle rectUn = new Rectangle(45, 45, 50, 50);
        Body bodyUn = new RectangleBody(rectUn, -15, -5);
        
        Rectangle rectDeux = new Rectangle(0, 0, 50, 50);
        Body bodyDeux = new RectangleBody(rectDeux);
        
        List<ShapeBodyVisitable> bodies = new ArrayList<ShapeBodyVisitable>();
        bodies.add(bodyUn);
        bodies.add(bodyDeux);
        
        UpdateVelocityVisitor updator = new UpdateVelocityVisitor();
        updator.visit(bodies);
        
        assertEquals(15, updator.getNewVelocity()[0], 0);
        assertEquals(-5, updator.getNewVelocity()[1], 0);
    }

}
