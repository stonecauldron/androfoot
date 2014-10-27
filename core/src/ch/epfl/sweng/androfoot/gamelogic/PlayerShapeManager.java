package ch.epfl.sweng.androfoot.gamelogic;

import ch.epfl.sweng.androfoot.polygongenerator.PaddleGenerator;

public class PlayerShapeManager {
	
	private static float DEFAULT_PADDLE_HEIGHT_CIRCLE = 0.25f;
	private static float DEFAULT_PADDLE_HEIGHT_RECTANGLE = 0.1f;
	private static float DEFAULT_PADDLE_WIDTH = 1f;

	private static PaddleGenerator paddleGeneratorInstanceTeam1 = null;
	private static PaddleGenerator paddleGeneratorInstanceTeam2 = null;
	
	public static PaddleGenerator getInstanceTeam1() {
		if(paddleGeneratorInstanceTeam1 == null) {
			paddleGeneratorInstanceTeam1 = new PaddleGenerator(
					DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE, DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam1;
	}
	
	public static PaddleGenerator getInstanceTeam2() {
		if(paddleGeneratorInstanceTeam2 == null) {
			paddleGeneratorInstanceTeam1 = new PaddleGenerator(
					DEFAULT_PADDLE_WIDTH, DEFAULT_PADDLE_HEIGHT_CIRCLE, DEFAULT_PADDLE_HEIGHT_RECTANGLE);
		}
		return paddleGeneratorInstanceTeam2;
	}
}
