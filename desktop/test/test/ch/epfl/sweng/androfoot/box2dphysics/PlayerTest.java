package test.ch.epfl.sweng.androfoot.box2dphysics;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Player;

public class PlayerTest {

	private Player player;
	
	private static final float ERROR_MARGIN = 0.001f;
	
	@Before
	public void init() {
		PhysicsWorld.getPhysicsWorld().clear();
		PhysicsWorld.getPhysicsWorld().getBox2DWorld();
		player = PhysicsWorld.getPhysicsWorld().createPaddle(1.0f, 2, 1, true).getPaddles().get(0).getPlayer();
	}
	
	@Test
	public void testPlayerFixtureInit() {
		assertEquals("Player shape not properly initialised", 0.4f, player.getSemiHeight(), ERROR_MARGIN);
	}
	
}
