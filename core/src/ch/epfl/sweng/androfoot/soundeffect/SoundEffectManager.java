package ch.epfl.sweng.androfoot.soundeffect;

import java.util.Random;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

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

	private Sound mBallOnWall;
	private Sound mBallOnPaddle;
	private Sound mGoalPlayerOne;
	
	private String mBallOnWallPath = "sounds/ballwall.wav";
	private String mBallGoalPlayerOne = "sounds/goalplayerone.wav";
	private String mBallOnPaddlePath = "sounds/ballpaddle.wav";
	
	SoundEffectManager() {
		loadSoundEffect();
	}

	public void loadSoundEffect() {
		AssetManager manager = new AssetManager();
		manager.load(mBallOnWallPath, Sound.class);
		manager.load(mBallGoalPlayerOne, Sound.class);
		manager.load(mBallOnPaddlePath, Sound.class);
		manager.finishLoading();
		mBallOnWall = manager.get(mBallOnWallPath, Sound.class);
		mGoalPlayerOne = manager.get(mBallGoalPlayerOne, Sound.class);
		mBallOnPaddle = manager.get(mBallOnPaddlePath, Sound.class);
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
		mGoalPlayerOne.play();
	}
}
