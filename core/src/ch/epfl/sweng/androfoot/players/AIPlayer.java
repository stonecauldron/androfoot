package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.players.ai.AIObserver;

/**
 * Class to represent an AI controlled player.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AIPlayer extends AbstractPlayer implements Controllable,
		AIObserver {

	AIPlayer(PlayerNumber number) {
		super(number);

		// start observing AIEngine
		AIEngine.getInstance().addAIObserver(this);
	}

	@Override
	public void moveHorizontally(float deltaX) {
		// TODO Auto-generated method stub
	}

	@Override
	public void moveVertically(float deltaY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void move(float deltaX, float deltaY) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
	}

}
