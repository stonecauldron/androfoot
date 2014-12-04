package ch.epfl.sweng.androfoot.interfaces;

public interface PowerUpEffectApplier extends PowerUpObserver{

	/**
	 * update the time intervals
	 * @param delta the time elapsed
	 */
	void update(float delta);

}
