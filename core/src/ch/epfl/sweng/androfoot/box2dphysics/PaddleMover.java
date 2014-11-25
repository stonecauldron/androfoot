package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;

/**
 * @author Ahaeflig
 *
 *         This class is used to abstract the movement of the paddle from the
 *         player and to have proper metrics for moving paddles
 */
public class PaddleMover {

	// TODO MAKE THIS PARAMETER IN THE GAME SETTINGS
	private final static float X_SPEED_RATIO = (float) 60;
	private final static float Y_SPEED_RATIO = (float) 60;

	// nexus 7 tablet screen size in cm
	private final static float NEXUS_X = 15.253849f;
	private final static float NEXUS_Y = 8.776713f;

	// TODO MAKE THIS AN PARAMETER IN THE GAME SETTINGS
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

	public PaddleMover(List<GroupPaddle> listPaddle) {
		mPaddleGroup = listPaddle;
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
		while (iterator.hasNext()) {
			GroupPaddle paddle = (GroupPaddle) iterator.next();
			paddle.setVelocity(deltaX * X_SPEED_RATIO, deltaY * Y_SPEED_RATIO);
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
				* (Constants.WORLD_SIZE_Y / mCentimeterYScreenSize) * (mCentimeterYScreenSize / NEXUS_Y);
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
	 * 
	 * @param pixelDistance
	 *            the value in pixel in the y axis to translate to game units
	 * @return the value in game units
	 */
	public float pixelYToGameUnit(float pixelDistance) {
		return (pixelDistance) * (Constants.WORLD_SIZE_Y / mPixelYScreenSize);
	}

}
