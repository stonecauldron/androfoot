package ch.epfl.sweng.androfoot.gamelogic;

import com.badlogic.gdx.graphics.Color;

import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;

/**
 * A static object to retrieve the actual shape of players The shape can be
 * different for team1 and team2 This class uses double singleton pattern
 * 
 * @author Guillame Leclerc
 *
 */
public class PlayerCharacteristicsManager {

	private static final float DEFAULT_PADDLE_HEIGHT_CIRCLE = 0.2f;
	private static final float DEFAULT_PADDLE_HEIGHT_RECTANGLE = 0.08f;
	private static final float DEFAULT_PADDLE_WIDTH = 0.8f;

	private static PaddleGenerator paddleGeneratorInstanceTeam1 = null;
	private static PaddleGenerator paddleGeneratorInstanceTeam2 = null;

	public static final Color[] TEAM_COLORS_HEX = new Color[] {
			new Color(0xFF2020FF), new Color(0x4040FFFF),
			new Color(0x40E080FF), new Color(0xC040FFFF),
			new Color(0x00FFFFFF), new Color(0xFF8040FF),
			new Color(0xE0E040FF), new Color(0xFFFFFFFF) };

	public static Color getColorTeam1() {
		return TEAM_COLORS_HEX[Configuration.getInstance().getPlayerOneTeam()];
	}

	public static Color getColorTeam2() {
		return TEAM_COLORS_HEX[Configuration.getInstance().getPlayerTwoTeam()];
	}

	/**
	 * Get the shape for players of team 1
	 * 
	 * @return the {@link PaddleGenerator} to generate the shape
	 */
	public static PaddleGenerator getInstanceTeam1() {
		if (paddleGeneratorInstanceTeam1 == null) {
			paddleGeneratorInstanceTeam1 = new PaddleGenerator(
							DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE,
							DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam1;
	}

	/**
	 * Get the shape for players of team 2
	 * 
	 * @return the {@link PaddleGenerator} to generate the shape
	 */
	public static PaddleGenerator getInstanceTeam2() {
		if (paddleGeneratorInstanceTeam2 == null) {
			paddleGeneratorInstanceTeam2 = new PaddleGenerator(
							DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE,
							DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam2;
	}

	public static void setShapeParamsPlayer1(float heightShootZone,
					float heightControlZone, float width) {
		paddleGeneratorInstanceTeam1 = new PaddleGenerator(width,
						heightShootZone, heightControlZone);
		warnShapeHasChanged();
	}

	private static void warnShapeHasChanged() {
		GraphicEngine.getEngine().shapeHasChanged();
		PhysicsWorld.getPhysicsWorld().shapeHasChanged();
	}

	public static void setShapeParamsPlayer2(float heightShootZone,
					float heightControlZone, float width) {
		paddleGeneratorInstanceTeam2 = new PaddleGenerator(width,
						heightShootZone, heightControlZone);
		warnShapeHasChanged();
	}

	public static void setShapeParamsDefaultPlayer1() {
		paddleGeneratorInstanceTeam1 = null;
		warnShapeHasChanged();
	}

	public static void setShapeParamsDefaultPlayer2() {
		paddleGeneratorInstanceTeam2 = null;
		warnShapeHasChanged();
	}
}
