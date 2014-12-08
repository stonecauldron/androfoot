package ch.epfl.sweng.androfoot.players.ai;

import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.utils.Timer;

public class MediumAI extends AbstractAIPlayer {

	private static final float FOLLOW_BALL_TIMER = 0.001f;
	private static final float DISTANCE_FACTOR = 2f;

	public MediumAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(FOLLOW_BALL_TIMER), new FollowBallCoRoutine(
				this, DISTANCE_FACTOR));
		addToCoRoutines(new Timer(0.5f), new ShootBallCoRoutine(this));
		addToCoRoutines(new Timer(0.1f), new RetreatCoRoutine(this));
	}
}
