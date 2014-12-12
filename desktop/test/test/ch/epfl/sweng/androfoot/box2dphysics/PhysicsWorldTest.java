package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

public class PhysicsWorldTest {
    
    Array<Body> bodies = new Array<Body>();
    World world;
    
    @Before
    public void init() {
    	PhysicsWorld.getPhysicsWorld().clear();
    	world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
    }

    @Test
    public void testClearRemoveAllBodies() {
        PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
        PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
        PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
        PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
        PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        
        assertTrue(bodies.size > 0);
        
        PhysicsWorld.getPhysicsWorld().clear();
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        
        assertTrue(bodies.size == 0);
        assertTrue(PhysicsWorld.getPhysicsWorld().toDraw().size() == 0);
    }
    
    @Test
    public void testDestroyBall() {
        PhysicsWorld.getPhysicsWorld().clear();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        
        Ball ball = PhysicsWorld.getPhysicsWorld().createBall(0, 0, 2);
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 1);
        
        PhysicsWorld.getPhysicsWorld().destroy(ball);
        PhysicsWorld.getPhysicsWorld().throwDestroy();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        assertTrue(!PhysicsWorld.getPhysicsWorld().toDraw().contains(ball));
    }
    
    @Test
    public void testDestroyGroupPaddle() {
        PhysicsWorld.getPhysicsWorld().clear();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        
        GroupPaddle group = PhysicsWorld.getPhysicsWorld().createPaddle(1, 2, 1, true);
        
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 1);
        
        PhysicsWorld.getPhysicsWorld().destroy(group);
        PhysicsWorld.getPhysicsWorld().throwDestroy();
        PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
        assertTrue(bodies.size == 0);
        assertTrue(PhysicsWorld.getPhysicsWorld().toDraw().size() == 0);
    }
    
    @Test
    public void testClear() {
    	PhysicsWorld.getPhysicsWorld().createBall(1, 1, 1);
    	PhysicsWorld.getPhysicsWorld().createPowerUp(3, 3, 1);
    	PhysicsWorld.getPhysicsWorld().createPowerUp(5, 5, 1);
    	PhysicsWorld.getPhysicsWorld().createPaddle(1, 1, 4, true);
    	
    	PhysicsWorld.getPhysicsWorld().clear();
    	
    	assertTrue(bodies.size == 0);
    	assertTrue(PhysicsWorld.getPhysicsWorld().toDraw().isEmpty());
    	assertTrue(PhysicsWorld.getPhysicsWorld().getBall() == null);
    }
}
