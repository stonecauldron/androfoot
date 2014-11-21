package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;

import com.badlogic.gdx.Screen;

/**
 * Display the game
 * 
 * @author Guillame Leclerc
 *
 */
public class GameScreen implements Screen {

	public GameScreen(PlayerType secondPlayer) {
	    BoardFactory.setupBoard(PlayerType.LOCAL_PLAYER, secondPlayer);
	}

	@Override
	public void render(float delta) {
		AIEngine.getInstance().update(delta);
		PhysicsWorld.getPhysicsWorld().phyStep(delta);
		GraphicEngine.getEngine().render(delta);
	}

	@Override
	public void resize(int width, int height) {
		GraphicEngine.getEngine().setScreenSize(width, height);
		PlayerTouchTracker.getInstance().setNewScreenWidth(width, height);
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
}
