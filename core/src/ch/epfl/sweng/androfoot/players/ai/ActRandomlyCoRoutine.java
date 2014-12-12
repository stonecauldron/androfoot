package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.utils.CoRoutine;

import com.badlogic.gdx.math.MathUtils;

/**
 * Class to represent a random behaviour coroutine.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class ActRandomlyCoRoutine implements CoRoutine {

	private static final int MAX_DELTA_X = 10;
	private static final int MAX_DELTA_Y = 10;
	private static final float MAX_SPEED_FACTOR = 0.9f;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.RANDOM);

	ActRandomlyCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
	}

	@Override
	public void execute() {
		actRandomly();
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}
	
	protected AbstractAIPlayer getPaddles() {
		return mPaddles;
	}
	
	private void actRandomly() {
		float deltaX = MathUtils.random(MAX_DELTA_X) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;
		float deltaY = MathUtils.random(MAX_DELTA_Y) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;

		mPaddles.move(deltaX, deltaY);
	}

}
