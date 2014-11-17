package ch.epfl.sweng.androfoot.rendering;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShockwaveManager implements DrawableRenderer{
	
	private final Set<ShockWave> shockwaves = new HashSet<ShockWave>(); 
	private final int maxNbShockwaves;
	
	ShockwaveManager(int maxWaves) {
		maxNbShockwaves = maxWaves;
	}
	
	public void addShockWave(ShockWave wave) {
		if(shockwaves.size() < maxNbShockwaves) {
			shockwaves.add(wave);
		}
	}
	
	public void age(float delta) {
		for(ShockWave wave : shockwaves) {
			wave.age(delta);
			if(wave.isEnded()) {
				//shockwaves.remove(wave);
			}
		}
	}
	

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shapes) {
		for(ShockWave wave : shockwaves) {
			wave.render(batch, shapes);
		}
	}

	
	


}
