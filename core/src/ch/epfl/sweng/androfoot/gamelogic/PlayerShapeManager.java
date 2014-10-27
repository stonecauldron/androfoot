package ch.epfl.sweng.androfoot.gamelogic;

import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;

/**
 * A static object to retrieve the actual shape of players 
 * The shape can be different for team1 and team2
 * This class uses double singleton pattern
 * @author Guillame Leclerc
 *
 */
public class PlayerShapeManager {
	
	private static float DEFAULT_PADDLE_HEIGHT_CIRCLE = 0.25f;
	private static float DEFAULT_PADDLE_HEIGHT_RECTANGLE = 0.1f;
	private static float DEFAULT_PADDLE_WIDTH = 1f;

	private static PaddleGenerator paddleGeneratorInstanceTeam1 = null;
	private static PaddleGenerator paddleGeneratorInstanceTeam2 = null;
	
	/**
	 * Get the shape for players of team 1
	 * @return the {@link PaddleGenerator} to generate the shape
	 */
	public static PaddleGenerator getInstanceTeam1() {
		if(paddleGeneratorInstanceTeam1 == null) {
			paddleGeneratorInstanceTeam1 = new PaddleGenerator(
					DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE, DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam1;
	}
	
	/**
	 * Get the shape for players of team 2
	 * @return the {@link PaddleGenerator} to generate the shape
	 */
	public static PaddleGenerator getInstanceTeam2() {
		if(paddleGeneratorInstanceTeam2 == null) {
			paddleGeneratorInstanceTeam2 = new PaddleGenerator(
					DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE, DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam2;
	}
}