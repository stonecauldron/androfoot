package ch.epfl.sweng.androfoot.gamelogic.powerups;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.PowerUpEffect;

/**
 * Represent a powerup which change the size of the ball
 * 
 * @author Guillaume
 *
 */
public class BallSizePowerUp implements PowerUpEffect {

	private final static float DURATION = 30f;
	private final static float RADIUS_FACTOR_REDUCTION = 0.5f;
	private final static float RADIUS_FACTOR_AUGMENTATION = 2f;
	private final static float NEW_SMALL_RADIUS = Constants.BALL_RADIUS
					* RADIUS_FACTOR_REDUCTION;
	private final static float NEW_BIG_RADIUS = Constants.BALL_RADIUS
					* RADIUS_FACTOR_AUGMENTATION;

	private final boolean isBig;

	BallSizePowerUp(boolean bigArg) {
		isBig = bigArg;
	}

	@Override
	public float getEffectDuration() {
		return DURATION;
	}

	@Override
	public void begin(boolean isTeam1) {
		if (isBig) {
			PhysicsWorld.getPhysicsWorld().getBall()
							.changeFixture(NEW_BIG_RADIUS);
		} else {
			PhysicsWorld.getPhysicsWorld().getBall()
							.changeFixture(NEW_SMALL_RADIUS);
		}
	}

	@Override
	public void end() {
		PhysicsWorld.getPhysicsWorld().getBall()
						.changeFixture(Constants.BALL_RADIUS);
	}

	@Override
	public PowerUpEffect copy() {
		return new BallSizePowerUp(isBig);
	}

}
