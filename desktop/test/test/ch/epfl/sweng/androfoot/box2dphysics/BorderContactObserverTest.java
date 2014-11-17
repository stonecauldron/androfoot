package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;

public class BorderContactObserverTest implements BorderObserver {

    private boolean eventOccured = false;
    private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
    
    @Test
    public void testIfEventOccured() {
        EventManager.getEventManager().addBorderContactObserver(this);
        
        PhysicsWorld.createBorder(0, 0, 10, 0.2f, BorderType.NO_TEAM);
        Ball ball = PhysicsWorld.createBall(4, Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
        ball.setLinearVelocity(0, -2);
        
        multiplePhyStep();
        
        assertTrue(eventOccured);
    }

    @Override
    public void borderContact(BorderType type) {
        eventOccured = true;
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 50; i++) {
            world.getBox2DWorld().step(1, 1, 1);
            
            EventManager.getEventManager().throwEvents();
        }
    }
}
