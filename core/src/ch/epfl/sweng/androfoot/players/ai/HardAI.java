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
	private static final float SHOOT_TIMER = 0.25f;
	private static final float RETREAT_TIMER = 0.1f;
	private static final float RANDOM_TIMER = 0.5f;

	public HardAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(FOLLOW_BALL_TIMER), new FollowBallCoRoutine(
				this, DISTANCE_FACTOR));
		addToCoRoutines(new Timer(SHOOT_TIMER), new ShootBallCoRoutine(this));
		addToCoRoutines(new Timer(RETREAT_TIMER), new RetreatCoRoutine(this));
		addToCoRoutines(new Timer(RANDOM_TIMER), new ActRandomlyWhenDeadLocked(this));
	}

}
