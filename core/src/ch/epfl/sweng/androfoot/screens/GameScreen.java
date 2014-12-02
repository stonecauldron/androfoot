package ch.epfl.sweng.androfoot.screens;

import ch.epfl.sweng.androfoot.board.Board;
import ch.epfl.sweng.androfoot.board.BoardFactory;
import ch.epfl.sweng.androfoot.box2dphysics.PhysicsWorld;
import ch.epfl.sweng.androfoot.configuration.Configuration;
import ch.epfl.sweng.androfoot.players.PlayerType;
import ch.epfl.sweng.androfoot.players.ai.AIEngine;
import ch.epfl.sweng.androfoot.rendering.GraphicEngine;
import ch.epfl.sweng.androfoot.touchtracker.PlayerTouchTracker;
import ch.epfl.sweng.androfoot.gui.GuiManager;
import ch.epfl.sweng.androfoot.gui.GuiCommand;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;

/**
 * Display the game
 * 
 * @author Guillame Leclerc
 *
 */
public class GameScreen implements Screen {

	public GameScreen(PlayerType firstPlayer, PlayerType secondPlayer) {

		BoardFactory.setupBoard(firstPlayer, secondPlayer, Configuration
				.getInstance().getScoreLimit());

	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			GuiManager.getInstance().executeCommand(GuiCommand.goToMainMenu);
			// reset board
			Board.getInstance().resetBoard();
		}
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
		Gdx.input.setCatchBackKey(true);
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
