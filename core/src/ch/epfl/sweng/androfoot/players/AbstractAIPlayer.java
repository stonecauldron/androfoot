package ch.epfl.sweng.androfoot.players;

import java.util.HashMap;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.Paddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.players.ai.AIObserver;
import ch.epfl.sweng.androfoot.players.ai.AIState;
import ch.epfl.sweng.androfoot.utils.CoRoutine;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Class to represent a generic AI controlled player.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public abstract class AbstractAIPlayer extends AbstractPlayer implements
		Controllable, AIObserver {

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
		mState = AIState.DEFAULT;
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

	float GetYPositionOfPlayerThatCanReachTheBall() {
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

	protected void addToCoRoutines(Timer timer, CoRoutine coRoutine) {
		coRoutinesMap.put(timer, coRoutine);
	}

	protected void removeFromCoRoutines(Timer timer) {
		coRoutinesMap.remove(timer);
	}

	protected void setState(AIState state) {
		mState = state;
	}

	private boolean ballIsGoingTowardsDefense() {
		float ballXSpeed = PhysicsWorld.getPhysicsWorld().getBall()
				.getLinearVelocity().x;

		return takeIntoAccountPlayerNumber(ballXSpeed <= 0);
	}

	private boolean ballIsAheadOfAttack() {
		float ballXPosition = PhysicsWorld.getPhysicsWorld().getBall()
				.getPositionX();
		// get x coordinate of the defense row
		float defenseXPosition = getAttackPaddles().getPaddles().get(0)
				.getPlayer().getPositionX();

		return takeIntoAccountPlayerNumber(ballXPosition > defenseXPosition);

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
