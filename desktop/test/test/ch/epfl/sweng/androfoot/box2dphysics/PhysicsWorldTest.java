package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

public class PhysicsWorldTest {
    
    Array<Body> bodies = new Array<Body>();

    @Test
    public void testClearRemoveAllBodies() {
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        
        assertTrue(bodies.size > 0);
        
        PhysicsWorld.clear();
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        
        assertTrue(bodies.size == 0);
    }
    
    @Test
    public void testDestroyBall() {
        PhysicsWorld.clear();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        
        Ball ball = PhysicsWorld.createBall(0, 0, 2);
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 1);
        
        PhysicsWorld.destroy(ball);
        PhysicsWorld.getPhysicsWorld().throwDestroy();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
    }
    
    @Test
    public void testDestroyGroupPaddle() {
        PhysicsWorld.clear();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        
        GroupPaddle group = PhysicsWorld.createPaddle(1, 2, 1, true);
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 1);
        
        PhysicsWorld.destroy(group);
        PhysicsWorld.getPhysicsWorld().throwDestroy();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
    }
}
