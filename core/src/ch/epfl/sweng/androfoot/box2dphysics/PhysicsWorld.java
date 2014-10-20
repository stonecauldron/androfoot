package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;

/**
 * The class that defines the Physics World which will contain all the physical objects and 
 * manage their interactions and movements.
 * @author Matvey
 *
 */
public class PhysicsWorld implements DrawableWorld{

	private static final int POSITION_ITERATIONS = 1;
	private static final int VELOCITY_ITERATIONS = 1;
	private static final float TIME_STEP = 1/30f;
	private static final float BALL_RESTITUTION = 1.0f;
	private static final float BALL_FRICTION = 0.0f;
	private static final float BALL_DENSITY = 0.5f;
	private static final float BALL_RADIUS = 0.2f;
	private static final float BALL_INIT_POS_Y = 3.0f;
	private static final float BALL_INIT_POS_X = 5.0f;
	private static final float WORLD_ORIGIN_Y = 0;
	private static final float WORLD_ORIGIN_X = 0;
	private static final float WORLD_SIZE_Y = 6.0f;
	private static final float WORLD_SIZE_X = 10.0f;
	
	private static final PhysicsWorld PHYSICS_WORLD_INSTANCE = new PhysicsWorld();
	
	private World physicsWorld = new World(new Vector2(0, 0), false);
	private Set<Drawable> drawableObjectsSet = new HashSet<Drawable>();
	
	private PhysicsWorld() {
		Ball ball = new Ball(physicsWorld, BALL_INIT_POS_X, BALL_INIT_POS_Y, BALL_RADIUS, 
				BALL_DENSITY, BALL_FRICTION, BALL_RESTITUTION);
		
		drawableObjectsSet.add(ball);
		
		AllBorders allBorders = new AllBorders(physicsWorld, WORLD_SIZE_X, WORLD_SIZE_Y);
	}
	
	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	@Override
	public Set<Drawable> toDraw() {
		return new HashSet<Drawable>(drawableObjectsSet);
	}
	/**
	 * Performs a step of the physics simulation.
	 * @param delta The delat number (Frames Per Second).
	 */
	public void phyStep(float delta) {
		physicsWorld.step(delta, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(WORLD_ORIGIN_X, WORLD_ORIGIN_Y, WORLD_SIZE_X, WORLD_SIZE_Y);
	}
}
