package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.World;

public class PaddleTest {
    
    private World world;
    private GroupPaddle groupPaddle;
    private Paddle paddle;
    
    @Before
    public void init() {
        PhysicsWorld.getPhysicsWorld().clear();
        world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
        groupPaddle = PhysicsWorld.getPhysicsWorld().createPaddle(2, 2, 3, true);
        paddle = groupPaddle.getPaddles().get(1);
    }
    
    @Test
    public void testPlayerBottomLimit() {
        groupPaddle.setVelocity(0, -2);
        
        float bottomY = Constants.WORLD_SIZE_Y / 3;
        
        multiplePhyStep(world, paddle);
        
        assertEquals(paddle.getPlayer().getPositionY(), bottomY, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerTopLimit() {
        groupPaddle.setVelocity(0, 2);
        
        float topY = Constants.WORLD_SIZE_Y * 2 / 3;
        
        multiplePhyStep(world, paddle);
        
        assertEquals(paddle.getPlayer().getPositionY(), topY, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerRightLimit() {
        groupPaddle.setVelocity(2, 0);
        
        float rightX = 3;
        
        multiplePhyStep(world, paddle);
        
        assertEquals(paddle.getPlayer().getPositionX(), rightX, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testPlayerLeftLimit() {
        groupPaddle.setVelocity(-2, 0);
        
        float leftX = 1;
        
        multiplePhyStep(world, paddle);
        
        assertEquals(paddle.getPlayer().getPositionX(), leftX, Constants.CIRCLERADIUS);
    }
    
    @Test
    public void testInteractionWithBallandPlayer() {
        groupPaddle.setVelocity(0, 0);
        
        Ball ball = PhysicsWorld.getPhysicsWorld().createBall(paddle.getPlayer().getPositionX() + 
                Constants.CIRCLERADIUS + 1.0f, paddle.getPlayer().getPositionY(), Constants.BALL_RADIUS);
        
        ball.setLinearVelocity(-4.0f, 0);
        
        multiplePhyStep(world, paddle);
        
        assertEquals(ball.getLinearVelocity().y, 0, 0.1);
        assertEquals(ball.getLinearVelocity().x, 4, 0.1);
    }
    
    public static void multiplePhyStep(World world, Paddle paddle) {
        for (int i = 0; i < 200; i++) {
            world.step(1 / 60.0f, 4, 10);
            
            if (paddle != null) {
                paddle.checkPosition();
            }
            
            EventManagerTester.getEventManager().throwEvents();
        }
    }
}
