package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.players.ai.AIObserver;

import com.badlogic.gdx.math.MathUtils;

/**
 * Class to represent an AI controlled player.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AIPlayer extends AbstractPlayer implements Controllable,
		AIObserver {

	private static final float EXECUTION_INTERVAL = 0.5f;
	private static final float RESET_TIME = 0;

	private static final int MAX_DELTA_X = 10;
	private static final int MAX_DELTA_Y = 10;

	private static final float MAX_SPEED_FACTOR = 0.9f;

	private float timer;

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
		for (GroupPaddle paddles : getPaddles()) {
			paddles.setVelocity(deltaX, deltaY);
		}
	}

	@Override
	public void update(float deltaTime) {
		// increment timer
		timer += deltaTime;

		if (timer >= EXECUTION_INTERVAL) {
			actRandomly();

			// reset timer
			timer = RESET_TIME;
		}
	}

	private void actRandomly() {
		float deltaX = MathUtils.random(MAX_DELTA_X) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;
		float deltaY = MathUtils.random(MAX_DELTA_Y) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;

		move(deltaX, deltaY);
	}

}
