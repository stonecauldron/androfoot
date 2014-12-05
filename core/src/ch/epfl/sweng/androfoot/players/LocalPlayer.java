package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.box2dphysics.PaddleMover;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;
import ch.epfl.sweng.androfoot.kryonetnetworking.ShakeData;
import ch.epfl.sweng.androfoot.screens.NetworkClientScreen;
import ch.epfl.sweng.androfoot.screens.NetworkHostScreen;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

/**
 * Class to represent a human player on the local machine.
 * 
 * @author Pedro Caldeira <pedrocaldeira>
 * @author Ahaeflig
 *
 */
public class LocalPlayer extends AbstractPlayer implements Controllable,
		TouchTrackerObserver, HostObserver, ClientObserver {

	private float mOldX = 0;
	private float mOldY = 0;
	private boolean mOldTouched = false;
	private PaddleMover mPaddleMover;

	private PlayerNumber mPlayerNumber;
	private boolean mClientMode;
	private boolean mHostMode;

	LocalPlayer(PlayerNumber playerNumber) {
		super(playerNumber);
		mPlayerNumber = playerNumber;
		switch (playerNumber) {
		case ONE:
			PlayerTouchTracker.getInstance().addObserverPlayerOne(this);
			if (Configuration.getInstance().isNetworkMode()) {
				NetworkHostScreen.getPlayerHost().addHostObserver(this);
			}
			break;
		case TWO:
			PlayerTouchTracker.getInstance().addObserverPlayerTwo(this);
			if (Configuration.getInstance().isNetworkMode()) {
				NetworkClientScreen.getPlayerClient().addClientObserver(this);
			}
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

		if (mHostMode) {
			PlayerHost.sendHostData(new InputData(deltaX, deltaY));
		}

		if (mClientMode) {
			PlayerClient.sendClientData(new InputData(deltaX, deltaY));
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
	public void destroy() {
		super.destroy();
		switch (mPlayerNumber) {
		case ONE:
			PlayerTouchTracker.getInstance().removeObserverPlayerOne(this);
			NetworkHostScreen.getPlayerHost().addHostObserver(this);
			break;
		case TWO:
			PlayerTouchTracker.getInstance().removeObserverPlayerTwo(this);
			NetworkClientScreen.getPlayerClient().addClientObserver(this);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
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
	public void updateHostData(HostData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHostTouchData(InputData data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateHostGameInfoData(GameInfo data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClientData(InputData data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateClientShakeData(ShakeData data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateClientGameInfoData(GameInfo data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void gameClientStart() {
		mClientMode = true;
	}

	@Override
	public void gameHostStart() {
		mHostMode = true;
	}

}
