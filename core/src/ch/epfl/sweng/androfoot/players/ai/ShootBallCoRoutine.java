package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.utils.CoRoutine;

public class ShootBallCoRoutine implements CoRoutine {

	private static final int MAX_SHOOTING_SPEED = 10;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.SHOOT);

	public ShootBallCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
	}

	@Override
	public void execute() {
		shoot();
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	public void shoot() {
		int speed = mPaddles
				.takeIntoAccountPlayerNumberInRelationToXSpeed(MAX_SHOOTING_SPEED);
		mPaddles.moveHorizontally(speed);
	}
}
