package ch.epfl.sweng.androfoot.android.test;

import org.junit.Test;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import junit.framework.TestCase;

public class PaddleTest extends TestCase {
    
    @Test
    public final void testPaddleCreation() {
        World worldTest = PhysicsWorld.getPhysicsWorld().getWorld();
        
        Paddle paddle = new Paddle(worldTest, 1, 1, 1, 1);
        
        Array<Body> bodies = new Array<Body>();
        worldTest.getBodies(bodies);
        
        // World must contains the body
        assertTrue(bodies.contains(paddle.getLimitedArea(), true));
        
        // Test the initial position
        assertEquals(paddle.getLimitedArea().getPosition().x, 1.0f, 0);
    }
}
