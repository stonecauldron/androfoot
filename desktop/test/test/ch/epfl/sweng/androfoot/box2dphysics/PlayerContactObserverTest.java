package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Player;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;
import junit.framework.TestCase;

public class PlayerContactObserverTest extends TestCase implements
		PlayerObserver {

	private PhysicsWorld world = PhysicsWorld.getPhysicsWorld();
	private Ball ball = PhysicsWorld.createBall(Constants.BALL_INIT_POS_X,
			Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS);
	
	private boolean eventOcurred = false;
	
	@Test
	public void testIfPlayerEventOcurredOnFront() {
		EventManager.getEventManager().addPlayerObserver(this);
		
		GroupPaddle paddle = PhysicsWorld.createPaddle(1.0f, Constants.PADDLE_WIDTH, 1, true);
		ball.setLinearVelocity(-Constants.BALL_MAX_VELOCITY, 0f);
		
		multipleStep(50);
		
		assertFalse(eventOcurred);
	}
	
	@Test
	public void testIfPlayerEventOcurredOnBack() {
		EventManager.getEventManager().addPlayerObserver(this);
		
		PhysicsWorld.createPaddle(3.0f, Constants.PADDLE_WIDTH, 1, false);
		ball.setBallPosition(Constants.BALL_INIT_POS_X * 1.5f, Constants.BALL_INIT_POS_Y);
		ball.setLinearVelocity(-Constants.BALL_MAX_VELOCITY, 0f);
		
		multipleStep(50);
		
		assertTrue(eventOcurred);
	}

	@Override
	public void setBall(Player player, boolean teamFlag) {
		eventOcurred = true;
	}

	private void multipleStep(int nbSteps) {
		for (int i = 0; i < nbSteps; i++) {
			world.getBox2DWorld().step(1, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);

			EventManager.getEventManager().throwEvents();
		}
	}
}
