package ch.epfl.sweng.androfoot.players.ai;

import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Represents an AI on hard difficulty setting
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class HardAI extends AbstractAIPlayer {

	private static final float FOLLOW_BALL_TIMER = 0.001f;
	private static final float DISTANCE_FACTOR = 1.75f;

	public HardAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(FOLLOW_BALL_TIMER), new FollowBallCoRoutine(
				this, DISTANCE_FACTOR));
	}

}
