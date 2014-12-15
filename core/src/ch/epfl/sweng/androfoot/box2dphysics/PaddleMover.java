package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.interfaces.IsTransformableObserver;
import ch.epfl.sweng.androfoot.kryonetnetworking.InputData;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerClient;
import ch.epfl.sweng.androfoot.kryonetnetworking.PlayerHost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ahaeflig
 *
 *         This class is used to abstract the movement of the paddle from the
 *         player and to have proper metrics for moving paddles
 */
public class PaddleMover implements IsTransformableObserver {

	private float xSpeedRatio = (float) Constants.X_SPEED_RATIO;
	private float ySpeedRatio = (float) Constants.Y_SPEED_RATIO;

	private static final int PADDLE_COUNT = 5;

	// nexus 7 tablet screen size in cm
	private final static float NEXUS_X = 15.253849f;
	private final static float NEXUS_Y = 8.776713f;

	// MAKE THIS AN PARAMETER IN THE GAME SETTINGS ?
	private final static float CM_TRESHOLD = 0.040f;

	private float mPixelXScreenSize = Gdx.graphics.getWidth();
	private float mCentimeterXScreenSize = mPixelXScreenSize
			/ X_PIXEL_PER_CENTIMETER;

	private float mPixelYScreenSize = Gdx.graphics.getHeight();
	private float mCentimeterYScreenSize = mPixelYScreenSize
			/ Y_PIXEL_PER_CENTIMETER;

	private final static float X_PIXEL_PER_CENTIMETER = Gdx.graphics.getPpcX();
	private final static float Y_PIXEL_PER_CENTIMETER = Gdx.graphics.getPpcY();

	private List<GroupPaddle> mPaddleGroup;
	private float mMoveTresholdX;
	private float mMoveTresholdY;

	private boolean isTransformPositionSafe = false;
	private boolean newPosition = false;

	private ArrayList<Vector2> paddlePosition = new ArrayList<Vector2>();
	private ArrayList<Vector2> receivedPaddlePosition = new ArrayList<Vector2>();

	public void setNewPosition(boolean position) {
		this.newPosition = position;
	}

	public PaddleMover(List<GroupPaddle> listPaddle) {
		xSpeedRatio = Configuration.getInstance().getSensitivity();
		ySpeedRatio = Configuration.getInstance().getSensitivity();
		mPaddleGroup = listPaddle;

		paddlePosition = new ArrayList<Vector2>();
		// populate list with useless starting position
		for (int i = 0; i < PADDLE_COUNT; i++) {
			paddlePosition.add(new Vector2(0, 0));
		}

		for (int i = 0; i < PADDLE_COUNT; i++) {
			receivedPaddlePosition.add(new Vector2(0, 0));
		}

		EventManager.getEventManager().addIsTransformableObserver(this);
	}

	/**
	 * Call when you want to move player Paddle
	 * 
	 * @param deltaX
	 *            the X pixel coordinate of the move
	 * @param deltaY
	 *            the Y pixel coordinate of the move
	 */
	public void movePaddle(float deltaX, float deltaY) {
		Iterator<GroupPaddle> iterator = mPaddleGroup.iterator();
		int paddlePosCounter = 0;

		while (iterator.hasNext()) {
			GroupPaddle GroupPaddle = (GroupPaddle) iterator.next();
			// Used to get each paddle's position
			List<Paddle> paddles = GroupPaddle.getPaddles();
			for (Paddle paddle : paddles) {
				paddlePosition.set(paddlePosCounter++, paddle.getPosition());
			}
			GroupPaddle.setVelocity(deltaX * xSpeedRatio, deltaY * ySpeedRatio);
		}
	}

	public void moveNetworkPaddle(float deltaX, float deltaY,
			List<Vector2> networkPaddlePosition) {
		Iterator<GroupPaddle> iterator = mPaddleGroup.iterator();
		int paddlePosCounter = 0;

		while (iterator.hasNext()) {
			GroupPaddle GroupPaddle = (GroupPaddle) iterator.next();
			GroupPaddle.setVelocity(deltaX * xSpeedRatio, deltaY * ySpeedRatio);
			if (isTransformPositionSafe) {
				List<Paddle> paddles = GroupPaddle.getPaddles();
				for (Paddle paddle : paddles) {
					receivedPaddlePosition.set(paddlePosCounter,
							networkPaddlePosition.get(paddlePosCounter++));
				}
			}
		}
	}

	/**
	 * Call this method when the screen is resized to update new correct
	 * threshold value
	 * 
	 */
	public void updateTreshold() {
		mMoveTresholdX = CM_TRESHOLD
				* (Constants.WORLD_SIZE_X / mCentimeterXScreenSize)
				* (mCentimeterXScreenSize / NEXUS_X);
		mMoveTresholdY = CM_TRESHOLD
				* (Constants.WORLD_SIZE_Y / mCentimeterYScreenSize)
				* (mCentimeterYScreenSize / NEXUS_Y);
	}

	/**
	 * 
	 * @return X coordinate touch threshold in game unit
	 */
	public float getmMoveTresholdX() {
		return mMoveTresholdX;
	}

	/**
	 * 
	 * @return Y coordinate touch threshold in game unit
	 */
	public float getmMoveTresholdY() {
		return mMoveTresholdY;
	}

	/**
	 * 
	 * @param pixelDistance
	 *            the value in pixel in the x axis to translate to game units
	 * @return the value in game units
	 */
	public float pixelXToGameUnit(float pixelDistance) {
		return pixelDistance * (Constants.WORLD_SIZE_X / mPixelXScreenSize);
	}

	/**
	 * @param pixelDistance
	 *            the value in pixel in the y axis to translate to game units
	 * @return the value in game units
	 */
	public float pixelYToGameUnit(float pixelDistance) {
		return pixelDistance * (Constants.WORLD_SIZE_Y / mPixelYScreenSize);
	}

	public void moveNetworkPaddleHost(float deltaX, float deltaY) {
		PlayerHost.sendHostData(new InputData(deltaX, deltaY, paddlePosition));
	}

	public void moveNetworkPaddleClient(float deltaX, float deltaY) {
		PlayerClient.sendClientData(new InputData(deltaX, deltaY,
				paddlePosition));
	}

	@Override
	public void isTransformable() {
		isTransformPositionSafe = true;

		if (newPosition) {
			newPosition = false;
			Iterator<GroupPaddle> iterator = mPaddleGroup.iterator();
			int paddlePosCounter = 0;

			while (iterator.hasNext()) {
				GroupPaddle GroupPaddle = (GroupPaddle) iterator.next();
				List<Paddle> paddles = GroupPaddle.getPaddles();
				for (Paddle paddle : paddles) {
					paddle.setPosition(receivedPaddlePosition
							.get(paddlePosCounter++));
				}
			}

		}
	}
}