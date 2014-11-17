package ch.epfl.sweng.androfoot.rendering;

import java.util.HashSet;
import java.util.Iterator;
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
		Iterator<ShockWave> iterator = shockwaves.iterator();
		while(iterator.hasNext())
		{
			ShockWave w = iterator.next();
			w.age(delta);
			if(w.isEnded()) {
				System.out.println("ended");
				iterator.remove();
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
