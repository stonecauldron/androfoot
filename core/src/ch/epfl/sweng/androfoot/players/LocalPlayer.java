package ch.epfl.sweng.androfoot.players;

import java.util.Iterator;

import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;
import ch.epfl.sweng.androfoot.utils.Timer;

/**
 * Class to represent a human player on the local machine.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 * @author Ahaeflig
 *
 */
public class LocalPlayer extends AbstractPlayer implements Controllable,
		TouchTrackerObserver {

	private final static float X_SPEED_RATIO = (float) 0.75;
	private final static float Y_SPEED_RATIO = (float) 0.75;

	private float mOldX = 0;
	private float mOldY = 0;
	private boolean mOldTouched = false;

	private PhysicsWorld physicWorld = PhysicsWorld.getPhysicsWorld();

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
		Iterator<GroupPaddle> iterator = super.getPaddles().iterator();
		while (iterator.hasNext()) {
			GroupPaddle paddle = (GroupPaddle) iterator.next();
			paddle.setXVelocity(deltaX * X_SPEED_RATIO);
		}
	}

	@Override
	public void moveVertically(float deltaY) {
		Iterator<GroupPaddle> iterator = super.getPaddles().iterator();
		while (iterator.hasNext()) {
			GroupPaddle paddle = (GroupPaddle) iterator.next();
			paddle.setYVelocity(deltaY * Y_SPEED_RATIO);
		}
	}

	@Override
	public void move(float deltaX, float deltaY) {
		Iterator<GroupPaddle> iterator = super.getPaddles().iterator();
		while (iterator.hasNext()) {
			GroupPaddle paddle = (GroupPaddle) iterator.next();
			paddle.setVelocity(deltaX * X_SPEED_RATIO, deltaY * Y_SPEED_RATIO);
		}
	}

	@Override
	public void updatePlayerOne(int playerId, float posX, float posY,
			boolean touched) {
		applyMoveCondition(playerId, posX, posY, touched);
	}

	@Override
	public void updatePlayerTwo(int playerId, float posX, float posY,
			boolean touched) {
		applyMoveCondition(playerId, posX, posY, touched);
	}

	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
	}

	private void applyMoveCondition(int playerId, float posX, float posY,
			boolean touched) {
		float deltaX = posX - mOldX;
		float deltaY = posY - mOldY;
		PlayerTouchTracker touchTracker = PlayerTouchTracker.getInstance();
		float moveTresholdX = touchTracker.getmMoveTresholdX();
		float moveTresholdY = touchTracker.getmMoveTresholdY();

		if (mOldTouched == true && touched == true) {

			if (Math.abs(deltaX) > moveTresholdX
					&& Math.abs(deltaY) > moveTresholdY) {
				move(deltaX, -(deltaY));
			} else if (Math.abs(deltaX) > moveTresholdX) {
				moveHorizontally(deltaX);
				moveVertically(0);
			} else if (Math.abs(deltaY) > moveTresholdY) {
				moveHorizontally(0);
				moveVertically(-deltaY);
			} else {
				move(0, 0);
			}

			// if (posX != -1 && posY != -1) {
			// move(deltaX, -deltaY);
			mOldX = posX;
			mOldY = posY;
			mOldTouched = touched;

			/*
			 * if (playerId == 1)
			 * physicWorld.getTouchEventTimerPlayerOne().resetTimer();
			 * 
			 * if(playerId == 2)
			 * physicWorld.getTouchEventTimerPlayerTwo().resetTimer();
			 */
			//
			// } else {
			// Case where we want to speed down paddle because of inactivity
			// move(0, 0);
			// }

		} else if (touched) {
			mOldTouched = touched;
			mOldX = posX;
			mOldY = posY;
		} else {
			move(0, 0);
			mOldTouched = false;
		}
	}
}
