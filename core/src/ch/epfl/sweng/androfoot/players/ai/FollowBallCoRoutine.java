package ch.epfl.sweng.androfoot.players.ai;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.utils.CoRoutine;

import com.badlogic.gdx.math.MathUtils;

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
	private static final float GOAL_Y_POSITION = Constants.WORLD_SIZE_Y / 2;

	private float mDistanceFactor;

	private AbstractAIPlayer mPaddles;

	private List<AIState> authorizedStates = Arrays.asList(AIState.DEFENSE);

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
		
		if (mPaddles.playerCanShootBall()) {
			mPaddles.setState(AIState.SHOOT);
		}
	}

	@Override
	public List<AIState> getStatesWhereCoRoutineIsExecutable() {
		return authorizedStates;
	}

	private void followBall() {
		float playerY = mPaddles.getYPositionOfPlayerThatCanReachTheBall();
		float ballY = PhysicsWorld.getPhysicsWorld().getBall().getPositionY();

		float playerHeight = mPaddles.getPlayerHeight();

		// offset should not be higher than height of player
		float offset = MathUtils.random(playerHeight);

		// offset center of player to allow targeted shooting at goal
		if (playerY > GOAL_Y_POSITION) {
			offset = -offset;
		} else if (Math.abs(playerY - GOAL_Y_POSITION) < TOLERANCE) {
			offset = 0;
		}
		float yDistanceFromPlayerToBall = Math.abs(ballY - (playerY + offset));

		// player is in front of ball
		if (yDistanceFromPlayerToBall < TOLERANCE) {
			// do not move
			mPaddles.moveVertically(0);
		} else {
			// compute speed factor
			float speedFactor = yDistanceFromPlayerToBall / mDistanceFactor;
			if ((playerY + offset) > ballY) {
				// go down
				mPaddles.moveVertically(-MAX_DELTA_Y * speedFactor);
			} else if ((playerY + offset) < ballY) {
				// go up
				mPaddles.moveVertically(MAX_DELTA_Y * speedFactor);
			}
		}
	}
}
