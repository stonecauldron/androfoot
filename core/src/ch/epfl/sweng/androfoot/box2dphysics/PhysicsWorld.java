package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalBorder;
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
	private float accumulator = 0f;
	private static boolean isRunning = false;
	private TreeSet<Drawable> drawableObjectsSet = new TreeSet<Drawable>(Drawable.DRAWABLE_COMPARATOR);
	
	/* World's object */
	private static Ball ball;
	
	private PhysicsWorld() {
	    physicsWorld.setContactListener(GlobalContactListener.getInstance());
	}
	
	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	
	public static void startWorld() {
	    isRunning = true;
	}
	
	public static void stopWorld() {
	    isRunning = false;
	}
	
	public static void createBall(float x, float y, float radius) {
	    stopWorld();
	    ball = new Ball(x, y, radius, Constants.BALL_DENSITY, Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
	    startWorld();
	}
	
	public static Ball getBall() {
	    return ball;
	}
	
	/**
	 * Returns the Box2D world.
	 * @return Box2D world.
	 */
	public World getBox2DWorld() {
	    return physicsWorld;
	}
	
	/**
	 * Adds a Drawable object to the Drawable objects set.
	 * @param object Drawable object to be added.
	 */
	public void addToDrawableObjectsSet(Drawable object) {
		drawableObjectsSet.add(object);
	}
	
	@Override
	public SortedSet<Drawable> toDraw() {
		return drawableObjectsSet;
	}
	/**
	 * Performs a step of the physics simulation. Uses an accumulator to calculate correctly the number
	 * of physics steps in relation to the delta time of the renderer. The time step stays constant. 
	 * @param delta The delta number (Frames Per Second).
	 */
	public void phyStep(float delta) {
	    if (isRunning) {
    		//fixed time step
    		float correctedDelta = delta + accumulator;
    		float nbPhysicsStepInFrame =  correctedDelta/Constants.TIME_STEP;
    		int discreteNbPhysicsStepInFrame = (int) Math.floor(nbPhysicsStepInFrame);
    		accumulator = (nbPhysicsStepInFrame-discreteNbPhysicsStepInFrame)*Constants.TIME_STEP;
    		
    		for (int i = 0; i < discreteNbPhysicsStepInFrame; i++) {
    			physicsWorld.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
    		}
    
            EventManager.getEventManager().throwEvents();
            if (ball != null) {
                checkVelocity(ball);
            }
	    }
	}
	
	/**
	 * Checks the velocity of the ball and limits it if the velocity of the ball is bigger
	 * thatn the maximum allowed.
	 * @param ball Ball to be checked.
	 */
	public void checkVelocity(Ball ball) {
		Vector2 ballVelocity = ball.getLinearVelocity();
		
		if((ballVelocity.x > Constants.BALL_MAX_VELOCITY) || 
				(ballVelocity.y > Constants.BALL_MAX_VELOCITY)) {
			
			ball.setLinearVelocity(Constants.BALL_MAX_VELOCITY, Constants.BALL_MAX_VELOCITY);
		}
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(Constants.WORLD_ORIGIN_X, Constants.WORLD_ORIGIN_Y, 
		        Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}
}
