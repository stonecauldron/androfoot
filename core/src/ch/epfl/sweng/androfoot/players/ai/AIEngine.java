package ch.epfl.sweng.androfoot.players.ai;

/**
 * An observable class handling AI updates, it is implemented as a singleton.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AIEngine {
	
	private static AIEngine instance;
	
	private AIEngine() {}
	
	/**
	 * Gets the active instance of the active AI engine.
	 * @return the singleton instance of the AI engine.
	 */
	public static AIEngine getInstance() {
		if (instance == null) {
			instance = new AIEngine();
		}
		return instance;
	}
}
