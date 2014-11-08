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
	private TreeSet<Drawable> drawableObjectsSet = new TreeSet<Drawable>(Drawable.DRAWABLE_COMPARATOR);
	private Ball ball;
	
	//TESTING======================================================
	private GroupPaddle mPaddlesOnePlayerOne;
	private GroupPaddle mPaddlesTwoPlayerOne;
	private GroupPaddle mPaddlesOnePlayerTwo;
	private GroupPaddle mPaddlesTwoPlayerTwo;
	
	
	private PhysicsWorld() {
	    EventManager.getEventManager().initListener(physicsWorld);
	    
		ball = new Ball(physicsWorld, Constants.BALL_INIT_POS_X, Constants.BALL_INIT_POS_Y, Constants.BALL_RADIUS, 
		        Constants.BALL_DENSITY, Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
		
		addToDrawableObjectsSet(ball);
		
		
		Goal teamOneGoal = new Goal(true, physicsWorld, 500);
		EventManager.getEventManager().addGoalListener(teamOneGoal);
		
		for (GoalBorder goalPart : teamOneGoal.getBorders()) {
			drawableObjectsSet.add(goalPart);
		}
		
		Goal teamTwoGoal = new Goal(false, physicsWorld, 520);
		EventManager.getEventManager().addGoalListener(teamTwoGoal);
        
        for (GoalBorder goalPart : teamTwoGoal.getBorders()) {
            drawableObjectsSet.add(goalPart);
        }
		
		new AllBorders(physicsWorld, Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
		
		//TESTING===================================================================
		mPaddlesOnePlayerOne = new GroupPaddle(1, 2, 3,
				physicsWorld, Constants.WORLD_SIZE_Y,
				true);

		mPaddlesTwoPlayerOne = new GroupPaddle(5, 2, 2,
				physicsWorld, Constants.WORLD_SIZE_Y,
				true);

		mPaddlesOnePlayerTwo = new GroupPaddle(7, 2, 3,
				physicsWorld, Constants.WORLD_SIZE_Y,
				false);

		mPaddlesTwoPlayerTwo = new GroupPaddle(3, 2, 2,
				physicsWorld, Constants.WORLD_SIZE_Y,
				false);
		
		for (Player player : getAllPlayers()) {
			drawableObjectsSet.add(player);
		}
		
		for (Player player : getAllPlayers()) {
			EventManager.getEventManager().addPlayerListener(player);
		}
		
	}
	
	private ArrayList<Player> getAllPlayers() {
		ArrayList<GroupPaddle> groupPaddleList = new ArrayList<GroupPaddle>();
		groupPaddleList.add(mPaddlesOnePlayerOne);
		groupPaddleList.add(mPaddlesOnePlayerTwo);
		groupPaddleList.add(mPaddlesTwoPlayerOne);
		groupPaddleList.add(mPaddlesTwoPlayerTwo);
		
		ArrayList<Paddle> paddleList = new ArrayList<Paddle>();
		
		for (GroupPaddle group : groupPaddleList) {
			paddleList.addAll(group.getPaddles());
		}
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		for (Paddle paddle : paddleList) {
			playerList.add(paddle.getPlayer());
		}
		
		return playerList;
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
	 * Performs a step of the physics simulation. Uses an accumulator to calculate correctly the number
	 * of physics steps in relation to the delta time of the renderer. The time step stays constant. 
	 * @param delta The delta number (Frames Per Second).
	 */
	public void phyStep(float delta) {
		//fixed time step
		
		float correctedDelta = delta + accumulator;
		float nbPhysicsStepInFrame =  correctedDelta/Constants.TIME_STEP;
		int discreteNbPhysicsStepInFrame = (int) Math.floor(nbPhysicsStepInFrame);
		accumulator = (nbPhysicsStepInFrame-discreteNbPhysicsStepInFrame)*Constants.TIME_STEP;
		
		for (int i = 0; i < discreteNbPhysicsStepInFrame; i++) {
			physicsWorld.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
		}

        EventManager.getEventManager().throwEvents();
		checkVelocity(ball);
	}
	
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
