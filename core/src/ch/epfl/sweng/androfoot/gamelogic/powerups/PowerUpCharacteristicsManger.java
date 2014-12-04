package ch.epfl.sweng.androfoot.gamelogic.powerups;

import com.badlogic.gdx.graphics.Color;

import ch.epfl.sweng.androfoot.polygongenerator.PowerUpPolygonGenerator;

public class PowerUpCharacteristicsManger {
	
	private static final float POWERUP_BRANCH_WIDHT = 0.4f;
	private static final int POWERUP_BRANCH_NUMBER = 4; 
	private static final int POWER_UP_COLOR_HEX = 0xFF52E8FF;
	private static final Color POWER_UP_COLOR = new Color(POWER_UP_COLOR_HEX);
	private static final float POWER_UP_RADIUS = 0.3f;
	
	private static PowerUpPolygonGenerator powerupShape = null;
	
	public static PowerUpPolygonGenerator getPowerUpShape() {
		if(powerupShape == null) {
			powerupShape = new PowerUpPolygonGenerator(POWER_UP_RADIUS, POWERUP_BRANCH_NUMBER, POWERUP_BRANCH_WIDHT);
		}
		return powerupShape;
	}
	
	public static Color getPowerUpColor() {
		return POWER_UP_COLOR;
	}
}