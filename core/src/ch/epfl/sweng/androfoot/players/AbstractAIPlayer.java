package ch.epfl.sweng.androfoot.players;

import java.util.HashMap;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
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
public abstract class AbstractAIPlayer extends AbstractPlayer implements Controllable,
		AIObserver {

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

	protected void addToCoRoutines(Timer timer, CoRoutine coRoutine) {
		coRoutinesMap.put(timer, coRoutine);
	}

	protected void removeFromCoRoutines(Timer timer) {
		coRoutinesMap.remove(timer);
	}

	protected void setState(AIState state) {
		mState = state;
	}

}
