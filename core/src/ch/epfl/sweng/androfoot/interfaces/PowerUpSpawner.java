package ch.epfl.sweng.androfoot.interfaces;

/**
 * Represent a class which spawn powerups
 * 
 * @author Guillaume
 *
 */
public interface PowerUpSpawner {

	void update(float delta);

	void addPowerUpEffect(PowerUpEffect effect);

	void setSpawnRate(float time);

	void setSeed(long l);

}
