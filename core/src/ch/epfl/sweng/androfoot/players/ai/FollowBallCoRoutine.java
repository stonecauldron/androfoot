package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.utils.CoRoutine;

/**
 * Class to represent the follow ball behaviour.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class FollowBallCoRoutine implements CoRoutine {

	private static final int MAX_DELTA_Y = 10;
	private static final float TOLERANCE = 0.01f;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.DEFAULT);

	FollowBallCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
	}

	@Override
	public void execute() {
		followBall();
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	private void followBall() {
		float playerY = mPaddles.GetYPositionOfPlayerThatCanReachTheBall();
		float ballY = mPaddles.GetYPositionOfPlayerThatCanReachTheBall();

		// player is in front of ball
		if (Math.abs(ballY - playerY) < TOLERANCE) {
			// do not move
			mPaddles.move(0, 0);
		} else {
			if (playerY > ballY) {
				// go down
				mPaddles.moveVertically(-MAX_DELTA_Y);
			} else if (playerY < ballY) {
				// go up
				mPaddles.moveVertically(MAX_DELTA_Y);
			}
		}
	}

}
