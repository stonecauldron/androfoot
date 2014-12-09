package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Player;
import ch.epfl.sweng.androfoot.box2dphysics.PlayerContactListener;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

import com.badlogic.gdx.physics.box2d.World;

public class PlayerContactObserverTest implements PlayerObserver {

	private World world;
	private boolean eventOcurred = false;
	private Ball ball;

	@Before
	public void initilalize() {
	    PhysicsWorld.getPhysicsWorld().clear();
	    world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
		world.setContactListener(PlayerContactListener.getInstance());
		PlayerContactListener.setEventManager(EventManagerTester
				.getEventManager());
		
		ball = PhysicsWorld.getPhysicsWorld().createBall(Constants.BALL_INIT_POS_X,
                Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS);
	}

	@Test
	public void testIfPlayerEventOcurredOnFront() {
		EventManagerTester.getEventManager().addPlayerObserver(this);
		
		GroupPaddle paddleFacingRight = PhysicsWorld.getPhysicsWorld()
		        .createPaddle(1.0f, Constants.PADDLE_WIDTH, 1, true);

		ball.setLinearVelocity(-Constants.BALL_MAX_VELOCITY, 0f);
		
		PlayerContactListener.addPlayer(paddleFacingRight.getPaddles().get(0).getPlayer());

		multipleStep(200);

		assertFalse(eventOcurred);
	}


	@Override
	public void setBall(Player player, boolean teamFlag) {
		eventOcurred = true;
	}

	private void multipleStep(int nbSteps) {
		for (int i = 0; i < nbSteps; i++) {
			world.step(1 / 60.0f, Constants.VELOCITY_ITERATIONS,
					Constants.POSITION_ITERATIONS);

			EventManagerTester.getEventManager().throwEvents();
		}
	}
}
