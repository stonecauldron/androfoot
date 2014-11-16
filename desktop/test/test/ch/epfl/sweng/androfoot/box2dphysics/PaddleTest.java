package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import junit.framework.TestCase;

public class PaddleTest extends TestCase {
    
    private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
    private GroupPaddle groupPaddle = PhysicsWorld.createPaddle(1, 2, 3, true);
    Paddle paddle = groupPaddle.getPaddles().get(1);
    
    @Test
    public final void testPaddleCreation() {
        
        Array<Body> bodies = new Array<Body>();
        world.getBox2DWorld().getBodies(bodies);
        
        // World must contains the body
        assertTrue(bodies.contains(groupPaddle.getPaddles().get(0).getLimitedArea(), true));
        
        // Test the initial position
        assertEquals(groupPaddle.getPaddles().get(0).getLimitedArea().getPosition().x, 1.0f, 0);
    }
    
    @Test
    public void testPlayerBottomLimit() {
        groupPaddle.setVelocity(0, -2);
        
        float bottomY = Constants.WORLD_SIZE_Y / 3;
        
        multiplePhyStep();
        
        assertEquals(paddle.getPlayer().getPositionY(), bottomY, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerTopLimit() {
        groupPaddle.setVelocity(0, 2);
        
        float topY = Constants.WORLD_SIZE_Y * 2 / 3;
        
        multiplePhyStep();
        
        assertEquals(paddle.getPlayer().getPositionY(), topY, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerRightLimit() {
        groupPaddle.setVelocity(2, 0);
        
        float rightX = 3;
        
        multiplePhyStep();
        
        assertEquals(paddle.getPlayer().getPositionX(), rightX, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerLeftLimit() {
        groupPaddle.setVelocity(-2, 0);
        
        float leftX = 1;
        
        multiplePhyStep();
        
        assertEquals(paddle.getPlayer().getPositionX(), leftX, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testInteractionWithBallandPlayer() {
        groupPaddle.setVelocity(0, 0);
        
        Ball ball = PhysicsWorld.createBall(paddle.getPlayer().getPositionX() + Constants.CIRCLERADIUS + 1, 
                paddle.getPlayer().getPositionY(), Constants.BALL_RADIUS);
        
        ball.setLinearVelocity(-3, 0);
        
        multiplePhyStep();
        
        assertEquals(ball.getLinearVelocity().x, 3, 0.1);
        assertEquals(ball.getLinearVelocity().y, 0, 0.1);
    }
    
    private void multiplePhyStep() {
        for (int i = 0; i < 50; i++) {
            world.phyStep(1);
        }
    }
}
