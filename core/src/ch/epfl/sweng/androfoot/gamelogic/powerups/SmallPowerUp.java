package ch.epfl.sweng.androfoot.gamelogic.powerups;

import ch.epfl.sweng.androfoot.gamelogic.PlayerCharacteristicsManager;
import ch.epfl.sweng.androfoot.interfaces.PowerUpEffect;

public class SmallPowerUp implements PowerUpEffect {

	private static final float DURATION = 15f;
	private static final float WIDTH = 0.3f;
	private static final float HEIGHT_SHOOT = 0.07f;
	private static final float HEIGHT_CONTROL = 0.2f;

	private boolean isTeam1 = true;

	@Override
	public float getEffectDuration() {
		return DURATION;
	}

	@Override
	public void begin(boolean isTeam1Arg) {
		isTeam1 = isTeam1Arg;
		if (isTeam1) {
			PlayerCharacteristicsManager.setShapeParamsPlayer1(HEIGHT_SHOOT,
					HEIGHT_CONTROL, WIDTH);
		} else {
			PlayerCharacteristicsManager.setShapeParamsPlayer2(HEIGHT_SHOOT,
					HEIGHT_CONTROL, WIDTH);
		}
	}

	@Override
	public void end() {
		if (isTeam1) {
			PlayerCharacteristicsManager.setShapeParamsDefaultPlayer1();
		} else {
			PlayerCharacteristicsManager.setShapeParamsDefaultPlayer2();
		}
	}
	@Override
	public PowerUpEffect copy() {
		return new SmallPowerUp();
	}

}
