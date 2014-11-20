package ch.epfl.sweng.androfoot.players;

import com.badlogic.gdx.math.MathUtils;

public class RandomAI extends AbstractAIPlayer {

	private static final int MAX_DELTA_X = 0;
	private static final int MAX_DELTA_Y = 0;
	private static final int MAX_SPEED_FACTOR = 0;

	RandomAI(PlayerNumber number) {
		super(number);
	}
	
	private void actRandomly() {
		float deltaX = MathUtils.random(MAX_DELTA_X) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;
		float deltaY = MathUtils.random(MAX_DELTA_Y) * MathUtils.randomSign()
				* MAX_SPEED_FACTOR;

		move(deltaX, deltaY);
	}

}
