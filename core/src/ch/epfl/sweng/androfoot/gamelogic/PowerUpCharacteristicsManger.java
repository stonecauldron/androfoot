package ch.epfl.sweng.androfoot.gamelogic;

import com.badlogic.gdx.graphics.Color;

import ch.epfl.sweng.androfoot.polygongenerator.PowerUpPolygonGenerator;

public class PowerUpCharacteristicsManger {
	
	private static final float POWERUP_BRANCH_WIDHT = 0.1f;
	private static final int POWERUP_BRANCH_NUMBER = 1; 
	private static final int POWER_UP_COLOR_HEX = 0xFF52E8FF;
	private static final Color POWER_UP_COLOR = new Color(POWER_UP_COLOR_HEX);
	
	private static PowerUpPolygonGenerator powerupShape = null;
	
	public static PowerUpPolygonGenerator getPowerUpShape() {
		if(powerupShape == null) {
			powerupShape = new PowerUpPolygonGenerator(POWERUP_BRANCH_NUMBER, POWERUP_BRANCH_WIDHT);
			float[] result = powerupShape.generateVertexesFloat();
			System.out.println("test");
		}
		return powerupShape;
	}
	
	public static Color getPowerUpColor() {
		return POWER_UP_COLOR;
	}
}