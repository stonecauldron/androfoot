package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.box2dphysics.Ball;
import ch.epfl.sweng.androfoot.box2dphysics.Constants;
import ch.epfl.sweng.androfoot.box2dphysics.EventManager;
import ch.epfl.sweng.androfoot.box2dphysics.GroupPaddle;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.box2dphysics.Player;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;
import ch.epfl.sweng.androfoot.interfaces.TouchTrackerObserver;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.soundeffect.SoundEffectManager;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Screen;

/**
 * Display the game
 * 
 * @author Guillame Leclerc
 *
 */
public class GameScreen implements Screen, TouchTrackerObserver, GoalObserver, PlayerObserver {

	private final static float X_SPEED_RATIO = (float) 0.75;
	private final static float Y_SPEED_RATIO = (float) 0.75;

	private float mPlayerOneOldX = 0;
	private float mPlayerOneOldY = 0;
	private boolean mPlayerOneOldTouched = false;

	private float mPlayerTwoOldX = 0;
	private float mPlayerTwoOldY = 0;
	private boolean mPlayerTwoOldTouched = false;
	

	public GameScreen() {
	    EventManager.getEventManager().addGoalObserver(this);
	    EventManager.getEventManager().addPlayerObserver(this);
	}

	@Override
	public void render(float delta) {
		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render();
	}

	// TODO Refactor code duplication.
	@Override
	public void update(int playerId, float posX, float posY, boolean touched) {
//		if (playerId == 0) {
//			if (mPlayerOneOldTouched == true && touched == true) {
//				/*
//				 * This condition checks if the touch dragged coordinates we get
//				 * from the the input class is big enough because we don't want
//				 * to update the velocity but set it to zero if the user doesn't
//				 * move but holds his finger against the screen (gives small
//				 * input variation because of not precise touch screens
//				 * physics).
//				 */
//				if (Math.abs(posX - mPlayerOneOldX) > 2
//						|| Math.abs(mPlayerOneOldY - posY) > 2) {
//					mPaddlesOnePlayerOne.setVelocity((posX - mPlayerOneOldX)
//							* X_SPEED_RATIO, (mPlayerOneOldY - posY)
//							* Y_SPEED_RATIO);
//					mPaddlesTwoPlayerOne.setVelocity((posX - mPlayerOneOldX)
//							* X_SPEED_RATIO, (mPlayerOneOldY - posY)
//							* Y_SPEED_RATIO);
//				} else {
//					mPaddlesOnePlayerOne.setVelocity(0, 0);
//					mPaddlesTwoPlayerOne.setVelocity(0, 0);
//				}
//				mPlayerOneOldX = posX;
//				mPlayerOneOldY = posY;
//				mPlayerOneOldTouched = touched;
//			} else if (touched) {
//				mPlayerOneOldTouched = touched;
//				mPlayerOneOldX = posX;
//				mPlayerOneOldY = posY;
//			} else {
//				mPaddlesOnePlayerOne.setVelocity(0, 0);
//				mPaddlesTwoPlayerOne.setVelocity(0, 0);
//				mPlayerOneOldTouched = false;
//			}
//		} else if (playerId == 1) {
//			if (mPlayerTwoOldTouched == true && touched == true) {
//				if (Math.abs(posX - mPlayerTwoOldX) > 2
//						|| Math.abs(mPlayerTwoOldY - posY) > 2) {
//					mPaddlesOnePlayerTwo.setVelocity((posX - mPlayerTwoOldX)
//							* X_SPEED_RATIO, (mPlayerTwoOldY - posY)
//							* Y_SPEED_RATIO);
//					mPaddlesTwoPlayerTwo.setVelocity((posX - mPlayerTwoOldX)
//							* X_SPEED_RATIO, (mPlayerTwoOldY - posY)
//							* Y_SPEED_RATIO);
//				} else {
//					mPaddlesOnePlayerTwo.setVelocity(0, 0);
//					mPaddlesTwoPlayerTwo.setVelocity(0, 0);
//				}
//				mPlayerTwoOldX = posX;
//				mPlayerTwoOldY = posY;
//				mPlayerTwoOldTouched = touched;
//			} else if (touched) {
//				mPlayerTwoOldTouched = touched;
//				mPlayerTwoOldX = posX;
//				mPlayerTwoOldY = posY;
//			} else {
//				mPaddlesOnePlayerTwo.setVelocity(0, 0);
//				mPaddlesTwoPlayerTwo.setVelocity(0, 0);
//				mPlayerTwoOldTouched = false;
//			}
//		}
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
		PlayerTouchTracker.getInstance().setNewScreenWidth(width);
	}

	@Override
	public void show() {
		GraphicEngine.getEngine().bindToWorld(PhysicsWorld.getPhysicsWorld());
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

    @Override
    public void goal(boolean isTeamOne) {
        Ball ball = PhysicsWorld.getPhysicsWorld().getBall();
        ball.setBallPosition(Constants.WORLD_SIZE_X / 2, Constants.WORLD_SIZE_Y / 2);
        ball.setLinearVelocity(3, 0);
    }

	@Override
	public void setBall(Player player, boolean teamFlag) {
		System.out.println("Yes!");
		
		Ball ball = PhysicsWorld.getPhysicsWorld().getBall();
		
		if (teamFlag) {
			ball.setBallPosition(player.getPositionX() + Constants.BALL_CONTROL_OFFSET,
					player.getPositionY());
			ball.setLinearVelocity(0, 0);
		} else {
			ball.setBallPosition(player.getPositionX() - Constants.BALL_CONTROL_OFFSET,
					player.getPositionY());
			ball.setLinearVelocity(0, 0);
		}
	}
}
