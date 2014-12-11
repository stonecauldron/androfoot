package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.World;

public class BallTest {
	
	private World world;
	private Ball ball;
	private static final float ERROR_MARGIN = 0.001f;
	
	@Before
	public void init() {
	    PhysicsWorld.getPhysicsWorld().clear();
	    world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
	    ball = PhysicsWorld.getPhysicsWorld().createBall(1.0f, 1.0f, Constants.BALL_RADIUS);
	}
	
	@Test
	public void testBallInit() {
		//Test if the ball was created at the right coordinates in the world.
		assertEquals("The x coordinate is false", 1.0f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals("The y coordinate is false", 1.0f, ball.getPositionY(), ERROR_MARGIN);
		assertEquals(Constants.BALL_RADIUS, ball.getRadius(), 0f);
		assertTrue(PhysicsWorld.getPhysicsWorld().toDraw().contains(ball));
	}
	
	@Test
	public void testBallSetPosition() {
		ball.setBallPosition(1.2f, 1.2f);
		multipleStep(50);
		assertEquals(1.2f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals(1.2f, ball.getPositionY(), ERROR_MARGIN);
		ball.setBallPosition(4.9f, 2.5f);
		multipleStep(1);
		assertEquals(4.9f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals(2.5f, ball.getPositionY(), ERROR_MARGIN);
	}
	
	@Test
	public void testBallIntegrity() {
		ball.setLinearVelocity(0f, 0f);
		ball.setBallPosition(Constants.WORLD_SIZE_X + 3.0f, 0f);
		multipleStep(50);
		assertEquals("The ball is outside the world on the x axis", Constants.WORLD_SIZE_X/2, ball.getPositionX(), ERROR_MARGIN);
		
		ball.setBallPosition(0f, Constants.WORLD_SIZE_Y + 5.0f);
		multipleStep(50);
		assertEquals("The ball is outside the world on the y axis", Constants.WORLD_SIZE_Y/2, ball.getPositionY(), ERROR_MARGIN);
		
		ball.setBallPosition(Constants.WORLD_SIZE_X + 0.18f, Constants.WORLD_SIZE_Y + 0.18f);
		multipleStep(50);
		assertEquals("The ball is outside the world on the x axis", Constants.WORLD_SIZE_X/2, ball.getPositionX(), ERROR_MARGIN);
		assertEquals("The ball is outside the world on the y axis", Constants.WORLD_SIZE_Y/2, ball.getPositionY(), ERROR_MARGIN);
	}
	
	@Test
	public void testChangeFixture() {
		ball.changeFixture(0.66f, 100f, 0.88f, 0.89f);
		assertEquals("The radius is incorrect", 0.66f, ball.getRadius(), ERROR_MARGIN);
		assertEquals("The density is incorrect", 100f, ball.getBody().getFixtureList().get(0).getDensity(), ERROR_MARGIN);
		assertEquals("The friction is incorrect", 0.88f, ball.getBody().getFixtureList().get(0).getFriction(), ERROR_MARGIN);
		assertEquals("The restitution is incorrect", 0.89f, ball.getBody().getFixtureList().get(0).getRestitution(), ERROR_MARGIN);
	}
	
	private void multipleStep(int nbSteps) {
		for (int i = 0; i < nbSteps; i++) {
			world.step(1/60f, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);

			EventManager.getEventManager().throwEvents();
			
			PhysicsWorld.getPhysicsWorld().checkIntegrity(ball);
		}
	}

}
