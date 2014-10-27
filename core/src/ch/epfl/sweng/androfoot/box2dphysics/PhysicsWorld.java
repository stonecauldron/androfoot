package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.SortedSet;
import java.util.TreeSet;

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
public final class PhysicsWorld implements DrawableWorld {
	
	private static final PhysicsWorld PHYSICS_WORLD_INSTANCE = new PhysicsWorld();
	
	private World physicsWorld = new World(new Vector2(0, 0), false);
	private TreeSet<Drawable> drawableObjectsSet = new TreeSet<Drawable>(Drawable.DRAWABLE_COMPARATOR);
	
	private PhysicsWorld() {
		Ball ball = new Ball(physicsWorld, Constants.BALL_INIT_POS_X, Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS, 
		        Constants.BALL_DENSITY, Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
		
		addToDrawableObjectsSet(ball);
		
		Player p = new Player(physicsWorld, 2.5f, 3.0f, true);
		
		drawableObjectsSet.add(p);
		
		new AllBorders(physicsWorld, Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}
	
	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	
	public World getWorld() {
	    return physicsWorld;
	}
	
	public void addToDrawableObjectsSet(Drawable object) {
		drawableObjectsSet.add(object);
	}
	@Override
	public SortedSet<Drawable> toDraw() {
		return drawableObjectsSet;
	}
	/**
	 * Performs a step of the physics simulation.
	 * @param delta The delta number (Frames Per Second).
	 */
	public void phyStep(float delta) {
		physicsWorld.step(delta, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(Constants.WORLD_ORIGIN_X, Constants.WORLD_ORIGIN_Y, 
		        Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}
}
