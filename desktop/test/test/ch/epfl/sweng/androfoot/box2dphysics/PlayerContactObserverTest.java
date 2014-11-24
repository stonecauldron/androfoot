package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Player;
import ch.epfl.sweng.androfoot.box2dphysics.PlayerContactListener;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;
import junit.framework.TestCase;

public class PlayerContactObserverTest extends TestCase implements
		PlayerObserver {

	private World world = new World(new Vector2(0, 0), false);

	private boolean eventOcurred = false;

	private Ball ball = new Ball(world, Constants.BALL_INIT_POS_X,
			Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS,
			Constants.BALL_DENSITY, Constants.BALL_FRICTION,
			Constants.BALL_RESTITUTION);

	@Before
	public void initilalize() {
		world.setContactListener(PlayerContactListener.getInstance());
		PlayerContactListener.setEventManager(EventManagerTester
				.getEventManager());
	}

	@Test
	public void testIfPlayerEventOcurredOnFront() {
		EventManagerTester.getEventManager().addPlayerObserver(this);
		
		GroupPaddle paddleFacingRight = new GroupPaddle(world, 1.0f,
				Constants.PADDLE_WIDTH, 1, true);

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
