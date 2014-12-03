package ch.epfl.sweng.androfoot.players.ai;

/**
 * Interface for listeners of the AIEngine.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public interface AIObserver {
	/**
	 * Called when new frame is about to be rendered. This function allows to
	 * make game logic operation that depend on the frame rate.
	 * 
	 * @param deltaTime
	 *            time ellapsed since last frame.
	 */
	void update(float deltaTime);
}
