package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PowerUp;
import ch.epfl.sweng.androfoot.box2dphysics.PowerUpContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.PowerUpObserver;

public class PowerUpContactObserverTest implements PowerUpObserver {

	private boolean eventOcurred = false;
	private World world = new World(new Vector2(0, 0), false);
	private Ball ball;

	@Before
	public void initialise() {
		world.setContactListener(PowerUpContactListener.getInstance());
		PowerUpContactListener.setEventManager(EventManagerTester
				.getEventManager());
	}

	@Test
	public void testIfEventOcurred() {
		EventManagerTester.getEventManager().addPowerUpContactObserver(this);

		PowerUpContactListener.addPowerUp(new PowerUp(world, 1.0f, 1.0f, 1.0f));
		ball = new Ball(world, 4.0f, 1.0f, Constants.BALL_RADIUS,
				Constants.BALL_DENSITY, Constants.BALL_FRICTION,
				Constants.BALL_RESTITUTION);
		
		ball.setLinearVelocity(1.0f, 0);
		PaddleTest.multiplePhyStep(world, null);
		assertFalse(eventOcurred);
		
		ball.setBallPosition(1.0f, 1.0f);
		ball.setLinearVelocity(-1.0f, 0);
		PaddleTest.multiplePhyStep(world, null);
		assertTrue(eventOcurred);
	}

	@Override
	public void applyPowerUp(DefaultPowerUp powerUp) {
		eventOcurred = true;
	}
}
