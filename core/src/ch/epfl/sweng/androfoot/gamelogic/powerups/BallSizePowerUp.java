package ch.epfl.sweng.androfoot.gamelogic.powerups;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.PowerUpEffect;

public class BallSizePowerUp implements PowerUpEffect {
	
	private final static float DURATION = 30f;
	private final static float RADIUS_FACTOR_REDUCTION = 0.5f;
	private final static float NEW_RADIUS = Constants.BALL_RADIUS*RADIUS_FACTOR_REDUCTION;

	@Override
	public float getEffectDuration() {
		return DURATION;
	}

	@Override
	public void begin(boolean isTeam1) {
		PhysicsWorld.getBall().changeFixture(NEW_RADIUS);
	}

	@Override
	public void end() {
		PhysicsWorld.getBall().changeFixture(Constants.BALL_RADIUS);
	}

	@Override
	public PowerUpEffect copy() {
		return new BallSizePowerUp();
	}

}
