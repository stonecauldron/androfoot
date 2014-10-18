package ch.epfl.sweng.androfoot.customphysicengine.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.math.Polygon;

import ch.epfl.sweng.androfoot.customphysicengine.DynamicBody;
import ch.epfl.sweng.androfoot.customphysicengine.StaticBody;

public class BodyTest {

    @Test
    public void createAStaticBodyTest() {
        float[] vertices = new float[8];
        vertices[0] = 5;
        vertices[1] = 6;
        
        vertices[2] = 8;
        vertices[3] = 8;
        
        vertices[4] = 15;
        vertices[5] = 8;
        
        vertices[6] = 15;
        vertices[7] = 5;
        
        StaticBody body = new StaticBody(new Polygon(vertices));
        body.move(5, 6);
        
        assertEquals(body.getX(), 5, 0);
        assertEquals(body.getY(), 6, 0);
    }
    
    @Test
    public void createADynamicBodyTest() {
        float[] vertices = new float[8];
        vertices[0] = 5;
        vertices[1] = 6;
        
        vertices[2] = 8;
        vertices[3] = 8;
        
        vertices[4] = 15;
        vertices[5] = 8;
        
        vertices[6] = 15;
        vertices[7] = 5;
        
        DynamicBody body = new DynamicBody(new Polygon(vertices));
        body.move(5, 6);
        
        assertEquals(body.getX(), 5, 0);
        assertEquals(body.getY(), 6, 0);
        assertEquals(body.getVelocity().getVelocityX(), 0, 0);
        assertEquals(body.getVelocity().getVelocityY(), 0, 0);
    }

}
