package ch.epfl.sweng.androfoot.android.test;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import junit.framework.TestCase;

public class GroupPaddleTest extends TestCase {
    
    @Test
    public void testGroupPaddleCreation() {
        GroupPaddle paddles = new GroupPaddle(0, 1, 2, true);
        
        Array<Body> bodies = new Array<Body>();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        
        // World must contains the body
        for (Paddle paddle : paddles.getPaddles()) {
            assertTrue(bodies.contains(paddle.getLimitedArea(), true));
        }
    }

}
