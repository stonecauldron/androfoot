package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

public class GoalObserverTest implements GoalObserver {

    private boolean eventOccured = false;
    private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
    
    @Test
    public void testIfEventOccured() {
        EventManager.getEventManager().addGoalObserver(this);
        
        PhysicsWorld.createGoal(false);
        Ball ball = PhysicsWorld.createBall(6, Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
        ball.setLinearVelocity(2, 0);
        
        multiplePhyStep();
        
        assertTrue(eventOccured);
    }

    @Override
    public void goal(boolean isTeamOne) {
        eventOccured = true;
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 50; i++) {
            world.phyStep(1);
        }
    }
}
