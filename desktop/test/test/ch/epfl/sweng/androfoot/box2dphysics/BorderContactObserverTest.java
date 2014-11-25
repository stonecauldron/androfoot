package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Border;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.BorderContactListener;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;

public class BorderContactObserverTest implements BorderObserver {

    private boolean eventOccured = false;
    private World world = new World(new Vector2(0, 0), false);
    private Ball ball;
    
    @Before
    public void init() {
        world.setContactListener(BorderContactListener.getInstance());
        BorderContactListener.setEventManager(EventManagerTester.getEventManager());
    }
    
    @Test
    public void testIfEventOccured() {
        EventManagerTester.getEventManager().addBorderContactObserver(this);
        
        BorderContactListener.addBorder(new Border(world, 0, 0, 10, 0.2f, BorderType.NO_TEAM));
        ball = new Ball(world, 5.0f, 5.0f, Constants.BALL_RADIUS, 0.000001f, 0.0f, 1.0f);
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
