package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.SinglePlayerTouchTracker;

import com.badlogic.gdx.Screen;

/**
 * Display the game
 * 
 * @author Guillame Leclerc
 *
 */
public class GameScreen implements Screen, TouchTrackerObserver {

	private final static float X_SPEED_RATIO = (float) 0.75;
	private final static float Y_SPEED_RATIO = (float) 0.75;

	private GroupPaddle mPaddlesOnePlayerOne = new GroupPaddle(2, 2, 1,
			PhysicsWorld.getPhysicsWorld().getWorld(), Constants.WORLD_SIZE_Y,
			true);

	private float mPlayerOneOldX = 0;
	private float mPlayerOneOldY = 0;
	private boolean mPlayerOneOldTouched = false;

	@Override
	public void render(float delta) {
		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render();
	}

	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
		if (mPlayerOneOldTouched == true && touched == true) {
			mPaddlesOnePlayerOne.setVelocity((posX - mPlayerOneOldX) * X_SPEED_RATIO,
					(mPlayerOneOldY - posY) * Y_SPEED_RATIO);
			mPlayerOneOldX = posX;
			mPlayerOneOldY = posY;
			mPlayerOneOldTouched = touched;
		} else if (touched) {
			mPlayerOneOldTouched = touched;
			mPlayerOneOldX = posX;
			mPlayerOneOldY = posY;
		} else {
			mPaddlesOnePlayerOne.setVelocity(0, 0);
			mPlayerOneOldTouched = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
	}

	@Override
	public void show() {
		GraphicEngine.getEngine().bindToWorld(PhysicsWorld.getPhysicsWorld());

		SinglePlayerTouchTracker sptt = SinglePlayerTouchTracker.getInstance();
		sptt.addObserver(this);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
