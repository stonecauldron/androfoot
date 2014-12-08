package ch.epfl.sweng.androfoot.players;

import ch.epfl.sweng.androfoot.box2dphysics.PaddleMover;
import ch.epfl.sweng.androfoot.interfaces.ClientObserver;
import ch.epfl.sweng.androfoot.interfaces.Controllable;
import ch.epfl.sweng.androfoot.interfaces.HostObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.GameInfo;
import ch.epfl.sweng.androfoot.kryonetnetworking.HostData;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.ShakeData;
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


	private PaddleMover mPaddleMover;

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

	@Override
	public void updateClientData(InputData data) {
		move(data.getTouchX(), data.getTouchY());
	}

	@Override
	public void updateHostTouchData(InputData data) {
		move(data.getTouchX(), data.getTouchY());
	}

	@Override
	public void updateHostData(HostData data) {

	}

	@Override
	public void updateClientShakeData(ShakeData data) {

	}

	@Override
	public void updateClientGameInfoData(GameInfo data) {
	}

	@Override
	public void updateHostGameInfoData(GameInfo data) {

	}

	@Override
	public void gameHostStart() {
	}

	@Override
	public void gameClientStart() {
	}

}
