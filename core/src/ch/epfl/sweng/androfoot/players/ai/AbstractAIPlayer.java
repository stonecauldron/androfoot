package ch.epfl.sweng.androfoot.players.ai;

import java.util.HashMap;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.players.AbstractPlayer;
import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.utils.CoRoutine;
import ch.epfl.sweng.androfoot.utils.Timer;

import com.badlogic.gdx.math.Vector2;

/**
 * Class to represent a generic AI controlled player.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractAIPlayer extends AbstractPlayer implements
		Controllable, AIObserver {

	// tolerance for the floating point comparisons
	private static float TOLERANCE = 0.0000000000000000000000000000000000000001f;
	
	// deadlock checks
	private static final float DEADLOCK_TOLERANCE = 0.1f;
	private static final int MAX_NB_DEADLOCKS = 10;

	private Vector2 previousBallPosition;
	private int deadlockCounter;
	
	// HashMap linking Timers with CoRoutines
	private HashMap<Timer, CoRoutine> coRoutinesMap;

	// current state of the AI
	private AIState mState;


	public AbstractAIPlayer(PlayerNumber number) {
		super(number);

		// start observing AIEngine
		AIEngine.getInstance().addAIObserver(this);

		// initialize HashMap
		coRoutinesMap = new HashMap<Timer, CoRoutine>();

		// set AI to default state
		mState = AIState.DEFENSE;
		
		previousBallPosition = new Vector2();
	}

	@Override
	public void moveHorizontally(float deltaX) {
		for (GroupPaddle paddles : getPaddles()) {
			paddles.setXVelocity(deltaX);
		}
	}

	@Override
	public void moveVertically(float deltaY) {
		for (GroupPaddle paddles : getPaddles()) {
			paddles.setYVelocity(deltaY);
		}
	}

	@Override
	public void move(float deltaX, float deltaY) {
		for (GroupPaddle paddles : getPaddles()) {
			paddles.setVelocity(deltaX, deltaY);
		}
	}

	@Override
	public void update(float deltaTime) {
		updateTimers(deltaTime);
	}

	/**
	 * Updates all the timers associated with coroutines and execute them if
	 * given timers tick.
	 * 
	 * @param deltaTime
	 *            the time elapsed since the last frame call
	 */
	public void updateTimers(float deltaTime) {
		// iterate over the list of timers
		for (Timer timer : coRoutinesMap.keySet()) {
			timer.updateTimer(deltaTime);

			// if a timer ticks
			if (timer.checkTimer()) {
				// execute coRoutine if state of the AI coincides
				if (coRoutinesMap.get(timer)
						.getStatesWhereCoRoutineIsExecutable().contains(mState)) {
					coRoutinesMap.get(timer).execute();
				}
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		AIEngine.getInstance().removeAIObserver(this);
	}

	float getYPositionOfPlayerThatCanReachTheBall() {
		int playerIndex;
		float y;
		if (ballIsAheadOfAttack()) {
			playerIndex = getIndexOfPlayerThatCanReachBall(getNumberOfAttackers());
			y = getAttackPaddles().getPaddles().get(playerIndex).getPlayer()
					.getPositionY();
		} else {
			playerIndex = getIndexOfPlayerThatCanReachBall(getNumberOfDefensors());
			y = getDefensePaddles().getPaddles().get(playerIndex).getPlayer()
					.getPositionY();
		}
		return y;
	}

	float getXPositionOfPlayerThatCanReachTheBall() {
		int playerIndex;
		float x;
		if (ballIsAheadOfAttack()) {
			playerIndex = getIndexOfPlayerThatCanReachBall(getNumberOfAttackers());
			x = getAttackPaddles().getPaddles().get(playerIndex).getPlayer()
					.getPositionX();
		} else {
			playerIndex = getIndexOfPlayerThatCanReachBall(getNumberOfDefensors());
			x = getDefensePaddles().getPaddles().get(playerIndex).getPlayer()
					.getPositionX();
		}
		return x;
	}

	float getPlayerHeight() {
		return getPaddles().get(0).getPaddles().get(0).getPlayer()
				.getSemiHeight();
	}

	boolean playerCanShootBall() {
		Vector2 ballSpeed = PhysicsWorld.getPhysicsWorld().getBall()
				.getLinearVelocity();
		if (ballSpeed.y < TOLERANCE || ballSpeed.x < TOLERANCE) {
			// see if ball can be reached in x
			float ballX = PhysicsWorld.getPhysicsWorld().getBall()
					.getPositionX();
			float playerX = getXPositionOfPlayerThatCanReachTheBall();
			float paddleWidth = getPaddles().get(0).getPaddles().get(0)
					.getWidth();
			float ballRadius = PhysicsWorld.getPhysicsWorld().getBall()
					.getRadius();
			// compute correct ball radius
			if (getPlayerNumber() == PlayerNumber.ONE) {
				ballRadius = -ballRadius;
			}

			// see if ball can be reached in y
			float ballY = PhysicsWorld.getPhysicsWorld().getBall()
					.getPositionY();
			float playerY = getYPositionOfPlayerThatCanReachTheBall();

			boolean canReachInYAxis = Math.abs(playerY - ballY) <= getPlayerHeight() * 2;
			boolean canReachInXAxis = Math.abs((ballX + ballRadius) - playerX) <= paddleWidth;

			// check whether player can reach ball;
			return canReachInXAxis && canReachInYAxis;
		}
		return false;
	}

	boolean isDeadLocked() {
		Vector2 currentBallPosition = getBallPosition();

		boolean hasSameXCoordinate = Math.abs(currentBallPosition.x
				- previousBallPosition.x) < DEADLOCK_TOLERANCE;
		boolean hasSameYCoordinate = Math.abs(currentBallPosition.y
				- previousBallPosition.y) < DEADLOCK_TOLERANCE;

		if (hasSameXCoordinate || hasSameYCoordinate) {
			deadlockCounter++;
		} else {
			deadlockCounter = 0;
		}

		// store currentPosition
		previousBallPosition = currentBallPosition;

		return deadlockCounter > MAX_NB_DEADLOCKS;
	}

	protected void addToCoRoutines(Timer timer, CoRoutine coRoutine) {
		coRoutinesMap.put(timer, coRoutine);
	}

	protected void removeFromCoRoutines(Timer timer) {
		coRoutinesMap.remove(timer);
	}

	protected void setState(AIState state) {
		mState = state;
	}

	protected int takeIntoAccountPlayerNumberInRelationToXSpeed(int speed) {
		if (getPlayerNumber() == PlayerNumber.TWO) {
			return -speed;
		} else {

			return speed;
		}
	}

	protected boolean ballIsAheadOfAttack() {
		float ballXPosition = PhysicsWorld.getPhysicsWorld().getBall()
				.getPositionX();
		// get x coordinate of the defense row
		float defenseXPosition = getAttackPaddles().getPaddles().get(0)
				.getPlayer().getPositionX();

		return takeIntoAccountPlayerNumber(ballXPosition > defenseXPosition);

	}

	private Vector2 getBallPosition() {
		float ballX = PhysicsWorld.getPhysicsWorld().getBall().getPositionX();
		float ballY = PhysicsWorld.getPhysicsWorld().getBall().getPositionY();
	
		return new Vector2(ballX, ballY);
	}

	private boolean takeIntoAccountPlayerNumber(boolean bool) {
		if (getPlayerNumber() == PlayerNumber.TWO) {
			bool = !bool;
		}
		return bool;
	}

	private int getIndexOfPlayerThatCanReachBall(int totalNumber) {
		float ballYPosition = PhysicsWorld.getPhysicsWorld().getBall()
				.getPositionY();

		float heightFactor = Constants.WORLD_SIZE_Y / totalNumber;

		float lowerBound = 0;
		float upperBound = heightFactor;

		int playerIndex = 0;

		while (!(ballYPosition >= lowerBound && ballYPosition <= upperBound)) {
			lowerBound += heightFactor;
			upperBound += heightFactor;
			playerIndex++;
		}
		return playerIndex;
	}
}
