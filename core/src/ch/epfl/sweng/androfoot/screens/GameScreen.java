package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.DualPlayerTouchTracker;
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

	private GroupPaddle mPaddlesOnePlayerOne = new GroupPaddle(1, 2, 3,
			PhysicsWorld.getPhysicsWorld().getWorld(), Constants.WORLD_SIZE_Y,
			true);

	private GroupPaddle mPaddlesTwoPlayerOne = new GroupPaddle(5, 2, 2,
			PhysicsWorld.getPhysicsWorld().getWorld(), Constants.WORLD_SIZE_Y,
			true);

	private GroupPaddle mPaddlesOnePlayerTwo = new GroupPaddle(7, 2, 3,
			PhysicsWorld.getPhysicsWorld().getWorld(), Constants.WORLD_SIZE_Y,
			false);

	private GroupPaddle mPaddlesTwoPlayerTwo = new GroupPaddle(3, 2, 2,
			PhysicsWorld.getPhysicsWorld().getWorld(), Constants.WORLD_SIZE_Y,
			false);

	private float mPlayerOneOldX = 0;
	private float mPlayerOneOldY = 0;
	private boolean mPlayerOneOldTouched = false;

	private float mPlayerTwoOldX = 0;
	private float mPlayerTwoOldY = 0;
	private boolean mPlayerTwoOldTouched = false;

	@Override
	public void render(float delta) {
		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render();
	}

	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
		if (playerId == 0) {
			if (mPlayerOneOldTouched == true && touched == true) {
				mPaddlesOnePlayerOne.setVelocity((posX - mPlayerOneOldX)
						* X_SPEED_RATIO, (mPlayerOneOldY - posY)
						* Y_SPEED_RATIO);
				mPaddlesTwoPlayerOne.setVelocity((posX - mPlayerOneOldX)
						* X_SPEED_RATIO, (mPlayerOneOldY - posY)
						* Y_SPEED_RATIO);
				mPlayerOneOldX = posX;
				mPlayerOneOldY = posY;
				mPlayerOneOldTouched = touched;
			} else if (touched) {
				mPlayerOneOldTouched = touched;
				mPlayerOneOldX = posX;
				mPlayerOneOldY = posY;
			} else {
				mPaddlesOnePlayerOne.setVelocity(0, 0);
				mPaddlesTwoPlayerOne.setVelocity(0, 0);
				mPlayerOneOldTouched = false;
			}
		} else if (playerId == 1) {
			if (mPlayerTwoOldTouched == true && touched == true) {
				mPaddlesOnePlayerTwo.setVelocity((posX - mPlayerTwoOldX)
						* X_SPEED_RATIO, (mPlayerTwoOldY - posY)
						* Y_SPEED_RATIO);
				mPaddlesTwoPlayerTwo.setVelocity((posX - mPlayerTwoOldX)
						* X_SPEED_RATIO, (mPlayerTwoOldY - posY)
						* Y_SPEED_RATIO);
				mPlayerTwoOldX = posX;
				mPlayerTwoOldY = posY;
				mPlayerTwoOldTouched = touched;
			} else if (touched) {
				mPlayerTwoOldTouched = touched;
				mPlayerTwoOldX = posX;
				mPlayerTwoOldY = posY;
			} else {
				mPaddlesOnePlayerTwo.setVelocity(0, 0);
				mPaddlesTwoPlayerTwo.setVelocity(0, 0);
				mPlayerOneOldTouched = false;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
	}

	@Override
	public void show() {
		GraphicEngine.getEngine().bindToWorld(PhysicsWorld.getPhysicsWorld());

		DualPlayerTouchTracker dptt = DualPlayerTouchTracker.getInstance();
		dptt.addObserver(this);
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
