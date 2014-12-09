package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.utils.CoRoutine;

/**
 * Represents the behaviour when the player goes to the position furthest away
 * from the goal.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class RetreatCoRoutine implements CoRoutine {

	private static final int RETREAT_SPEED = 10;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.RETREAT);

	public RetreatCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
	}

	@Override
	public void execute() {
		retreat();
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	public void retreat() {
		int speed = mPaddles
				.takeIntoAccountPlayerNumberInRelationToXSpeed(RETREAT_SPEED);
		mPaddles.moveHorizontally(-speed);
	}

}
