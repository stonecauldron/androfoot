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
		float ballY = PhysicsWorld.getPhysicsWorld().getBall().getPositionY();
		
		float yDistanceFromPlayerToBall = Math.abs(ballY - playerY); 
		
		// player is in front of ball
		if (yDistanceFromPlayerToBall < TOLERANCE) {
			// do not move
			mPaddles.move(0, 0);
		} else {
			// compute speed factor
			float speedFactor = yDistanceFromPlayerToBall/DISTANCE_FACTOR;
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
