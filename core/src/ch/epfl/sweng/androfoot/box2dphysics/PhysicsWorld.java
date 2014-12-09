package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.accelerometer.AccelerometerTracker;
import ch.epfl.sweng.androfoot.box2dphysics.Border.BorderType;
import ch.epfl.sweng.androfoot.box2dphysics.Goal.GoalTeam;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultWorldObject;
import ch.epfl.sweng.androfoot.interfaces.Drawable;
import ch.epfl.sweng.androfoot.interfaces.DrawableWorld;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerShapeListener;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * The class that defines the Physics World which will contain all the physical objects and 
 * manage their interactions and movements.
 * @author Matvey
 *
 */
public final class PhysicsWorld implements DrawableWorld, ClientObserver, HostObserver, PlayerShapeListener {
	
	private static final PhysicsWorld PHYSICS_WORLD_INSTANCE = new PhysicsWorld();
	
	private World physicsWorld = new World(new Vector2(0, 0), false);
	private float accumulator = 0f;
	private boolean isRunning = true;
	private List<Drawable> drawableObjectsSet = new ArrayList<Drawable>();
	
	private float accelerometerTime = 0;

	private float networkBallX = Constants.WORLD_SIZE_X / 2;
	private float networkBallY = Constants.WORLD_ORIGIN_Y / 2;

	private boolean slaveMode = false;
	private boolean hostMode = false;

	private float networkBallSpeedX = 0;

	private float networkBallSpeedY = 0;

	private boolean updated = false;
	
	/* World's object */
	private static Ball ball;
	private static Set<Paddle> paddles;
	private static Set<DefaultWorldObject> objectsToDestroy;
	
	private PhysicsWorld() {
	    physicsWorld.setContactListener(GlobalContactListener.getInstance());
	    
	    paddles = new HashSet<Paddle>();
	    objectsToDestroy = new HashSet<DefaultWorldObject>();
	}
	
	/**
	 * Returns the singleton instance of the PhysicsWorld.
	 * @return The instance of PhysicsWorld.
	 */
	public static PhysicsWorld getPhysicsWorld() {
		return PHYSICS_WORLD_INSTANCE;
	}
	
	/**
	 * Run the physic world
	 */
	public void startWorld() {
	    isRunning = true;
	}
	
	/**
	 * Set the world in pause mode
	 */
	public void pauseWorld() {
	    isRunning = false;
	}
	
	/**
	 * Create a ball in the world.
	 * @param x position
	 * @param y position
	 * @param radius of the ball
	 * @return
	 */
	public Ball createBall(float x, float y, float radius) {
	    if (ball != null) {
	        PHYSICS_WORLD_INSTANCE.getBox2DWorld().destroyBody(ball.getBody());
	        drawableObjectsSet.remove(ball);
	        GlobalContactListener.getInstance().removeBody(ball.getBody());
	    }
	    ball = new Ball(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), x, y, radius, Constants.BALL_DENSITY, 
	                        Constants.BALL_FRICTION, Constants.BALL_RESTITUTION);
	    drawableObjectsSet.add(ball);
	    
