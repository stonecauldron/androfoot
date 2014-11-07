package ch.epfl.sweng.androfoot.soundeffect;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Ahaeflig
 * 
 *         This class manages sound effects throughout the game listening to the
 *         physics engine's events
 */

public enum SoundEffectManager {
	INSTANCE;

	private Sound ballOnWall = null;
	private Sound ballOnPaddle;

	SoundEffectManager() {
		
		AssetManager manager = new AssetManager();
		manager.load("sounds/ballwall.wav", Sound.class);
		manager.finishLoading();
		ballOnWall = manager.get("sounds/ballwall.wav", Sound.class);
	}

	public static SoundEffectManager getInstance() {
		return INSTANCE;
	}

	public void play() {
		ballOnWall.play();
	}

}
