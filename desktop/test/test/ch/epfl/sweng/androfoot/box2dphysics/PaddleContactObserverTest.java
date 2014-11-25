package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PaddleContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;

public class PaddleContactObserverTest implements PaddleContactObserver {
    
    private boolean eventOccured = false;
    private World world = new World(new Vector2(0, 0), false);
    
    @Before
    public void init() {
        world.setContactListener(PaddleContactListener.getInstance());
        PaddleContactListener.setEventManager(EventManagerTester.getEventManager());
    }
    
    @Test
    public void testIfEventOccured() {
        EventManagerTester.getEventManager().addPaddleContactObserver(this);
        
        PaddleContactListener.addPlayer(new GroupPaddle(world, 1, 2, 1, true).getPaddles().get(0).getPlayer());
        Ball ball = new Ball(world, 5.0f, Constants.WORLD_SIZE_Y / 2, Constants.BALL_RADIUS, 0.000001f, 0.0f, 1.0f);
        PaddleContactListener.addBall(ball);
        ball.setLinearVelocity(-2, 0);
        
        PaddleTest.multiplePhyStep(world, null);
        
        assertTrue(eventOccured);
    }

    @Override
    public void paddleContact(DefaultPlayer player, DefaultBall ball) {
        eventOccured = true;
    }
}
