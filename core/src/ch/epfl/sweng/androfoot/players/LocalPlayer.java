package ch.epfl.sweng.androfoot.players;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

/**
 * Class to represent a human player on the local machine.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class LocalPlayer extends AbstractPlayer implements Controllable,
		TouchTrackerObserver {

	private final static float X_SPEED_RATIO = (float) 0.75;
	private final static float Y_SPEED_RATIO = (float) 0.75;

	private float mOldX = 0;
	private float mOldY = 0;
	private boolean mOldTouched = false;

	LocalPlayer(PlayerNumber playerNumber) {
		super(playerNumber);
		switch (playerNumber) {
		case ONE:
			PlayerTouchTracker.getInstance().addObserverPlayerOne(this);
			break;
		case TWO:
			PlayerTouchTracker.getInstance().addObserverPlayerTwo(this);
			break;
		default:
			throw new IllegalArgumentException(
					"Wrong instantiation of a player see enum playerType");
		}
	}

	@Override
	public void moveHorizontally(float deltaX) {
	}

	@Override
	public void moveVertically(float deltaY) {
	}
	
	@Override
	public void move(float deltaX, float deltaY) {
		Iterator<GroupPaddle> iterator = super.getPaddles().iterator();
		while (iterator.hasNext()) {
			GroupPaddle paddle = (GroupPaddle) iterator.next();
			paddle.setVelocity(deltaX * X_SPEED_RATIO , deltaY * Y_SPEED_RATIO);
		}
		
	}

	private void applyMoveCondition(float posX, float posY, boolean touched) {
		if (mOldTouched == true && touched == true) {
			if (Math.abs(posX - mOldX) > 2 || Math.abs(mOldY - posY) > 2) {
				move(posX - mOldX, mOldY - posY);
			} else {
				move(0,0);
			}


			mOldX = posX;
			mOldY = posY;
			mOldTouched = touched;
		} else if (touched) {
			mOldTouched = touched;
			mOldX = posX;
			mOldY = posY;
		} else {
			move(0,0);
			mOldTouched = false;
		}
	}

	@Override
	public void updatePlayerOne(int playerId, float posX, float posY,
			boolean touched) {
		applyMoveCondition(posX, posY, touched);
	}

	@Override
	public void updatePlayerTwo(int playerId, float posX, float posY,
			boolean touched) {
		applyMoveCondition(posX, posY, touched);
	}

	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
	}


}
