package test.ch.epfl.sweng.androfoot.box2dphysics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.PowerUpContactListener;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.PowerUpObserver;

import com.badlogic.gdx.physics.box2d.World;

public class PowerUpContactObserverTest implements PowerUpObserver {

	private boolean eventOcurred = false;
	private World world;
	private Ball ball;

	@Before
	public void initialise() {
	    PhysicsWorld.getPhysicsWorld().clear();
	    world = PhysicsWorld.getPhysicsWorld().getBox2DWorld();
		world.setContactListener(PowerUpContactListener.getInstance());
		PowerUpContactListener.setEventManager(EventManagerTester
				.getEventManager());
	}

	@Test
	public void testIfEventOcurred() {
		EventManagerTester.getEventManager().addPowerUpContactObserver(this);

		PowerUpContactListener.addPowerUp(PhysicsWorld.getPhysicsWorld().createPowerUp(1.0f, 1.0f, 1.0f));
		ball = PhysicsWorld.getPhysicsWorld().createBall(4.0f, 1.0f, Constants.BALL_RADIUS);
		
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
