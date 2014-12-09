package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a powerup
 * 
 * @author Guillaume
 *
 */
public interface PowerUpEffect {

	/**
	 * get the duration of the powerup
	 * 
	 * @return the duration
	 */
	float getEffectDuration();
	
	void begin(boolean isTeam1);
	
	void end();
	
	PowerUpEffect copy();

}
