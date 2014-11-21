package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.SortedSet;
import java.util.TreeSet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.accelerometer.AccelerometerTracker;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * The class that defines the Physics World which will contain all the physical
 * objects and manage their interactions and movements.
 * 
 * @author Matvey
 *
 */
public final class PhysicsWorld implements DrawableWorld {

	private static final PhysicsWorld PHYSICS_WORLD_INSTANCE = new PhysicsWorld();

	private World physicsWorld = new World(new Vector2(0, 0), false);
	private float accumulator = 0f;
	private static boolean isRunning = false;
	private static TreeSet<Drawable> drawableObjectsSet = new TreeSet<Drawable>(
			Drawable.DRAWABLE_COMPARATOR);

	private PlayerTouchTracker touchTrackerInstance = PlayerTouchTracker
			.getInstance();
	private float accelerometerTime = 0;
	private Timer touchEventTimerPlayerOne = new Timer(
			Constants.TOUCH_EVENT_TIMER);
	private Timer touchEventTimerPlayerTwo = new Timer(
			Constants.TOUCH_EVENT_TIMER);

	/* World's object */
	private static Ball ball;

	private PhysicsWorld() {
		physicsWorld.setContactListener(GlobalContactListener.getInstance());
	}

	/**
	 * Timer for player one of the touch event
	 * 
	 * @return Timer
	 */
	public Timer getTouchEventTimerPlayerOne() {
		return touchEventTimerPlayerOne;
	}

	/**
	 * Timer for player two of the touch event
	 * 
	 * @return Timer
	 */
	public Timer getTouchEventTimerPlayerTwo() {
		return touchEventTimerPlayerTwo;
	}

	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * 
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}

	/**
	 * Run the physic world
	 */
	public static void startWorld() {
		isRunning = true;
	}

	/**
	 * Set the world in pause mode
	 */
	public static void pauseWorld() {
		isRunning = false;
	}

	/**
	 * Create a ball in the world.
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param radius
	 *            of the ball
	 * @return
	 */
	public static Ball createBall(float x, float y, float radius) {
		pauseWorld();
		if (ball != null) {
			PHYSICS_WORLD_INSTANCE.getBox2DWorld().destroyBody(ball.getBody());
			drawableObjectsSet.remove(ball);
		}
		ball = new Ball(x, y, radius, Constants.BALL_DENSITY,
				Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
		drawableObjectsSet.add(ball);
		startWorld();

		return ball;
	}

	/**
	 * Get the ball
	 * 
	 * @return
	 */
	public static Ball getBall() {
		return ball;
	}

	/**
	 * Create a paddle in the world
	 * 
	 * @param x
	 *            position of the left bottom corner
	 * @param width
	 *            of the paddle
	 * @param number
	 *            of players in the paddle
	 * @param facingRight
	 *            true if the paddle is right oriented
	 * @return
	 */
	public static GroupPaddle createPaddle(float x, float width, int number,
			boolean facingRight) {
		pauseWorld();
		GroupPaddle groupPaddle = new GroupPaddle(x - width / 2, width, number,
				facingRight);
		for (Paddle paddle : groupPaddle.getPaddles()) {
			drawableObjectsSet.add(paddle.getPlayer());
		}
		startWorld();

		return groupPaddle;
	}

	/**
	 * Create a border
	 * 
	 * @param x
	 *            position of the bottom left corner
	 * @param y
	 *            position of the bottom left corner
	 * @param width
	 *            of the border
	 * @param height
	 *            of the border
	 * @param teamOne
	 *            true if the border is colored with teamOne parameter
	 * @return
	 */
	public static Border createBorder(float x, float y, float width,
			float height, BorderType type) {
		pauseWorld();
		Border border = new Border(x, y, width, height, type);
		drawableObjectsSet.add(border);
		startWorld();

		return border;
	}

	/**
	 * Create the goal of team one or two
	 * 
	 * @param teamOne
	 *            true for team one
	 * @return
	 */
	public static Goal createGoal(float x, float y, float width, float height,
			GoalTeam team) {
		pauseWorld();
		Goal goal = new Goal(x, y, width, height, team);
		startWorld();

		return goal;
	}

	/**
	 * Returns the Box2D world.
	 * 
	 * @return Box2D world.
	 */
	public World getBox2DWorld() {
		return physicsWorld;
	}

	@Override
	public SortedSet<Drawable> toDraw() {
		return drawableObjectsSet;
	}

	/**
	 * Performs a step of the physics simulation. Uses an accumulator to
	 * calculate correctly the number of physics steps in relation to the delta
	 * time of the renderer. The time step stays constant.
	 * 
	 * @param delta
	 *            The delta number (Frames Per Second).
	 */
	public void phyStep(float delta) {
		if (isRunning) {
			// fixed time step
			float correctedDelta = delta + accumulator;
			float nbPhysicsStepInFrame = correctedDelta / Constants.TIME_STEP;
			int discreteNbPhysicsStepInFrame = (int) Math
					.floor(nbPhysicsStepInFrame);
			accumulator = (nbPhysicsStepInFrame - discreteNbPhysicsStepInFrame)
					* Constants.TIME_STEP;

			for (int i = 0; i < discreteNbPhysicsStepInFrame; i++) {
				physicsWorld.step(Constants.TIME_STEP,
						Constants.VELOCITY_ITERATIONS,
						Constants.POSITION_ITERATIONS);
				if (ball != null) {
					checkVelocity(ball);
				}
			}

			// For Accelerometer polling
			accelerometerTime += correctedDelta;
			if (accelerometerTime > Constants.SHAKE_TIMER
					&& AccelerometerTracker.getInstance().isShaking()) {
				accelerometerTime = 0;
				ball.setLinearVelocity(-AccelerometerTracker.getInstance()
						.getmYGrav() * Constants.SHAKE_BOOST_RATIO,
						AccelerometerTracker.getInstance().getmXGrav()
								* Constants.SHAKE_BOOST_RATIO);
			}

			/*
			 * For touch event if timer is elapsed we throw a new update with
			 * touch up to reset ball speed, this ensures paddles don't move if
			 * no more event happens but a speed is set
			 */
			if (touchEventTimerPlayerOne.checkTimer()) {
				int playerOnePointer = touchTrackerInstance
						.getmPlayerOneCurrentPointer();
				touchTrackerInstance.touchDragged(-1, -1, playerOnePointer);
			}
			touchEventTimerPlayerOne.updateTimer(correctedDelta);

			if (touchEventTimerPlayerTwo.checkTimer()) {
				int playerTwoPointer = touchTrackerInstance
						.getmPlayerTwoCurrentPointer();
				touchTrackerInstance.touchDragged(-1, -1, playerTwoPointer);
			}
			touchEventTimerPlayerTwo.updateTimer(correctedDelta);

			EventManager.getEventManager().throwEvents();
		}
	}

	/**
	 * Checks the velocity of the ball and limits it if the velocity of the ball
	 * is bigger than the maximum allowed.
	 * 
	 * @param testedBall
	 *            Ball to be checked.
	 */
	public void checkVelocity(Ball testedBall) {
		Vector2 ballVelocity = testedBall.getLinearVelocity();
		ballVelocity = ballVelocity.clamp(Constants.BALL_MIN_VELOCITY,
				Constants.BALL_MAX_VELOCITY);
		testedBall.setLinearVelocity(ballVelocity.x, ballVelocity.y);

	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(Constants.WORLD_ORIGIN_X,
				Constants.WORLD_ORIGIN_Y, Constants.WORLD_SIZE_X,
				Constants.WORLD_SIZE_Y);
	}
}