	    return ball;
	}
	
	/**
	 * Get the ball
	 * @return
	 */
	public static Ball getBall() {
	    return ball;
	}
	
	/**
	 * Create a paddle in the world
	 * @param x position of the left bottom corner
	 * @param width of the paddle
	 * @param number of players in the paddle
	 * @param facingRight true if the paddle is right oriented
	 * @return
	 */
	public GroupPaddle createPaddle(float x, float width, int number, boolean facingRight) {
	    GroupPaddle groupPaddle = new GroupPaddle(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), x - width/2, width, 
	                                                number, facingRight);
	    for (Paddle paddle : groupPaddle.getPaddles()) {
	        drawableObjectsSet.add(paddle.getPlayer());
	        paddles.add(paddle);
	    }
	    
	    return groupPaddle;
	}
	
	/**
	 * Create a border
	 * @param x position of the bottom left corner
	 * @param y position of the bottom left corner
	 * @param width of the border
	 * @param height of the border
	 * @param teamOne true if the border is colored with teamOne parameter
	 * @return
	 */
	public Border createBorder(float x, float y, float width, float height, BorderType type) {
	    Border border = new Border(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), x, y, width, height, type);
	    drawableObjectsSet.add(border);
	    
	    return border;
	}
	
	/**
	 * Create the goal of team one or two
	 * @param teamOne true for team one
	 * @return
	 */
	public static Goal createGoal(float x, float y, float width, float height, GoalTeam team) {
	    Goal goal = new Goal(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), x, y, width, height, team);
	    
	    return goal;
	}
	
	private void addToDrawableSet(Drawable d) {
		drawableObjectsSet.add(d);
		java.util.Collections.sort(drawableObjectsSet, Drawable.DRAWABLE_COMPARATOR);
	}
	
	/**
	 * Creates a power up object.
	 * @param x X coordinate of the object.
	 * @param y Y coordinate of the object.
	 * @param hBoxRadius Radius of the power up hitbox.
	 * @return Power Up object.
	 */
	public PowerUpBody createPowerUp(float x, float y, float hBoxRadius) {
		pauseWorld();
		PowerUpBody powerUp = new PowerUpBody(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), x, y, hBoxRadius);
		startWorld();
		drawableObjectsSet.add(powerUp);
		return powerUp;
	}
	
	/**
	 * Creates a power up object in the centre of the field with a random y coordinate.
	 * @return Power Up object.
	 */
	public PowerUpBody createRandomPowerUp() {
		pauseWorld();
		float posY = (float) (Math.random() * Constants.WORLD_SIZE_Y);
		PowerUpBody powerUp = new PowerUpBody(PhysicsWorld.getPhysicsWorld().getBox2DWorld(), Constants.WORLD_SIZE_X/2,
				posY, Constants.POWERUP_RADIUS);
		startWorld();
		
		return powerUp;
	}
	
	public void destroy(DefaultWorldObject object) {
	    if (object.getBody().getFixtureList().first().getFilterData().categoryBits == Constants.CATEGORY_BALL) {
	        ball = null;
	    }
	    
	    drawableObjectsSet.remove(object);
	    objectsToDestroy.add(object);
	    GlobalContactListener.getInstance().removeBody(object.getBody());
	}
	
	public void destroy(GroupPaddle group) {
	    Iterator<Paddle> iterator = group.getPaddles().iterator();
	    while (iterator.hasNext()) {
	        destroy(iterator.next());
	    }
	}
	
	public void destroy(Paddle paddle) {
	    paddles.remove(paddle);
	    destroy(paddle.getPlayer());
	}
	
	public void clear() {
	    drawableObjectsSet.clear();
	    
	    Array<Body> bodies = new Array<Body>();
	    PhysicsWorld.getPhysicsWorld().getBox2DWorld().getBodies(bodies);
	    
	    for (Body body : bodies) {
	        PhysicsWorld.getPhysicsWorld().getBox2DWorld().destroyBody(body);
	        GlobalContactListener.getInstance().removeBody(body);
	    }
	    
	    objectsToDestroy.clear();
	    ball = null;
	    paddles.clear();
	}
	
	/**
	 * Returns the Box2D world.
	 * @return Box2D world.
	 */
	public World getBox2DWorld() {
	    return physicsWorld;
	}
	
	@Override
	public List<Drawable> toDraw() {
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
    			if (ball != null) {
    				checkVelocity(ball);
    			}
    			
    			for (Paddle paddle : paddles) {
    			    paddle.checkPosition();
    			}
    		}
    		
    		  		
			if (hostMode) {
				PlayerHost.sendHostData(new HostData(ball.getPositionX(), ball
						.getPositionY(), ball.getLinearVelocity().x, ball
						.getLinearVelocity().y));
			}
    		
    		
    		if (slaveMode && updated) {
    			ball.setBallPosition(networkBallX, networkBallY);
    			ball.setLinearVelocity(networkBallSpeedX, networkBallSpeedY);
    			updated = false;
    		}
    		
    		//For Accelerometer polling
    		accelerometerTime += correctedDelta;
    		if (accelerometerTime > Constants.SHAKE_TIMER && AccelerometerTracker.getInstance().isShaking()) {
        		accelerometerTime = 0;
        		
        		ball.setLinearVelocity(-AccelerometerTracker.getInstance().getmYGrav() * Constants.SHAKE_BOOST_RATIO, 
        		        AccelerometerTracker.getInstance().getmXGrav() * Constants.SHAKE_BOOST_RATIO);
    		} 	
    		
    		// Throw the events here because we need to do this outside the physic step
            EventManager.getEventManager().throwEvents();
            
            // Clear the body in a secure way
            throwDestroy();
	    }
	}
	
	/**
	 * Checks the velocity of the ball and limits it if the velocity of the ball is bigger
	 * than the maximum allowed.
	 * @param testedBall Ball to be checked.
	 */
	public void checkVelocity(Ball testedBall) {
		Vector2 ballVelocity = testedBall.getLinearVelocity();
		ballVelocity = ballVelocity.clamp(Constants.BALL_MIN_VELOCITY, Constants.BALL_MAX_VELOCITY);
		testedBall.setLinearVelocity(ballVelocity.x, ballVelocity.y);
	}
	
	/**
	 * Destroy all the bodies in the buffer
	 */
	public void throwDestroy() {
	    for (DefaultWorldObject object : objectsToDestroy) {
            physicsWorld.destroyBody(object.getBody());
        }
        objectsToDestroy.clear();
	}

	@Override
	public Rectangle regionToDraw() {
		return new Rectangle(Constants.WORLD_ORIGIN_X, Constants.WORLD_ORIGIN_Y, 
		        Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y);
	}

	@Override
	public void updateHostData(HostData data) {
		networkBallX = data.getmBallX();
		networkBallY = data.getmBallY();
		networkBallSpeedX = data.getmBallSpeedX();
		networkBallSpeedY = data.getmBallSpeedY();
		updated = true;
	}

	@Override
	public void gameClientStart() {
		slaveMode = true;
	}

	@Override
	public void updateClientData(InputData data) {
		//TODO take care of shake input
	}

	@Override
	public void gameHostStart() {
		hostMode = true;
	}

	@Override
	public void updateHostTouchData(InputData data) {
		//Do nothing
	}

	@Override
	public void shapeHasChanged() {
		for (Paddle paddle : paddles) {
			paddle.changePlayerFixture();
		}
	}

}
