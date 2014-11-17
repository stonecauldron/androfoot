package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

public class GoalObserverTest implements GoalObserver {

    private boolean eventOccured = false;
    private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
    
    @Test
    public void testIfEventOccured() {
        EventManager.getEventManager().addGoalObserver(this);
        
        PhysicsWorld.createGoal(10, 0, 0.5f, 6, GoalTeam.TWO);
        Ball ball = PhysicsWorld.createBall(6, Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS);
        ball.setLinearVelocity(Constants.BALL_MAX_VELOCITY, 0);
        
        multiplePhyStep();
        
        assertTrue(eventOccured);
    }

    @Override
    public void goal(GoalTeam team) {
        eventOccured = true;
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 50; i++) {
            world.getBox2DWorld().step(1, 1, 1);
            
            EventManager.getEventManager().throwEvents();
        }
    }
}
