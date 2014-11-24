package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.players.ai.coroutines.ActRandomlyCoRoutine;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Represents a completely random AI
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class RandomAI extends AbstractAIPlayer {

	RandomAI(PlayerNumber number) {
		super(number);

		addToCoRoutines(new Timer(0.5f), new ActRandomlyCoRoutine(this));
	}
}