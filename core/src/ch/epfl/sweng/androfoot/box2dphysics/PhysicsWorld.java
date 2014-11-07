package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.SortedSet;
import java.util.TreeSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalBorder;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

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
	private TreeSet<Drawable> drawableObjectsSet = new TreeSet<Drawable>(Drawable.DRAWABLE_COMPARATOR);
	private Ball ball;
	private GoalContactListener goalListener;
	
	private PhysicsWorld() {
	    goalListener = new GoalContactListener();
	    physicsWorld.setContactListener(goalListener);
	    
		ball = new Ball(physicsWorld, Constants.BALL_INIT_POS_X, Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS, 
		        Constants.BALL_DENSITY, Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
		
		addToDrawableObjectsSet(ball);
		
		
		Goal teamOneGoal = new Goal(true, physicsWorld, 500);
		goalListener.addGoal(teamOneGoal);
		
		for (GoalBorder goalPart : teamOneGoal.getBorders()) {
			drawableObjectsSet.add(goalPart);
		}
		
		Goal teamTwoGoal = new Goal(false, physicsWorld, 520);
		goalListener.addGoal(teamTwoGoal);
        
        for (GoalBorder goalPart : teamTwoGoal.getBorders()) {
            drawableObjectsSet.add(goalPart);
        }
		
		new AllBorders(physicsWorld, Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}
	
	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	
	public World getBox2DWorld() {
	    return physicsWorld;
	}
	
	public Ball getBall() {
	    return ball;
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
		//fixed time step
		//max frame to avoid the spiral of death
		
		/* float wholeInteger = (float) (delta + accumulator) * Constants.TIME_STEP;
		System.out.println(delta);
		int stepsPerFrame = (int) Math.floor(wholeInteger);
		accumulator = wholeInteger - stepsPerFrame; */
		
		System.out.println("Deltas");
		System.out.println(delta);
		System.out.println(accumulator);
		float correctedDelta = delta + accumulator;
		System.out.println(correctedDelta);
		System.out.println("division");
		System.out.println("time step " + Constants.TIME_STEP);
		float nbPhysicsStepInFrame =  correctedDelta/Constants.TIME_STEP;
		System.out.println("nb phy : " + nbPhysicsStepInFrame);
		int discreteNbPhysicsStepInFrame = (int) Math.floor(nbPhysicsStepInFrame);
		System.out.println("dis " + discreteNbPhysicsStepInFrame);
		accumulator = (nbPhysicsStepInFrame-discreteNbPhysicsStepInFrame)*Constants.TIME_STEP;
		
		//System.out.println(Constants.TIME_STEP);
		//System.out.println(accumulator);
		//System.out.println(delta);
		//System.out.println(correctedDelta);
		//System.out.println(nbPhysicsStepInFrame);
		//System.out.println(discreteNbPhysicsStepInFrame);*/
		
		System.out.println("------");
		
		for (int i = 0; i < discreteNbPhysicsStepInFrame; i++) {
			physicsWorld.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
		}
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(Constants.WORLD_ORIGIN_X, Constants.WORLD_ORIGIN_Y, 
		        Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}

    public void addGoalObserver(GoalObserver obs) {
        goalListener.addObserver(obs);
    }
}
