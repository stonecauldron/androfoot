package ch.epfl.sweng.androfoot.soundeffect;

import java.util.Random;

import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

/**
 * @author Ahaeflig
 * 
 *         This class manages sound effects throughout the game listening to the
 *         physics engine's events
 */
public enum SoundEffectManager implements GoalObserver, PaddleContactObserver,
		BorderObserver {
	INSTANCE;

	private final static int MAX_PITCH = 150;
	
	private Sound mBallOnWall;
	private Sound mBallOnPaddle;
	private Sound mGoalPlayerOne;

	private final String mBallOnWallPath = "sounds/ballwall.wav";
	private final String mBallGoalPlayerOne = "sounds/goalplayerone.wav";
	private final String mBallOnPaddlePath = "sounds/ballpaddle.wav";

	/**
	 * Used to listen to the various game events
	 */
	public void observe() {
		EventManager.getEventManager().addGoalObserver(this);
		EventManager.getEventManager().addPaddleContactObserver(this);
		EventManager.getEventManager().addBorderContactObserver(this);
	}

	/**
	 * Call me to load sound assets
	 */
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

	/**
	 * 
	 * @return the unique instance of the SoundEffectManager
	 */
	public static SoundEffectManager getInstance() {
		return INSTANCE;
	}

	private float pitchRandomizer(int offSet) {
		Random rand = new Random();
		int randomNum = rand.nextInt((MAX_PITCH - offSet) + 1) + offSet;

		return (float) (randomNum / 100);
	}

	@Override
	public void goal(DefaultGoal goal, DefaultBall ball) {
		if (Configuration.getInstance().getSound())
			mGoalPlayerOne.play((float) 0.3, 1, 0);
	}

	@Override
	public void borderContact(DefaultBorder border, DefaultBall ball) {
		if (Configuration.getInstance().getSound())
			mBallOnWall.play((float) 0.1, 1, 0);
	}

	@Override
	public void paddleContact(DefaultPlayer player, DefaultBall ball) {
		if (Configuration.getInstance().getSound())
			mBallOnPaddle.play(1, pitchRandomizer(125), 0);
	}
}
