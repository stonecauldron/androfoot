package ch.epfl.sweng.androfoot.utils;

/**
 * Class that creates a Timer that will check whether a defined time has
 * ellapsed or not.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class Timer {

	private final static float RESET_TIME = 0f;

	private float tickInterval;
	private float currentTimeCount;

	/**
	 * Create a Timer with a given time interval
	 * 
	 * @param tick
	 *            the wanted time interval
	 */
	public Timer(float tick) {
		tickInterval = tick;
	}

	/**
	 * update the timer.
	 * 
	 * @param deltaTime
	 *            the time ellapsed since the last frame.
	 */
	public void updateTimer(float deltaTime) {
		currentTimeCount += deltaTime;
	}
	
	/**
	 * checks if the Timer should signal a tick.
	 * 
	 * @return true if the tick interval has passed, false otherwise
	 */
	public boolean checkTimer() {
		if (currentTimeCount >= tickInterval) {
			// reset the timer
			currentTimeCount = RESET_TIME;

			return true;
		} else {
			return false;
		}
	}
}