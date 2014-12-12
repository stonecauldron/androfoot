package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.utils.CoRoutine;

/**
 * CoRoutine for the shooting behaviour.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class ShootBallCoRoutine implements CoRoutine {

	private static final int MAX_SHOOTING_SPEED = 4;
	
	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.SHOOT);

	public ShootBallCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
	}

	@Override
	public void execute() {
		shoot();
		if (mPaddles.isDeadLocked()) {
			mPaddles.setState(AIState.RANDOM);
		} else {
			mPaddles.setState(AIState.DEFENSE);
		}
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	private void shoot() {
		int speed = mPaddles
				.takeIntoAccountPlayerNumberInRelationToXSpeed(MAX_SHOOTING_SPEED);
		mPaddles.moveHorizontally(speed);
	}
}
