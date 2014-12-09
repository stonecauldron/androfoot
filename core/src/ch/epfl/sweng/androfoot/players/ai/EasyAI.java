package ch.epfl.sweng.androfoot.players.ai;

import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Class to represent an AI in easy difficulty
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class EasyAI extends AbstractAIPlayer {

	private static final float FOLLOW_BALL_TIMER = 0.1f;
	private static final float DISTANCE_FACTOR = 5f;

	public EasyAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(FOLLOW_BALL_TIMER), new FollowBallCoRoutine(
				this, DISTANCE_FACTOR));
	}
}
