package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.box2dphysics.GoalContactListener;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

import com.badlogic.gdx.physics.box2d.World;

public class GoalObserverTest implements GoalObserver {

    private boolean eventOccured = false;
    private World world;
    
    @Before
    public void init() {
        PhysicsWorld.getPhysicsWorld().clear();
        world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
        world.setContactListener(GoalContactListener.getInstance());
        GoalContactListener.setEventManager(EventManagerTester.getEventManager());
    }
    
    @Test
    public void testIfEventOccured() {
        EventManagerTester.getEventManager().addGoalObserver(this);
        
        GoalContactListener.addGoal(PhysicsWorld.getPhysicsWorld().createGoal(10, 0, 0.5f, 6, GoalTeam.TWO));
        Ball ball = PhysicsWorld.getPhysicsWorld().createBall(8.0f, 4.0f, Constants.BALL_RADIUS);
        GoalContactListener.addBall(ball);
        
        ball.setLinearVelocity(Constants.BALL_MAX_VELOCITY, 0);
        
        PaddleTest.multiplePhyStep(world, null);
        
        assertTrue(eventOccured);
    }

    @Override
    public void goal(DefaultGoal goal, DefaultBall ball) {
        eventOccured = true;
    }
}
