package ch.epfl.sweng.androfoot.soundeffect;

import java.util.Random;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Ahaeflig
 * 
 *         This class manages sound effects throughout the game listening to the
 *         physics engine's events
 */
public enum SoundEffectManager implements GoalObserver {
	INSTANCE;

	private Sound mBallOnWall = null;
	private Sound mBallOnPaddle;
	private Sound mGoalPlayerOne;

	SoundEffectManager() {

		AssetManager manager = new AssetManager();
		manager.load("sounds/ballwall.wav", Sound.class);
		manager.load("sounds/goalplayerone.wav", Sound.class);
		manager.finishLoading();
		mBallOnWall = manager.get("sounds/ballwall.wav", Sound.class);
		mGoalPlayerOne = manager.get("sounds/goalplayerone.wav", Sound.class);
	}

	public static SoundEffectManager getInstance() {
		return INSTANCE;
	}

	public void play() {
		
		Random rand = new Random();
	    int randomNum = rand.nextInt((250 - 50) + 1) + 50;
	    float pitchRandomizer = (float)(randomNum / 100);
		
		mBallOnWall.play(1, pitchRandomizer, 0);
	}

	@Override
	public void goal(boolean isTeamOne) {
		Gdx.app.log("SON GOAL", "SON GOAL MARQUEE");
		mGoalPlayerOne.play();
	}
}
