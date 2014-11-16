package ch.epfl.sweng.androfoot.players.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * An observable class handling AI updates, it is implemented as a singleton.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class AIEngine {

	private static AIEngine instance;

	// the list holding the observers
	private List<AIObserver> observers;

	private AIEngine() {
		observers = new ArrayList<AIObserver>();
	}

	/**
	 * Gets the active instance of the active AI engine.
	 * 
	 * @return the singleton instance of the AI engine.
	 */
	public static AIEngine getInstance() {
		if (instance == null) {
			instance = new AIEngine();
		}
		return instance;
	}

	/**
	 * Call the update function on all the observers.
	 * 
	 * @param deltaTime
	 *            time passed since last frame.
	 */
	public void update(float deltaTime) {
		for (AIObserver observer : observers) {
			observer.update(deltaTime);
		}
	}

	/**
	 * Allow an observer to start observing this class
	 * 
	 * @param ai
	 *            the AIObserver observing the AIEngine.
	 */
	public void addAIObserver(AIObserver ai) {
		observers.add(ai);
	}

	/**
	 * Stop observing the AIEngine
	 * 
	 * @param ai
	 *            the AIObserve to remove from the observers list
	 */
	public void removeAIObserver(AIObserver ai) {
		observers.remove(ai);
	}
}
