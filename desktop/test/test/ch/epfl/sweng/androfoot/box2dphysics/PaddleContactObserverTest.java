package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;

public class PaddleContactObserverTest implements PaddleContactObserver {
    
    private boolean eventOccured = false;
    private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
    
    @Test
    public void testIfEventOccured() {
        EventManager.getEventManager().addPaddleContactObserver(this);
        
        PhysicsWorld.createPaddle(1, 2, 1, true);
        Ball ball = PhysicsWorld.createBall(4, Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
        ball.setLinearVelocity(-2, 0);
        
        multiplePhyStep();
        
        assertTrue(eventOccured);
    }

    @Override
    public void paddleContact() {
        eventOccured = true;
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 50; i++) {
            world.phyStep(1);
        }
    }
}
