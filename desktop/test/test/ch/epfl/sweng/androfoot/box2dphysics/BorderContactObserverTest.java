package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.BorderContactListener;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;

import com.badlogic.gdx.physics.box2d.World;

public class BorderContactObserverTest implements BorderObserver {

    private boolean eventOccured = false;
    private World world;
    private Ball ball;
    
    @Before
    public void init() {
        PhysicsWorld.getPhysicsWorld().clear();
        world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
        world.setContactListener(BorderContactListener.getInstance());
        BorderContactListener.setEventManager(EventManagerTester.getEventManager());
    }
    
    @Test
    public void testIfEventOccured() {
        EventManagerTester.getEventManager().addBorderContactObserver(this);
        
        BorderContactListener.addBorder(PhysicsWorld.getPhysicsWorld()
                .createBorder(0, 0, 10, 0.2f, BorderType.NO_TEAM));
        ball = PhysicsWorld.getPhysicsWorld().createBall(5.0f, 5.0f, Constants.BALL_RADIUS);
        BorderContactListener.addBall(ball);
        ball.setLinearVelocity(0, -2.0f);
        
        PaddleTest.multiplePhyStep(world, null);
        
        assertTrue(eventOccured);
    }

    @Override
    public void borderContact(DefaultBorder border, DefaultBall ball) {
        eventOccured = true;
    }
}
