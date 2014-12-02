package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.box2dphysics.PaddleMover;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.screens.NetworkClientScreen;
import ch.epfl.sweng.androfoot.screens.NetworkHostScreen;

/**
 * Class to represent a player over the network.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 *
 */
public class RemotePlayer extends AbstractPlayer implements Controllable,
		ClientObserver, HostObserver {

	@SuppressWarnings("unused")
	private boolean mClientMode = false;
	@SuppressWarnings("unused")
	private boolean mHostMode = false;

	private PaddleMover mPaddleMover;

	private float mOldX = 0;
	private float mOldY = 0;
	private boolean mOldTouched = false;

	RemotePlayer(PlayerNumber number) {
		super(number);

		switch (number) {
			case ONE:
				NetworkClientScreen.getPlayerClient().addClientObserver(this);
				break;
			case TWO:
				NetworkHostScreen.getPlayerHost().addHostObserver(this);
				break;
			default:
				throw new IllegalArgumentException(
					"Wrong instantiation of a player see enum playerType");
		}

		this.mPaddleMover = new PaddleMover(super.getPaddles());
		mPaddleMover.updateTreshold();
	}

	@Override
	public void moveHorizontally(float deltaX) {
	}

	@Override
	public void moveVertically(float deltaY) {
	}

	@Override
	public void move(float deltaX, float deltaY) {
		mPaddleMover.movePaddle(deltaX, deltaY);
	}

	private void applyMoveCondition(int playerId, float posX, float posY,
			boolean touched) {
		float deltaX = mPaddleMover.pixelXToGameUnit(posX - mOldX);
		float deltaY = mPaddleMover.pixelYToGameUnit(posY - mOldY);
		float moveTresholdX = mPaddleMover.getmMoveTresholdX();
		float moveTresholdY = mPaddleMover.getmMoveTresholdY();

		if (mOldTouched == true && touched == true) {

			if (Math.abs(deltaX) > moveTresholdX
					&& Math.abs(deltaY) > moveTresholdY) {
				move(deltaX, -deltaY);
			} else if (Math.abs(deltaX) > moveTresholdX) {
				move(deltaX, 0);
			} else if (Math.abs(deltaY) > moveTresholdY) {
				move(0, -deltaY);
			} else {
				move(0, 0);
			}

			mOldX = posX;
			mOldY = posY;
			mOldTouched = touched;

		} else if (touched) {
			mOldTouched = touched;
			mOldX = posX;
			mOldY = posY;
		} else {
			move(0, 0);
			mOldTouched = false;
		}
	}

	@Override
	public void updateClientData(InputData data) {
		applyMoveCondition(2, data.getTouchX(), data.getTouchY(),
				data.isTouched());
	}

	@Override
	public void updateHostData(HostData data) {

	}

	@Override
	public void gameHostStart() {
		mHostMode = true;
	}

	@Override
	public void gameClientStart() {
		mClientMode = true;
	}

	@Override
	public void updateHostTouchData(InputData data) {
		applyMoveCondition(1, data.getTouchX(), data.getTouchY(),
				data.isTouched());
	}

}
