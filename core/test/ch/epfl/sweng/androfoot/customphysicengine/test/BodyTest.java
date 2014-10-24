package ch.epfl.sweng.androfoot.customphysicengine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.sweng.androfoot.customphysicengine.CircleBody;
import ch.epfl.sweng.androfoot.customphysicengine.RectangleBody;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class BodyTest {

    @Test
    public void createARectanglecBodyTest() {
        Rectangle rect = new Rectangle(1, 2, 200, 50);
        RectangleBody body = new RectangleBody(rect, 5, 0);
        
        assertEquals(body.getX(), 1, 0);
        assertEquals(body.getY(), 2, 0);
        assertTrue(body.isDynamic());
    }
    
    @Test
    public void createACircleBodyTest() {
        Circle circle = new Circle(5, 6, 10);
        CircleBody body = new CircleBody(circle);
        
        assertEquals(body.getX(), 5, 0);
        assertEquals(body.getY(), 6, 0);
        assertTrue(body.isStatic());
    }
    
    @Test
    public void moveBodyTest() {
        Rectangle rect = new Rectangle(0, 0, 50, 50);
        RectangleBody body = new RectangleBody(rect);
        
        body.move(5, 0);
        assertEquals(body.getX(), 5, 0);
        assertEquals(body.getY(), 0, 0);
        
        body.move(0, 10);
        assertEquals(body.getX(), 5, 0);
        assertEquals(body.getY(), 10, 0);
    }

}
