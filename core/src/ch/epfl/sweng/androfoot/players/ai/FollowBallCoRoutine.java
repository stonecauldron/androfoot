package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.utils.CoRoutine;

/**
 * Class to represent the follow ball behaviour.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class FollowBallCoRoutine implements CoRoutine {

	private static final int MAX_DELTA_Y = 10;
	private static final float TOLERANCE = 0.1f;
	private static final float DISTANCE_FACTOR = 1.75f;

	private float mDistanceFactor;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.DEFENSE, AIState.SHOOT);

	FollowBallCoRoutine(AbstractAIPlayer paddles) {
		mPaddles = paddles;
		mDistanceFactor = DISTANCE_FACTOR;
	}

	FollowBallCoRoutine(AbstractAIPlayer paddles, float distanceFactor) {
		mPaddles = paddles;
		mDistanceFactor = distanceFactor;
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
		float playerY = mPaddles.getYPositionOfPlayerThatCanReachTheBall();
		float ballY = PhysicsWorld.getPhysicsWorld().getBall().getPositionY();

		float yDistanceFromPlayerToBall = Math.abs(ballY - playerY);

		// player is in front of ball
		if (yDistanceFromPlayerToBall < TOLERANCE) {
			// do not move
			mPaddles.moveVertically(0);
		} else {
			// compute speed factor
			float speedFactor = yDistanceFromPlayerToBall / mDistanceFactor;
			if (playerY > ballY) {
				// go down
				mPaddles.moveVertically(-MAX_DELTA_Y * speedFactor);
			} else if (playerY < ballY) {
				// go up
				mPaddles.moveVertically(MAX_DELTA_Y * speedFactor);
			}
		}
	}

}
