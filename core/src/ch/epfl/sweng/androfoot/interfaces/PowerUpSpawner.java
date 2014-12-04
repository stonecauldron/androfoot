package ch.epfl.sweng.androfoot.interfaces;

public interface PowerUpSpawner {
	
	void update(float delta);
	void addPowerUpEffect(PowerUpEffect effect);
	void setSpawnRate(float time);
	
}
