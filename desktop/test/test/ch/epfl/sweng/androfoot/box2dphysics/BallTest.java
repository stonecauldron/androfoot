package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import junit.framework.TestCase;

public class BallTest extends TestCase {
	
	private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
	private Ball ball = PhysicsWorld.createBall(1.0f, 1.0f, Constants.BALL_RADIUS);
	private static final float ERROR_MARGIN = 0.001f;
	
	@Test
	public final void testBallInit() {
		//Test if the ball was created at the right coordinates in the world.
		assertEquals("The x coordinate is false", 1.0f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals("The y coordinate is false", 1.0f, ball.getPositionY(), ERROR_MARGIN);
		assertEquals(0.2f, ball.getRadius(), 0f);
		assertTrue(world.toDraw().contains(ball));
	}
	
	@Test
	public void testBallSetPosition() {
		Ball ball = PhysicsWorld.createBall(1.0f, 1.0f, Constants.BALL_RADIUS);
		ball.setBallPosition(1.2f, 1.2f);
		multipleStep(50);
		assertEquals(1.2f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals(1.2f, ball.getPositionY(), ERROR_MARGIN);
		ball.setBallPosition(4.9f, 2.5f);
		multipleStep(1);
		assertEquals(4.9f, ball.getPositionX(), ERROR_MARGIN);
		assertEquals(2.5f, ball.getPositionY(), ERROR_MARGIN);
	}
	
	private void multipleStep(int nbSteps) {
		for (int i = 0; i < nbSteps; i++) {
			world.getBox2DWorld().step(1, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);

			EventManager.getEventManager().throwEvents();
		}
	}

}
