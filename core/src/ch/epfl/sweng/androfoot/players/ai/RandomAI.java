package ch.epfl.sweng.androfoot.players.ai;

import ch.epfl.sweng.androfoot.players.PlayerNumber;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Represents a completely random AI
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class RandomAI extends AbstractAIPlayer {

	private static final float RANDOM_TIMER = 0.5f;

	public RandomAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(RANDOM_TIMER), new ActRandomlyCoRoutine(this));
	}
}
